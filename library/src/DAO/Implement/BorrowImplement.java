package DAO.Implement;

import DTO.Borrow;
import DAO.Interface.BorrowInterface;
import DbConnect.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowImplement implements BorrowInterface {
    private final Connection connection = dbConnection.getConn();

    @Override
    public Borrow createBorrow(Borrow borrow) throws SQLException {
        String insertQuery = "INSERT INTO Borrow (id_copy, id_borrower, date_borrow, date_return_agreement) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, borrow.getBookCopyId());
            preparedStatement.setInt(2, borrow.getBorrowerId());
            preparedStatement.setDate(3, new java.sql.Date(borrow.getDateBorrow().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(borrow.getDateReturnAgreement().getTime()));
            // preparedStatement.setDate(5, new java.sql.Date(borrow.getDateReturn().getTime()));

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    borrow.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error creating borrow" + e, e);
        }

        return borrow;
    }

    @Override
    public Borrow getBorrowById(int id) throws SQLException {
        String selectQuery = "SELECT id, id_copy , id_borrower, date_borrow, date_return_agreement, date_actual_return FROM Borrow WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int borrowId = resultSet.getInt("id");
                int bookCopyId = resultSet.getInt("id_copy");
                int borrowerId = resultSet.getInt("id_borrower");
                java.util.Date dateBorrow = resultSet.getDate("date_borrow");
                java.util.Date dateReturnAgreement = resultSet.getDate("date_return_agreement");
                java.util.Date dateReturn = resultSet.getDate("date_actual_return");

                return new Borrow(borrowId, bookCopyId, borrowerId, dateBorrow, dateReturnAgreement, dateReturn);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving borrow" + e, e);
        }
    }

    @Override
    public List<Borrow> getAllBorrows() throws SQLException {
        List<Borrow> borrows = new ArrayList<>();
        String selectQuery = "SELECT id, id_copy, id_borrower, date_borrow, date_return_agreement, date_actual_return FROM Borrow";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int bookCopyId = resultSet.getInt("id_copy");
                int borrowerId = resultSet.getInt("id_borrower");
                java.util.Date dateBorrow = resultSet.getDate("date_borrow");
                java.util.Date dateReturnAgreement = resultSet.getDate("date_return_agreement");
                java.util.Date dateReturn = resultSet.getDate("date_actual_return");

                Borrow borrow = new Borrow(id, bookCopyId, borrowerId, dateBorrow, dateReturnAgreement, dateReturn);
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving borrows" + e, e);
        }

        return borrows;
    }

    @Override
    public boolean deleteBorrowById(int id) throws SQLException {
        String deleteQuery = "DELETE FROM Borrow WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Error deleting borrow", e);
        }
    }

    @Override
    public List<Borrow> findBorrowsByBookCopyId(int bookCopyId) throws SQLException {
        List<Borrow> borrows = new ArrayList<>();
        String selectQuery = "SELECT id, id_copy, id_borrower, date_borrow, date_return_agreement, date_actual_return FROM Borrow WHERE id_copy = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, bookCopyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int borrowerId = resultSet.getInt("id_borrower");
                java.util.Date dateBorrow = resultSet.getDate("date_borrow");
                java.util.Date dateReturnAgreement = resultSet.getDate("date_return_agreement");
                java.util.Date dateReturn = resultSet.getDate("date_actual_return");

                Borrow borrow = new Borrow(id, bookCopyId, borrowerId, dateBorrow, dateReturnAgreement, dateReturn);
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            throw new SQLException("Error finding borrows by book copy ID", e);
        }

        return borrows;
    }

    @Override
    public List<Borrow> findBorrowsByBorrowerId(int borrowerId) throws SQLException {
        List<Borrow> borrows = new ArrayList<>();
        String selectQuery = "SELECT id, id_copy, id_borrower, date_borrow, date_return_agreement, date_actual_return FROM Borrow WHERE id_borrower = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, borrowerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int bookCopyId = resultSet.getInt("id_copy");
                java.util.Date dateBorrow = resultSet.getDate("date_borrow");
                java.util.Date dateReturnAgreement = resultSet.getDate("date_return_agreement");
                java.util.Date dateReturn = resultSet.getDate("date_return");

                Borrow borrow = new Borrow(id, bookCopyId, borrowerId, dateBorrow, dateReturnAgreement, dateReturn);
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            throw new SQLException("Error finding borrows by borrower ID", e);
        }

        return borrows;
    }

    @Override
    public Borrow updateBorrow(Date date, int id) throws SQLException {
        Borrow borrow = null;
        String updateQuery = "UPDATE Borrow SET date_actual_return = ? where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setDate(1, new java.sql.Date(date.getTime()));
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();
            borrow = this.getBorrowById(id);
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update Borrow");
            }
        } catch (SQLException e) {
            throw new SQLException("Error updating Borrow" + e, e);
        }

        return borrow;
    }

}
