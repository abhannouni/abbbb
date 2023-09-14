package DAO.Interface;

import DTO.BookCopy;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BookCopyInterface {
    BookCopy createBookCopy(BookCopy bookCopy) throws SQLException;

    BookCopy getBookCopyById(int id) throws SQLException;

    List<BookCopy> getAllBookCopies() throws SQLException;

    List<BookCopy> getAvailableBookCopies() throws SQLException;

    List<BookCopy> getBookCopiesByIsbn(String isbn) throws SQLException;

    Map<BookCopy.Status, Integer> displayBookCopyCountByStatus() throws SQLException;

    boolean deleteBookCopyById(int id) throws SQLException;
}
