package DAO.Implement;

import DAO.Interface.BorrowerInterface;
import DTO.Borrower;
import DbConnect.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowerImplement implements BorrowerInterface {
    private Connection connection;

    public BorrowerImplement() {
        connection = dbConnection.getConn();
    }

    @Override
    public Borrower createBorrower(Borrower borrower) throws SQLException {
        String insertQuery = "INSERT INTO Borrower (name, cne) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, borrower.getName());
            preparedStatement.setString(2, borrower.getCne());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    borrower.setId(generatedKeys.getInt(1));
                    return borrower; // Return the borrower with the generated ID
                }
            }
        }

        return null; // Return null if the insertion fails
    }

    @Override
    public Borrower getBorrowerById(int id) throws SQLException {
        String selectQuery = "SELECT id, name, cne FROM Borrower WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int borrowerId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String cne = resultSet.getString("cne");
                return new Borrower(borrowerId, name, cne);
            }
        }

        return null; // Return null if the borrower is not found
    }

    @Override
    public List<Borrower> getAllBorrowers() throws SQLException {
        List<Borrower> borrowers = new ArrayList<>();
        String selectAllQuery = "SELECT id, name, cne FROM Borrower";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int borrowerId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String cne = resultSet.getString("cne");
                borrowers.add(new Borrower(borrowerId, name, cne));
            }
        }

        return borrowers;
    }

    @Override
    public boolean deleteBorrowerById(int id) throws SQLException {
        String deleteQuery = "DELETE FROM borrower WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected == 1; // Return true if one row is deleted
        }
    }

    public Borrower updateBorrower(Borrower borrowerToUpdate) throws SQLException {
        return null;
    }
}
