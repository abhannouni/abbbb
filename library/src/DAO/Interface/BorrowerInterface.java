package DAO.Interface;

import DTO.Borrower;

import java.sql.SQLException;
import java.util.List;

public interface BorrowerInterface {
    Borrower createBorrower(Borrower borrower) throws SQLException;

    Borrower getBorrowerById(int id) throws SQLException;

    List<Borrower> getAllBorrowers() throws SQLException;

    boolean deleteBorrowerById(int id) throws SQLException;
}
