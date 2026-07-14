import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Hackathon {
    Scanner sc = new Scanner(System.in);
    private String title;
    private String locationCity;
    private String mode;
    private double prizePool;
    private Date startDate;
    private Date endDate;
    private Date registrationDeadline;
    private int maxParticipants;
    private int currentParticipants = 0;

    // Add New Hackathon
    public void addHackathon() {

        System.out.println("\n===== Add Hackathon =====");

        System.out.print("Enter Title: ");
        title = sc.nextLine();

        System.out.print("Enter Location City: ");
        locationCity = sc.nextLine();

        System.out.print("Enter Mode (Online/Offline/Hybrid): ");
        mode = sc.nextLine();

        System.out.print("Enter Prize Pool: ");
        prizePool = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Start Date (yyyy-mm-dd): ");
        startDate = Date.valueOf(LocalDate.parse(sc.nextLine()));

        System.out.print("Enter End Date (yyyy-mm-dd): ");
        endDate = Date.valueOf(LocalDate.parse(sc.nextLine()));

        System.out.print("Enter Registration Deadline (yyyy-mm-dd): ");
        registrationDeadline = Date.valueOf(LocalDate.parse(sc.nextLine()));

        System.out.print("Enter Maximum Participants: ");
        maxParticipants = sc.nextInt();
        sc.nextLine();

        currentParticipants = 0;

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hackthone",
                    "root",
                    "");

            String query = "INSERT INTO hackathons(title,location_city,mode,prize_pool,start_date,end_date,registration_deadline,max_participants,current_participants) VALUES(?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, title);
            ps.setString(2, locationCity);
            ps.setString(3, mode);
            ps.setDouble(4, prizePool);
            ps.setDate(5, startDate);
            ps.setDate(6, endDate);
            ps.setDate(7, registrationDeadline);
            ps.setInt(8, maxParticipants);
            ps.setInt(9, currentParticipants);

            ps.executeUpdate();

            System.out.println("Hackathon Added Successfully!");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete Hackathon
public void deleteHackathon() {

    try {

        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hackthone",
                "root",
                "");

        System.out.print("Enter Hackathon ID to Delete: ");
        int hackathonId = sc.nextInt();
        sc.nextLine();

        String query = "DELETE FROM hackathons WHERE hackathon_id=?";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, hackathonId);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Hackathon Deleted Successfully!");
        } else {
            System.out.println("Hackathon Not Found!");
        }

        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

// View Hackathon
public void viewHackathon() {

    try {

        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hackthone",
                "root",
                "");

        String query = "SELECT * FROM hackathons";

        PreparedStatement ps = con.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n========== HACKATHONS ==========");

        while (rs.next()) {

            System.out.println("--------------------------------------------");
            System.out.println("Hackathon ID          : " + rs.getInt("hackathon_id"));
            System.out.println("Title                 : " + rs.getString("title"));
            System.out.println("Location              : " + rs.getString("location_city"));
            System.out.println("Mode                  : " + rs.getString("mode"));
            System.out.println("Prize Pool            : " + rs.getDouble("prize_pool"));
            System.out.println("Start Date            : " + rs.getDate("start_date"));
            System.out.println("End Date              : " + rs.getDate("end_date"));
            System.out.println("Registration Deadline : " + rs.getDate("registration_deadline"));
            System.out.println("Max Participants      : " + rs.getInt("max_participants"));
            System.out.println("Current Participants  : " + rs.getInt("current_participants"));
        }

        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}

