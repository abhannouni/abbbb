package DAO.Interface;

import DTO.Borrow;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface BorrowInterface {
    Borrow createBorrow(Borrow borrow) throws SQLException;

    Borrow getBorrowById(int id) throws SQLException;

    List<Borrow> getAllBorrows() throws SQLException;

    boolean deleteBorrowById(int id) throws SQLException;

    List<Borrow> findBorrowsByBookCopyId(int bookCopyId)throws SQLException;

    List<Borrow> findBorrowsByBorrowerId(int borrowerId)throws SQLException;

    Borrow updateBorrow(Date date, int id) throws SQLException;
}



