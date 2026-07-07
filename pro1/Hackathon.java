import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Hackathon {
    // int hackathonId;
    String title;
    String organizer;
    String location;
    // String domain;
    double prizePool;
    Date startDate;
    Date endDate;
    // Date registrationDeadline;
    ArrayList<String> requiredSkills;
    int maxTeamSize;

    // Add New Hackathon
    public void addHackathon() {
        Scanner sc = new Scanner(System.in);

        // System.out.print("Enter Hackathon ID: ");
        // hackathonId = sc.nextInt();
        // sc.nextLine();

        System.out.print("Enter Title: ");
        title = sc.nextLine();

        System.out.print("Enter Organizer: ");
        organizer = sc.nextLine();

        System.out.print("Enter Location: ");
        location = sc.nextLine();

        // System.out.print("Enter Domain: ");
        // domain = sc.nextLine();

        System.out.print("Enter Prize Pool: ");
        prizePool = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Start Date (yyyy-mm-dd): ");
        startDate = Date.valueOf(LocalDate.parse(sc.nextLine()));

        System.out.print("Enter End Date (yyyy-mm-dd): ");
        endDate = Date.valueOf(LocalDate.parse(sc.nextLine()));

        // System.out.print("Enter Registration Deadline (yyyy-mm-dd): ");
        // registrationDeadline = Date.valueOf(LocalDate.parse(sc.nextLine()));

        requiredSkills = new ArrayList<>();
        System.out.print("Enter Required Skill: ");
        requiredSkills.add(sc.nextLine());

        System.out.print("Enter Max Team Size: ");
        maxTeamSize = sc.nextInt();

        System.out.println("Hackathon Added Successfully!");
    }


    // Delete Hackathon
    public void deleteHackathon() {

        if (title == null) {
            System.out.println("No Hackathon Available!");
            return;
        }

        // hackathonId = 0;
        title = null;
        organizer = null;
        location = null;
        // domain = null;
        prizePool = 0;
        startDate = null;
        endDate = null;
        // registrationDeadline = null;

        if (requiredSkills != null) {
            requiredSkills.clear();
        }

        maxTeamSize = 0;

        System.out.println("Hackathon Deleted Successfully!");
    }

    // View Hackathon
    public void viewHackathon() {

        if (title == null) {
            System.out.println("No Hackathon Available!");
            return;
        }

        System.out.println("\n----- Hackathon Details -----");
        // System.out.println("Hackathon ID : " + hackathonId);
        System.out.println("Title : " + title);
        System.out.println("Organizer : " + organizer);
        System.out.println("Location : " + location);
        // System.out.println("Domain : " + domain);
        System.out.println("Prize Pool : " + prizePool);
        System.out.println("Start Date : " + startDate);
        System.out.println("End Date : " + endDate);
        // System.out.println("Registration Deadline : " + registrationDeadline);
        System.out.println("Required Skills : " + requiredSkills);
        System.out.println("Max Team Size : " + maxTeamSize);
    }
}