package pro1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {


    public  void main_user() throws Exception {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hackthone",
                "root",
                "");
        Scanner sc = new Scanner(System.in);

        User user = new User();
        Hackathon hackathon = new Hackathon();
        Team team = new Team();
        Registration registration = new Registration();
//        Watchlist watchlist = new Watchlist();
        Recommendation recommendation = new Recommendation();

        int userId = -1;
        String userEmail = "";
        int choice;


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
                    userEmail=user.registerUser();
                    String sql = "SELECT user_id FROM users WHERE email = ?";

                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, userEmail);

                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        userId = rs.getInt("user_id");
                    } else {
                        System.out.println("User not found!");
                    }
                    break;

                case 2:
                    System.out.print("Enter Email: ");
                    String loginEmail = sc.nextLine();

                    System.out.print("Enter Password: ");
                    String loginPassword = sc.nextLine();

                    userEmail=loginEmail;
                    String sql1 = "SELECT user_id FROM users WHERE email = ?";

                    PreparedStatement pst2 = con.prepareStatement(sql1);
                    pst2.setString(1, loginEmail);

                    ResultSet rs1 = pst2.executeQuery();

                    if (rs1.next()) {
                        userId = rs1.getInt("user_id");
                    }
                    //--------------------------------------------------------------------------
                    if (user.loginUser(loginEmail,loginPassword)) {


                        int userChoice;

                        do {

                            System.out.println("\n========== USER MENU ==========");
                            System.out.println("1. Profile");
                            System.out.println("2. Hackathons");
                            System.out.println("3. Recommendations");
                            System.out.println("4. Filter Hackathons");
                            System.out.println("5. Logout");

                            System.out.print("Enter Choice: ");

                            userChoice = sc.nextInt();
                            sc.nextLine();

                            switch (userChoice) {

                                case 1:

                                    int profileChoice;

                                    do {

                                        System.out.println("\n========== PROFILE ==========");
                                        System.out.println("1. View Profile");
                                        System.out.println("2. Edit Profile");
                                        System.out.println("3. Exit");
                                        System.out.print("Enter Choice: ");

                                        profileChoice = sc.nextInt();
//                                        sc.nextLine();

                                        switch (profileChoice) {

                                            case 1:
                                                user.viewProfile();
                                                break;

                                            case 2:
                                                user.editProfile();
                                                break;

                                            case 3:
                                                System.out.println("Returning...");
                                                break;

                                            default:
                                                System.out.println("Invalid Choice!");
                                        }

                                    } while (profileChoice != 3);

                                    break;

                                case 2:

                                    int hackChoice;

                                    do {

                                        System.out.println("\n========== HACKATHONS ==========");
                                        System.out.println("1. View Hackathons");
                                        System.out.println("2. Create Team");
                                        System.out.println("3. Join Team");
                                        System.out.println("4. Leave Team");
                                        System.out.println("5. View Teams");
                                        System.out.println("6. View Team Members");
                                        System.out.println("7. Register for Hackathon");
                                        System.out.println("8. Bookmark hackthone");
                                        System.out.println("9. Exit");
                                        System.out.print("Enter Choice: ");

                                        hackChoice = sc.nextInt();
                                        sc.nextLine();

                                        switch (hackChoice) {

                                            case 1:
                                                hackathon.viewHackathon();
                                                break;

                                            case 2:
                                                team.createTeam(userEmail);
                                                break;

                                            case 3:
                                                team.joinTeam(userEmail);
                                                break;

                                            case 4:
                                                team.leaveTeam(userEmail);
                                                break;

                                            case 5:
                                                team.viewTeam();
                                                break;

                                            case 6:
                                                team.viewTeamMembers();
                                                break;

                                            case 7:
                                                registration.registerHackathon(userEmail);
                                                break;

                                            case 8:
                                                Watchlist a=new Watchlist();
                                               a.menu(1);
                                                break;

                                            case 9:
                                                System.out.println("Returning...");
                                                break;
                                            default:
                                                System.out.println("Invalid Choice!");
                                        }

                                    } while (hackChoice != 9);

                                    break;

                                case 3:

                                    int recChoice;

                                    do {

                                        System.out.println("\n========== RECOMMENDATIONS ==========");
                                        System.out.println("1. Recommended Best Hackathons");
                                        System.out.println("2. Recommended Roadmap");
                                        System.out.println("3. Project Ideas / AI Chatbot");
                                        System.out.println("4. Recommended Join Team");
                                        System.out.println("5. Exit");
                                        System.out.print("Enter Choice: ");

                                        recChoice = sc.nextInt();
                                        sc.nextLine();

                                        switch (recChoice) {

                                            case 1:
                                                recommendation_for_Best_hackthone a=new recommendation_for_Best_hackthone();
                                                a.displayRecommendation(userId);
                                                break;

                                            case 2:
                                                Recommendation a1=new Recommendation();
                                                a1.buildRecommendationPrompt(userId);
                                                // recommendation.roadmap(userEmail);
                                                break;

                                            case 3:
                                                Recommendation_project_idea a2=new Recommendation_project_idea();
                                                a2.chat();
                                                // recommendation.projectIdeaChatbot(userEmail);
                                                break;

                                            case 4:
                                                Recommendation_To_Join_team a3=new Recommendation_To_Join_team();
                                                a3.recommendTeams(userId);
                                                // recommendation.joinTeamRecommendation(userEmail);
                                                break;

                                            case 5:
                                                System.out.println("Returning...");
                                                break;

                                            default:
                                                System.out.println("Invalid Choice!");
                                        }

                                    } while (recChoice != 5);

                                    break;

                                case 4:
                                    hackathon.filterHackathons();
                                    break;

                                case 5:
                                    System.out.println("Logged Out Successfully!");

                                    break;

//                                case 6:
//                                    team.leaveTeam(userEmail);
//                                    break;
//
//                                case 7:
//                                    team.viewTeam();
//                                    break;
//
//                                case 8:
//                                    team.viewTeamMembers();
//                                    break;
//
//                                case 9:
//                                    registration.registerHackathon(userEmail);
//                                    break;
//
//                                case 10:
////                                    watchlist.addToWatchlist(userEmail);
//                                    break;
//
//                                case 11:
////                                    watchlist.removeFromWatchlist(userEmail);
//                                    break;
//
//                                case 12:
////                                    watchlist.viewWatchlist(userEmail);
//                                    break;
//
//                                case 13:
//                                    System.out.println("Logged Out Successfully!");
//                                    break;
//                                case 14:
//                                    System.out.print("Enter Your Email Again: ");
//                                    userEmail = sc.nextLine();
////                                    recommendation.generateRecommendations(userEmail);
//                                    break;
//                                case 15:
////                                    recommendation.viewRecommendations(userEmail);
//                                    break;
//                                case 16:
//                                    hackathon.filterHackathons();
//                                    break;


                                default:
                                    System.out.println("Invalid Choice!");
                            }

                        } while (userChoice != 5);

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

//        sc.close();
    }
}
