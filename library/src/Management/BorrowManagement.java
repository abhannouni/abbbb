package Management;

import DAO.Implement.BookCopyImplement;
import DAO.Implement.BorrowImplement;
import DAO.Implement.BorrowerImplement;
import DAO.Interface.BookCopyInterface;
import DAO.Interface.BorrowInterface;
import DAO.Interface.BorrowerInterface;
import DTO.BookCopy;
import DTO.Borrow;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BorrowManagement {
    static BorrowInterface borrowDao = new BorrowImplement(); // Initialize this with your actual DAO implementation
    static BorrowerInterface borrowerDao = new BorrowerImplement();    
    static BookCopyInterface bookcopyDao = new BookCopyImplement();
    // static BookCopy book = new book()

    public static void BorrowMenu() throws ParseException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Borrow Management System");
            System.out.println("1. Create a Borrow");
            System.out.println("2. Get a Borrow by ID");
            System.out.println("3. List All Borrows");
            System.out.println("4. Delete a Borrow by ID");
            System.out.println("5. Find Borrows by Book Copy ID");
            System.out.println("6. Find Borrows by Borrower ID");
            System.out.println("7. Update Borrow");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            try {
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        // Create a Borrow
                        Borrow newBorrow = createBorrow(scanner);
                        BookCopy bookcopy = bookcopyDao.getBookCopyById(newBorrow.getBookCopyId());
                        if(borrowerDao.getBorrowerById(newBorrow.getId()) == null){
                            System.out.println("id of borrower does not exist");
                        }else if(bookcopy == null){
                            System.out.println("id of copy does not exist");
                        }else if(bookcopy.getStatus().toString() != "AVAILABLE"){
                            System.out.println("you can not borrow this copy");
                        }
                        else{
                            if (borrowDao.createBorrow(newBorrow) != null) {
                                System.out.println("Borrow created successfully.");
                            } else {
                                System.out.println("Failed to create the borrow.");
                            }
                        }
                        break;
                    case 2:
                        // Get a Borrow by ID
                        System.out.print("Enter ID of the borrow to retrieve: ");
                        int borrowId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        Borrow retrievedBorrow = borrowDao.getBorrowById(borrowId);
                        if (retrievedBorrow != null) {
                            System.out.println("Retrieved Borrow: " + retrievedBorrow);
                        } else {
                            System.out.println("Borrow not found with ID: " + borrowId);
                        }
                        break;
                    case 3:
                        // List All Borrows
                        List<Borrow> allBorrows = borrowDao.getAllBorrows();
                        System.out.println("List of Borrows:");
                        for (Borrow borrow : allBorrows) {
                            System.out.println(borrow);
                        }
                        break;
                    case 4:
                        // Delete a Borrow by ID
                        System.out.print("Enter ID of the borrow to delete: ");
                        int deleteBorrowId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        boolean deleted = borrowDao.deleteBorrowById(deleteBorrowId);
                        if (deleted) {
                            System.out.println("Borrow deleted successfully.");
                        } else {
                            System.out.println("Borrow not found or deletion failed.");
                        }
                        break;
                    case 5:
                        // Find Borrows by Book Copy ID
                        System.out.print("Enter Book Copy ID: ");
                        int bookCopyId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        List<Borrow> borrowsByBookCopy = borrowDao.findBorrowsByBookCopyId(bookCopyId);
                        if (!borrowsByBookCopy.isEmpty()) {
                            System.out.println("Borrows for Book Copy ID " + bookCopyId + ":");
                            for (Borrow borrow : borrowsByBookCopy) {
                                System.out.println(borrow);
                            }
                        } else {
                            System.out.println("No borrows found for Book Copy ID " + bookCopyId);
                        }
                        break;
                    case 6:
                        // Find Borrows by Borrower ID
                        System.out.print("Enter Borrower ID: ");
                        int borrowerId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        List<Borrow> borrowsByBorrower = borrowDao.findBorrowsByBorrowerId(borrowerId);
                        if (!borrowsByBorrower.isEmpty()) {
                            System.out.println("Borrows for Borrower ID " + borrowerId + ":");
                            for (Borrow borrow : borrowsByBorrower) {
                                System.out.println(borrow);
                            }
                        } else {
                            System.out.println("No borrows found for Borrower ID " + borrowerId);
                        }
                        break;
                    case 7:
                        System.out.println("set return date ");
                        Date date = setReturnDate(scanner);
                        Borrow borrow = checkIfIdExist(scanner);
                        if(date != null || borrow  != null){
                            if (borrowDao.updateBorrow(date, borrow.getId()) != null) {
                                System.out.println("Borrow up successfully.");
                            } else {
                                System.out.println("Failed to create the borrow.");
                            }
                        }else{
                            System.out.println("id does not exist or date invalid.");
                        }
                        break;
                    case 8:
                        // Exit
                        System.out.println("Exiting Borrow Management System. Goodbye!");
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

    private static Borrow createBorrow(Scanner scanner) throws ParseException, SQLException {
        System.out.println("Creating a new Borrow:");

        // Input Book Copy ID
        System.out.print("Enter Book Copy ID: ");
        int bookCopyId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        // Input Borrower ID
        System.out.print("Enter Borrower ID: ");
        int borrowerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        // Input number of days to add
        System.out.print("Enter the number of days to add: ");
        int daysToAdd = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        // Calculate dates based on the current date
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Calculate Date of Borrow
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
        Date dateBorrow = calendar.getTime();

        // Calculate Date of Return Agreement (e.g., 14 days after Date of Borrow)
        calendar.setTime(dateBorrow);
        calendar.add(Calendar.DAY_OF_MONTH, 14); // Assuming a default of 14 days for return agreement
        Date dateReturnAgreement = calendar.getTime();

        // Date of Actual Return is initially null since it's not known at this point


        // Create a new Borrow object
        Borrow newBorrow = new Borrow(bookCopyId, borrowerId, dateBorrow, dateReturnAgreement);

        try {
            // Create the borrow record
            newBorrow = borrowDao.createBorrow(newBorrow);
            System.out.println("Borrow created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating borrow: " + e.getMessage());
        }

        return newBorrow;
    }

    private static Date setReturnDate(Scanner scanner) {
        

        while (true) {
            System.out.print("Set return date to current time? (yes/no): ");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "yes":
                    // Get the current date and time
                    Date currentDate = new Date();

                    // Print the current date and time
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println("Return date set to current time: " + dateFormat.format(currentDate));

                    // Return the current date as the return date
                    return currentDate;

                case "no":
                    // Do nothing, return null or handle it as needed
                    return null;

                default:
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }

    public static Borrow checkIfIdExist(Scanner scanner) throws SQLException{
        System.out.println("enter Id of borrow");
        int id = scanner.nextInt();
        return borrowDao.getBorrowById(id);
    }

    // private static void updateBorrow(Scanner scanner) throws SQLException, ParseException {
    //     System.out.println("Updating a Borrow:");

    //     System.out.print("Enter ID of the borrow to update: ");
    //     int borrowId = scanner.nextInt();
    //     scanner.nextLine(); // Consume newline character

    //     // Check if the borrow with the provided ID exists
    //     Borrow existingBorrow = borrowDao.getBorrowById(borrowId);
    //     if (existingBorrow == null) {
    //         System.out.println("Borrow not found with ID " + borrowId);
    //         return;
    //     }

    //     System.out.println("Current Borrow Information:");
    //     System.out.println(existingBorrow);

    //     // Input new Date of Actual Return
    //     System.out.print("Enter new Date of Actual Return (yyyy-MM-dd): ");
    //     String dateActualReturnStr = scanner.nextLine();
    //     Date newDateActualReturn = new SimpleDateFormat("yyyy-MM-dd").parse(dateActualReturnStr);
    //     existingBorrow.setDateReturn(newDateActualReturn);

    //     // Update the borrow record
    //     borrowDao.updateBorrow(existingBorrow);
    //     System.out.println("Borrow updated successfully.");
    // }
}

