package Management;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import DAO.Implement.BookCopyImplement;
import DAO.Implement.BookImplement;
import DTO.BookCopy;

public class BookCopyManagement  {
    static BookCopyImplement bookCopyDao = new BookCopyImplement(); // Initialize this with your actual DAO implementation
    static BookImplement  bookDao = new BookImplement();
    public static void BookCopyMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Book Copy Management System");
            System.out.println("1. Create a Book Copy");
            System.out.println("2. Delete a Book Copy");
            System.out.println("3. Get Available Book Copies");
            System.out.println("4. Get a Book Copy by ID");
            System.out.println("5. List All Book Copies");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            try {
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        // Create a Book Copy
                        BookCopy newBookCopy = createBookCopy(scanner);
                        if(bookDao.getBookByIsbn(newBookCopy.getIsbn()) != null){
                            if (bookCopyDao.createBookCopy(newBookCopy) != null) {
                                System.out.println("Book Copy created successfully.");
                            } else {
                                System.out.println("Failed to create the book copy.");
                            }
                        }else{
                            System.out.println("isbn does not exist");
                        }
                        break;
                    case 2:
                        // Delete a Book Copy
                        System.out.print("Enter ID of the book copy to delete: ");
                        int bookCopyIdToDelete = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        boolean deleted = bookCopyDao.deleteBookCopyById(bookCopyIdToDelete);
                        if (deleted) {
                            System.out.println("Book Copy deleted successfully.");
                        } else {
                            System.out.println("Book Copy not found or deletion failed.");
                        }
                        break;
                    case 3:
                        // Get Available Book Copies
                        List<BookCopy> availableBookCopies = bookCopyDao.getAvailableBookCopies();
                        System.out.println("Available Book Copies:");
                        for (BookCopy bookCopy : availableBookCopies) {
                            System.out.println(bookCopy);
                        }
                        break;
                    case 4:
                        // Get a Book Copy by ID
                        System.out.print("Enter the Book Copy ID: ");
                        int bookCopyId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        BookCopy retrievedBookCopy = bookCopyDao.getBookCopyById(bookCopyId);
                        if (retrievedBookCopy != null) {
                            System.out.println("Retrieved Book Copy: " + retrievedBookCopy);
                        } else {
                            System.out.println("Book Copy not found with ID: " + bookCopyId);
                        }
                        break;
                    case 5:
                        // List All Book Copies
                        List<BookCopy> allBookCopies = bookCopyDao.getAllBookCopies();
                        System.out.println("List of Book Copies:");
                        for (BookCopy bookCopy : allBookCopies) {
                            System.out.println(bookCopy);
                        }
                        break;
                    case 6:
                        // Exit
                        System.out.println("Exiting Book Copy Management System. Goodbye!");
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

    private static BookCopy createBookCopy(Scanner scanner) {
        System.out.println("Creating a new Book Copy:");

        System.out.print("Enter ISBN of the Book: ");
        String bookIsbn = scanner.nextLine();

        return new BookCopy(bookIsbn);
    }
}



// public class BookCopyManagement {
    

//     public static void main(String[] args) {
//         BookCopyMenu();
//     }
// }
