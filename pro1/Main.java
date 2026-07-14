import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        User user = new User();
        Hackathon hackathon = new Hackathon();
        Team team = new Team();
        Registration registration = new Registration();

        int choice;
        String userEmail = "";

        do {

            System.out.println("\n========== HACKATHON PORTAL ==========");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter Choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    user.registerUser();
                    break;

                case 2:

                    if (user.loginUser()) {

                        System.out.print("Enter Your Email Again: ");
                        userEmail = sc.nextLine();

                        int userChoice;

                        do {

                            System.out.println("\n========== USER MENU ==========");
                            System.out.println("1. Edit Profile");
                            System.out.println("2. View Profile");
                            System.out.println("3. View Hackathons");
                            System.out.println("4. Create Team");
                            System.out.println("5. Join Team");
                            System.out.println("6. Leave Team");
                            System.out.println("7. View Teams");
                            System.out.println("8. View Team Members");
                            System.out.println("9. Register for Hackathon");
                            System.out.println("10. Logout");
                            System.out.print("Enter Choice: ");

                            userChoice = sc.nextInt();
                            sc.nextLine();

                            switch (userChoice) {

                                case 1:
                                    user.editProfile();
                                    break;

                                case 2:
                                    user.viewProfile();
                                    break;

                                case 3:
                                    hackathon.viewHackathon();
                                    break;

                                case 4:
                                    team.createTeam(userEmail);
                                    break;

                                case 5:
                                    team.joinTeam(userEmail);
                                    break;

                                case 6:
                                    team.leaveTeam(userEmail);
                                    break;

                                case 7:
                                    team.viewTeam();
                                    break;

                                case 8:
                                    team.viewTeamMembers();
                                    break;

                                case 9:
                                    registration.registerHackathon(userEmail);
                                    break;

                                case 10:
                                    System.out.println("Logged Out Successfully!");
                                    break;

                                default:
                                    System.out.println("Invalid Choice!");
                            }

                        } while (userChoice != 10);

                    } else {
                        System.out.println("Login Failed!");
                    }

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
