package Management;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import DAO.Implement.BookImplement;
import DTO.Book;

public class BookManagement {
    static BookImplement bookDao = new BookImplement(); // Initialize this with your actual DAO implementation

    public static void BookMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Book Management System");
            System.out.println("0. return menu principal");
            System.out.println("1. Create a Book");
            System.out.println("2. Update a Book");
            System.out.println("3. Delete a Book");
            System.out.println("4. Get a Book by ISBN");
            System.out.println("5. List All Books");
            System.out.println("6. Find Books by Author");
            System.out.println("7. Find Books by Title");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            try {
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        // Create a Book
                        Book newBook = createBook(scanner);
                        if (bookDao.createBook(newBook) != null) {
                            System.out.println("Book created successfully.");
                        } else {
                            System.out.println("Failed to create the book.");
                        }
                        break;
                    case 2:
                        // Update a Book
                        System.out.print("Enter ISBN of the book to update: ");
                        String isbnToUpdate = scanner.nextLine();
                        Book bookToUpdate = bookDao.getBookByIsbn(isbnToUpdate);
                        if (bookToUpdate != null) {
                            updateBook(scanner);
                        } else {
                            System.out.println("Book not found with ISBN: " + isbnToUpdate);
                        }
                        break;
                    case 3:
                        // Delete a Book
                        System.out.print("Enter ISBN of the book to delete: ");
                        String isbnToDelete = scanner.nextLine();
                        boolean deleted = bookDao.deleteBookByIsbn(isbnToDelete);
                        if (deleted) {
                            System.out.println("Book deleted successfully.");
                        } else {
                            System.out.println("Book not found or deletion failed.");
                        }
                        break;
                    case 4:
                        // Get a Book by ISBN
                        System.out.print("Enter the ISBN: ");
                        String bookIsbn = scanner.nextLine();
                        Book retrievedBook = bookDao.getBookByIsbn(bookIsbn);
                        if (retrievedBook != null) {
                            System.out.println("Retrieved Book: " + retrievedBook);
                        } else {
                            System.out.println("Book not found with ISBN: " + bookIsbn);
                        }
                        break;
                    case 5:
                        // List All Books
                        List<Book> allBooks = bookDao.getAllBooks();
                        System.out.println("List of Books:");
                        for (Book book : allBooks) {
                            System.out.println(book);
                        }
                        break;
                    case 6:
                        // Find Books by Author
                        System.out.print("Enter the Author's name: ");
                        String authorName = scanner.nextLine();
                        List<Book> booksByAuthor = bookDao.findBooksByAuthor(authorName);
                        if (!booksByAuthor.isEmpty()) {
                            System.out.println("Books by Author:");
                            for (Book book : booksByAuthor) {
                                System.out.println(book);
                            }
                        } else {
                            System.out.println("No books found for Author: " + authorName);
                        }
                        break;
                    case 7:
                        // Find Books by Title
                        System.out.print("Enter the Title: ");
                        String bookTitle = scanner.nextLine();
                        List<Book> booksByTitle = bookDao.findBooksByTitle(bookTitle);
                        if (!booksByTitle.isEmpty()) {
                            System.out.println("Books by Title:");
                            for (Book book : booksByTitle) {
                                System.out.println(book);
                            }
                        } else {
                            System.out.println("No books found with Title: " + bookTitle);
                        }
                        break;
                    case 8:
                        // Exit
                        System.out.println("Exiting Book Management System. Goodbye!");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            }
        }
    }

    private static Book createBook(Scanner scanner) {
        System.out.println("Creating a new Book:");
    
        // Input ISBN
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
    
        // Input Title
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
    
        // Input Author
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
    
        // Create a new Book object
        Book newBook = new Book(isbn, title, author);
    
        try {
            // Create the book record
            newBook = bookDao.createBook(newBook);
            System.out.println("Book created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating book: " + e.getMessage());
        }
    
        return newBook;
    }
    
    private static Book updateBook(Scanner scanner) throws SQLException {
        System.out.println("Updating a Book:");
    
        System.out.print("Enter ISBN of the book to update: ");
        String isbnToUpdate = scanner.nextLine();
    
        // Check if the book with the provided ISBN exists
        Book existingBook = bookDao.getBookByIsbn(isbnToUpdate);
        if (existingBook == null) {
            System.out.println("Book not found with ISBN " + isbnToUpdate);
            return null;
        }
    
        System.out.println("Current Book Information:");
        System.out.println(existingBook);
    
        // Input new Title
        System.out.print("Enter new Title (or press Enter to keep existing): ");
        String newTitle = scanner.nextLine();
        if (!newTitle.isEmpty()) {
            existingBook.setTitle(newTitle);
        }
    
        // Input new Author
        System.out.print("Enter new Author (or press Enter to keep existing): ");
        String newAuthor = scanner.nextLine();
        if (!newAuthor.isEmpty()) {
            existingBook.setAuthor(newAuthor);
        }
    
        try {
            // Update the book record
            existingBook = bookDao.updateBook(existingBook);
            System.out.println("Book updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
        }
    
        return existingBook;
    }
    

}

