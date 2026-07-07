import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        User user = new User();
        Hackathon hackathon = new Hackathon();

        int choice;

        do {
            System.out.println("\n========== HACKATHON PORTAL ==========");
            System.out.println("1. User Login");
            System.out.println("2. Organization Login");
            System.out.println("3. Exit");
            System.out.print("Enter Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                // ---------------- USER ----------------
                case 1:
                    int userChoice;

                    do {
                        System.out.println("\n------ USER MENU ------");
                        System.out.println("1. Register User");
                        System.out.println("2. Edit Profile");
                        System.out.println("3. View Profile");
                        System.out.println("4. View Hackathon");
                        System.out.println("5. Logout");
                        System.out.print("Enter Choice: ");

                        userChoice = sc.nextInt();
                        sc.nextLine();

                        switch (userChoice) {

                            case 1:
                                user.addNewUser();
                                break;

                            case 2:
                                user.editProfile();
                                break;

                            case 3:
                                user.viewProfile();
                                break;

                            case 4:
                                hackathon.viewHackathon();
                                break;

                            case 5:
                                System.out.println("Logged Out Successfully.");
                                break;

                            default:
                                System.out.println("Invalid Choice!");
                        }

                    } while (userChoice != 5);
                    break;

                // ---------------- ORGANIZATION ----------------
                case 2:
                    int orgChoice;

                    do {
                        System.out.println("\n------ ORGANIZATION MENU ------");
                        System.out.println("1. Add Hackathon");
                        System.out.println("2. View Hackathon");
                        System.out.println("3. Delete Hackathon");
                        System.out.println("4. Logout");
                        System.out.print("Enter Choice: ");

                        orgChoice = sc.nextInt();
                        sc.nextLine();

                        switch (orgChoice) {

                            case 1:
                                hackathon.addHackathon();
                                break;

                            case 2:
                                hackathon.viewHackathon();
                                break;

                            case 3:
                                hackathon.deleteHackathon();
                                break;

                            case 4:
                                System.out.println("Logged Out Successfully.");
                                break;

                            default:
                                System.out.println("Invalid Choice!");
                        }

                    } while (orgChoice != 4);
                    break;

                case 3:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 3);

        sc.close();
    }
}