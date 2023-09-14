package Management;

import DAO.Implement.BookCopyImplement;
import DAO.Interface.BookCopyInterface;
import DTO.BookCopy;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class StatisticManagement {
    static BookCopyInterface bookcopyDao = new BookCopyImplement();

    public static void StatisticMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Statistic Menu:");
            System.out.println("1. Display Book Copy Count by Status");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    return; // Return to the main menu
                case 1:
                    displayBookCopyCountByStatus();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void displayBookCopyCountByStatus() throws SQLException {
        Map<BookCopy.Status, Integer> copyCountByStatus = bookcopyDao.displayBookCopyCountByStatus();

        System.out.println("Book Copy Count by Status:");
        for (Map.Entry<BookCopy.Status, Integer> entry : copyCountByStatus.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
