package DAO.Implement;

import DTO.BookCopy;
import DAO.Interface.BookCopyInterface;
import DbConnect.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookCopyImplement implements BookCopyInterface {
    private final Connection connection = dbConnection.getConn();

    @Override
    public BookCopy createBookCopy(BookCopy bookCopy) throws SQLException {
        String insertQuery = "INSERT INTO BookCopy (isbn) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, bookCopy.getIsbn());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                return bookCopy;
            }
        } catch (SQLException e) {
            throw new SQLException("Error creating book copy", e);
        }

        return null;
    }

    @Override
    public BookCopy getBookCopyById(int id) throws SQLException {
        String selectQuery = "SELECT id, isbn, status FROM BookCopy WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String statusStr = resultSet.getString("status");
                BookCopy.Status status = BookCopy.Status.valueOf(statusStr);

                return new BookCopy(id, isbn, status);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving book copy by ID", e);
        }

        return null;
    }

    @Override
    public List<BookCopy> getAllBookCopies() throws SQLException {
        List<BookCopy> bookCopies = new ArrayList<>();
        String selectQuery = "SELECT id, isbn, status FROM BookCopy";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String isbn = resultSet.getString("isbn");
                String statusStr = resultSet.getString("status");
                BookCopy.Status status = BookCopy.Status.valueOf(statusStr);

                bookCopies.add(new BookCopy(id, isbn, status));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving all book copies", e);
        }

        return bookCopies;
    }

    @Override
    public boolean deleteBookCopyById(int id) throws SQLException {
        String deleteQuery = "DELETE FROM BookCopy WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Error deleting book copy by ID", e);
        }
    }

    @Override
    public List<BookCopy> getAvailableBookCopies() throws SQLException {
        List<BookCopy> availableBookCopies = new ArrayList<>();
        String selectQuery = "SELECT id, isbn, status FROM BookCopy WHERE status = ?::bookstatus";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, BookCopy.Status.AVAILABLE.toString());
    
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String isbn = resultSet.getString("isbn");
                String statusStr = resultSet.getString("status");
                BookCopy.Status status = BookCopy.Status.valueOf(statusStr);
    
                availableBookCopies.add(new BookCopy(id, isbn, status));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving available book copies: " + e.getMessage(), e);
        }
    
        return availableBookCopies;
    }
    
    @Override
    public List<BookCopy> getBookCopiesByIsbn(String isbn) throws SQLException {
        List<BookCopy> bookCopies = new ArrayList<>();
        String selectQuery = "SELECT id, isbn, status FROM BookCopy WHERE isbn = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String statusStr = resultSet.getString("status");
                BookCopy.Status status = BookCopy.Status.valueOf(statusStr);

                bookCopies.add(new BookCopy(id, isbn, status));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving book copies by ISBN", e);
        }

        return bookCopies;
    }
    @Override
    public Map<BookCopy.Status, Integer> displayBookCopyCountByStatus() throws SQLException {
    Map<BookCopy.Status, Integer> copyCountByStatus = new HashMap<>();
    String selectQuery = "SELECT status, COUNT(*) as copy_count FROM BookCopy GROUP BY status";

    try (Connection connection = dbConnection.getConn();
         PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {
            String statusStr = resultSet.getString("status");
            BookCopy.Status status = BookCopy.Status.valueOf(statusStr);
            int copyCount = resultSet.getInt("copy_count");

            copyCountByStatus.put(status, copyCount);
        }
    } catch (SQLException e) {
        throw new SQLException("Error retrieving book copy counts by status", e);
    }

    return copyCountByStatus;
}


}
