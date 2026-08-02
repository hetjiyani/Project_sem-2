package pro1;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    public void addHackathon(int id) {

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

            // Insert Hackathon
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
            ps.close();

            // Find latest hackathon ID
            String sql = "SELECT MAX(hackathon_id) FROM hackathons";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                int hackathonId = rs.getInt(1);

                // Insert into organizationhackthone
                String sql2 = "INSERT INTO organizationhackthone(organization_id,hackthone_id) VALUES(?,?)";

                PreparedStatement pst2 = con.prepareStatement(sql2);

                pst2.setInt(1, id);              // Logged-in organization
                pst2.setInt(2, hackathonId);     // Newly added hackathon

                pst2.executeUpdate();

                pst2.close();
            }

            rs.close();
            pst.close();

            System.out.println("Hackathon Added Successfully!");

            notifyAllUsers(
                    con,
                    title,
                    locationCity,
                    mode,
                    startDate,
                    registrationDeadline
            );

            con.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Email every registered user about the new hackathon
    private void notifyAllUsers(Connection con, String title, String locationCity,
                                String mode, Date startDate, Date registrationDeadline) {

        try {

            String userQuery = "SELECT name, email FROM users";

            PreparedStatement userPs = con.prepareStatement(userQuery);

            ResultSet userRs = userPs.executeQuery();

            // Collect all recipients first (email -> name)
            Map<String, String> recipients = new java.util.LinkedHashMap<>();

            while (userRs.next()) {
                recipients.put(userRs.getString("email"), userRs.getString("name"));
            }

            String subject = "New Hackathon Alert: " + title;

            String bodyTemplate =
                    "Hi {name},\n\n"
                            + "A new hackathon has just been posted on HackathonHub!\n\n"
                            + "Title                 : " + title + "\n"
                            + "Location              : " + locationCity + "\n"
                            + "Mode                  : " + mode + "\n"
                            + "Start Date            : " + startDate + "\n"
                            + "Registration Deadline : " + registrationDeadline + "\n\n"
                            + "Log in to HackathonHub to register before the deadline.\n\n"
                            + "- HackathonHub Team";

            // Sends to everyone using a single open SMTP connection
//            Mailer.sendBulkEmails(recipients, subject, bodyTemplate);--------------------------------------------------------

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete Hackathon
    public void deleteHackathon(int id) {

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hackthone",
                    "root",
                    "");

            System.out.print("Enter Hackathon ID to Delete: ");
            int hackathonId = sc.nextInt();
            sc.nextLine();

            // Check whether this organization owns the hackathon
            String check = "SELECT * FROM organizationhackthone WHERE organization_id=? AND hackthone_id=?";

            PreparedStatement pst = con.prepareStatement(check);

            pst.setInt(1, id);
            pst.setInt(2, hackathonId);

            ResultSet rs = pst.executeQuery();

            if (!rs.next()) {

                System.out.println("You cannot delete this hackathon.");

                rs.close();
                pst.close();
                con.close();

                return;
            }

            rs.close();
            pst.close();

            // Delete mapping from organizationhackthone
            String sql1 =
                    "DELETE FROM organizationhackthone WHERE organization_id=? AND hackthone_id=?";

            PreparedStatement pst1 = con.prepareStatement(sql1);

            pst1.setInt(1, id);
            pst1.setInt(2, hackathonId);

            pst1.executeUpdate();

            pst1.close();

            // Delete hackathon
            String sql2 =
                    "DELETE FROM hackathons WHERE hackathon_id=?";

            PreparedStatement pst2 = con.prepareStatement(sql2);

            pst2.setInt(1, hackathonId);

            int rows = pst2.executeUpdate();

            if (rows > 0) {

                System.out.println("Hackathon Deleted Successfully!");

            } else {

                System.out.println("Hackathon Not Found!");

            }

            pst2.close();
            con.close();

        }
        catch (Exception e) {

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

    // Filter Hackathons
    public void filterHackathons() {

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hackthone",
                    "root",
                    "");

            HackathonFilterDAO dao = new HackathonFilterDAO(con);

            System.out.println("\n===== Filter Hackathons =====");
            System.out.println("1. City + Mode + Prize Range");
            System.out.println("2. Search by Title Keyword");
            System.out.println("3. Filter by Skill");
            System.out.println("4. Filter by Domain");
            System.out.println("5. Only Open Seats");
            System.out.println("6. Filter by Status (Upcoming/Ongoing/Closed)");
            System.out.println("7. Trending Hackathons");
            System.out.println("8. Combined Filter (City + Skill)");
            System.out.print("Enter Choice: ");

            int filterChoice = sc.nextInt();
            sc.nextLine();

            List<Map<String, Object>> results = null;

            switch (filterChoice) {

                case 1:
                    System.out.print("Enter City: ");
                    String city = sc.nextLine();
                    System.out.print("Enter Mode (ONLINE/OFFLINE/HYBRID): ");
                    String mode = sc.nextLine();
                    System.out.print("Enter Min Prize: ");
                    double minPrize = sc.nextDouble();
                    System.out.print("Enter Max Prize: ");
                    double maxPrize = sc.nextDouble();
                    sc.nextLine();
                    results = dao.filterByCityModePrize(city, mode, minPrize, maxPrize);
                    break;

                case 2:
                    System.out.print("Enter Keyword: ");
                    String keyword = sc.nextLine();
                    results = dao.searchByTitle(keyword);
                    break;

                case 3:
                    System.out.print("Enter Skill: ");
                    String skill = sc.nextLine();
                    results = dao.filterBySkill(skill);
                    break;

                case 4:
                    System.out.print("Enter Domain: ");
                    String domain = sc.nextLine();
                    results = dao.filterByDomain(domain);
                    break;

                case 5:
                    results = dao.getOpenSeats();
                    break;

                case 6:
                    System.out.print("Enter Status (UPCOMING/ONGOING/CLOSED or leave blank for all): ");
                    String status = sc.nextLine();
                    results = dao.getByStatus(status.isEmpty() ? null : status);
                    break;

                case 7:
                    System.out.print("Enter Limit (e.g. 10): ");
                    int limit = sc.nextInt();
                    sc.nextLine();
                    results = dao.getTrending(limit);
                    break;

                case 8:
                    System.out.print("Enter City: ");
                    String cCity = sc.nextLine();
                    System.out.print("Enter Skill: ");
                    String cSkill = sc.nextLine();
                    results = dao.combinedFilter(cCity, cSkill);
                    break;

                default:
                    System.out.println("Invalid Choice!");
                    con.close();
                    return;
            }

            System.out.println("\n===== Results =====");

            if (results.isEmpty()) {
                System.out.println("No hackathons found.");
            } else {
                for (Map<String, Object> row : results) {
                    System.out.println(row);
                }
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewHackathon(int id) {

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hackthone",
                    "root",
                    "");

            String sql =
                    "SELECT h.hackathon_id, h.title, h.location_city, h.mode, " +
                            "h.prize_pool, h.start_date, h.end_date, " +
                            "h.registration_deadline, h.max_participants, " +
                            "h.current_participants " +
                            "FROM organizationhackthone o " +
                            "JOIN hackathons h " +
                            "ON o.hackthone_id = h.hackathon_id " +
                            "WHERE o.organization_id = ?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            int count = 0;

            System.out.println("\n========== YOUR HACKATHONS ==========");

            while (rs.next()) {

                count++;

                System.out.println("\n-----------------------------------------");
                System.out.println("Hackathon " + count);
                System.out.println("-----------------------------------------");
                System.out.println("Hackathon ID           : " + rs.getInt("hackathon_id"));
                System.out.println("Title                  : " + rs.getString("title"));
                System.out.println("Location               : " + rs.getString("location_city"));
                System.out.println("Mode                   : " + rs.getString("mode"));
                System.out.println("Prize Pool             : " + rs.getDouble("prize_pool"));
                System.out.println("Start Date             : " + rs.getDate("start_date"));
                System.out.println("End Date               : " + rs.getDate("end_date"));
                System.out.println("Registration Deadline  : " + rs.getDate("registration_deadline"));
                System.out.println("Max Participants       : " + rs.getInt("max_participants"));
                System.out.println("Current Participants   : " + rs.getInt("current_participants"));
            }

            if (count == 0) {
                System.out.println("No hackathons found for this organization.");
            }

            rs.close();
            pst.close();
            con.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}