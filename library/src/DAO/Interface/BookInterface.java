package DAO.Interface;

import DTO.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookInterface {
    Book createBook(Book book) throws SQLException;

    Book getBookByIsbn(String isbn) throws SQLException;

    List<Book> getAllBooks() throws SQLException;

    List<Book> findBooksByAuthor(String author) throws SQLException;

    List<Book> findBooksByTitle(String title) throws SQLException;

    List<Book> findBooksByIsbn(String isbn) throws SQLException;

    Book updateBook(Book book) throws SQLException;

    boolean deleteBookByIsbn(String isbn) throws SQLException;
}
