package DAO.Implement;

import DTO.Book;
import DAO.Interface.BookInterface;
import DbConnect.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookImplement implements BookInterface {
    private final Connection connection = dbConnection.getConn();

    @Override
    public Book createBook(Book book) throws SQLException {
        String insertQuery = "INSERT INTO Book (isbn, title, author) VALUES (?, ?, ?)";
        if(this.getBookByIsbn(book.getIsbn()) == null){
            System.out.println("ISBN already exist ");
            return null;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                return book;
            }
        } catch (SQLException e) {
            throw new SQLException("Error creating book", e);
        }

        return null;
    }

    @Override
    public Book getBookByIsbn(String isbn) throws SQLException {
        String selectQuery = "SELECT isbn, title, author FROM Book WHERE isbn = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");

                return new Book(isbn, title, author);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving book by ISBN", e);
        }

        return null;
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String selectQuery = "SELECT isbn, title, author FROM Book";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");

                books.add(new Book(isbn, title, author));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving all books", e);
        }

        return books;
    }

    @Override
    public List<Book> findBooksByAuthor(String author) throws SQLException {
        List<Book> books = new ArrayList<>();
        String selectQuery = "SELECT isbn, title, author FROM Book WHERE author LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, author + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                author = resultSet.getString("author");
                books.add(new Book(isbn, title, author));
            }
        } catch (SQLException e) {
            throw new SQLException("Error finding books by author", e);
        }

        return books;
    }

    @Override
    public List<Book> findBooksByTitle(String title) throws SQLException {
        List<Book> books = new ArrayList<>();
        String selectQuery = "SELECT isbn, title, author FROM Book WHERE title LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, title + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String author = resultSet.getString("author");
                title = resultSet.getString("title");
                books.add(new Book(isbn, title, author));
            }
        } catch (SQLException e) {
            throw new SQLException("Error finding books by title", e);
        }

        return books;
    }

    @Override
    public boolean deleteBookByIsbn(String isbn) throws SQLException {
        String deleteQuery = "DELETE FROM Book WHERE isbn = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, isbn);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Error deleting book by ISBN", e);
        }
    }

    @Override
    public List<Book> findBooksByIsbn(String isbn) throws SQLException {
        List<Book> books = new ArrayList<>();
        String selectQuery = "SELECT isbn, title, author FROM Book WHERE isbn = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String bookIsbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");

                books.add(new Book(bookIsbn, title, author));
            }
        } catch (SQLException e) {
            throw new SQLException("Error finding books by ISBN", e);
        }

        return books;
    }

    @Override
    public Book updateBook(Book book) throws SQLException {
        String updateQuery = "UPDATE Book SET title = ?, author = ? WHERE isbn = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getIsbn());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Failed to update book");
            }
        } catch (SQLException e) {
            throw new SQLException("Error updating book", e);
        }

        return book;
    }


}
