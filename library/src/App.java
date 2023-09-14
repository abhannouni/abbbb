import Management.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Borrowers");            
            System.out.println("3. Manage Borrow");
            System.out.println("4. Manage copy");
            System.out.println("5. Statistic");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    BookManagement.BookMenu();
                    break;
                case 2:
                    BorrowerManagement.BorrowerMenu();
                    break;
                case 3:
                    BorrowManagement.BorrowMenu();
                    break;
                case 4:
                    // StatisticManagement.StatisticMenu();
                    BookCopyManagement.BookCopyMenu();
                    break;
                case 5:
                    StatisticManagement.StatisticMenu();
                    break;
                case 0:
                    System.out.println("Exiting Library Management System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
