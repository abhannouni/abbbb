package Management;

import java.sql.SQLException;
// import java.util.Date;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import DAO.Implement.BorrowerImplement;
import DTO.Borrower;

public class BorrowerManagement {
    static BorrowerImplement borrowerDao = new BorrowerImplement(); // Initialize this with your actual DAO implementation

    public static void BorrowerMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Borrower Management System");
            System.out.println("1. Create a Borrower");
            System.out.println("2. Update a Borrower");
            System.out.println("3. Delete a Borrower");
            System.out.println("4. Get a Borrower by ID");
            System.out.println("5. List All Borrowers");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            try {
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        // Create a Borrower
                        Borrower newBorrower = createBorrower(scanner);
                        if (borrowerDao.createBorrower(newBorrower) != null) {
                            System.out.println("Borrower created successfully.");
                        } else {
                            System.out.println("Failed to create the borrower.");
                        }
                        break;
                    case 2:
                        // Update a Borrower
                        System.out.print("Enter ID of the borrower to update: ");
                        int borrowerIdToUpdate = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        Borrower borrowerToUpdate = borrowerDao.getBorrowerById(borrowerIdToUpdate);
                        if (borrowerToUpdate != null) {
                            updateBorrower(scanner, borrowerToUpdate);
                        } else {
                            System.out.println("Borrower not found with ID: " + borrowerIdToUpdate);
                        }
                        break;
                    case 3:
                        // Delete a Borrower
                        System.out.print("Enter ID of the borrower to delete: ");
                        int borrowerIdToDelete = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        boolean deleted = borrowerDao.deleteBorrowerById(borrowerIdToDelete);
                        if (deleted) {
                            System.out.println("Borrower deleted successfully.");
                        } else {
                            System.out.println("Borrower not found or deletion failed.");
                        }
                        break;
                    case 4:
                        // Get a Borrower by ID
                        System.out.print("Enter the Borrower ID: ");
                        int borrowerId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        Borrower retrievedBorrower = borrowerDao.getBorrowerById(borrowerId);
                        if (retrievedBorrower != null) {
                            System.out.println("Retrieved Borrower: " + retrievedBorrower);
                        } else {
                            System.out.println("Borrower not found with ID: " + borrowerId);
                        }
                        break;
                    case 5:
                        // List All Borrowers
                        List<Borrower> allBorrowers = borrowerDao.getAllBorrowers();
                        System.out.println("List of Borrowers:");
                        for (Borrower borrower : allBorrowers) {
                            System.out.println(borrower);
                        }
                        break;
                    case 6:
                        // Exit
                        System.out.println("Exiting Borrower Management System. Goodbye!");
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

    private static Borrower createBorrower(Scanner scanner) {
        System.out.println("Creating a new Borrower:");

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter CNE: ");
        String cne = scanner.nextLine();

        return new Borrower(name, cne);
    }

    private static void updateBorrower(Scanner scanner, Borrower borrowerToUpdate) throws SQLException {
        System.out.println("Updating Borrower with ID: " + borrowerToUpdate.getId());

        // Display menu for updating fields
        while (true) {
            System.out.println("Select a field to update:");
            System.out.println("1. Name");
            System.out.println("2. CNE");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    borrowerToUpdate.setName(newName);
                    System.out.println("Name updated successfully!");
                    break;
                case 2:
                    System.out.print("Enter new CNE: ");
                    String newCNE = scanner.nextLine();
                    borrowerToUpdate.setCne(newCNE);
                    System.out.println("CNE updated successfully!");
                    break;
                case 3:
                    // Exit the loop and return the updated borrower
                    borrowerDao.updateBorrower(borrowerToUpdate); // Update the borrower in the database
                    System.out.println("Borrower updated successfully!");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}

