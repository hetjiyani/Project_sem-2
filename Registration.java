package pro1;

import java.sql.*;
import java.util.Scanner;

public class Registration {

    Scanner sc = new Scanner(System.in);

    public void registerHackathon(String userEmail) {

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hackthone",
                    "root",
                    ""
            );

            // Get User ID
            int userId = 0;

            String userQuery = "SELECT user_id FROM users WHERE email=?";

            PreparedStatement userPs = con.prepareStatement(userQuery);

            userPs.setString(1, userEmail);

            ResultSet userRs = userPs.executeQuery();

            if (userRs.next()) {
                userId = userRs.getInt("user_id");
            } else {
                System.out.println("User Not Found!");
                con.close();
                return;
            }

            // Show Hackathons
            String hackQuery =
                    "SELECT hackathon_id,title,current_participants,max_participants FROM hackathons";

            PreparedStatement hackPs = con.prepareStatement(hackQuery);

            ResultSet hackRs = hackPs.executeQuery();

            System.out.println("\n========== Available Hackathons ==========");

            while (hackRs.next()) {

                System.out.println("------------------------------------");
                System.out.println("Hackathon ID : " + hackRs.getInt("hackathon_id"));
                System.out.println("Title : " + hackRs.getString("title"));
                System.out.println("Participants : "
                        + hackRs.getInt("current_participants")
                        + "/"
                        + hackRs.getInt("max_participants"));
            }

            System.out.print("\nEnter Hackathon ID: ");
            int hackathonId = sc.nextInt();
            sc.nextLine();

            // Already Registered?
            String checkQuery =
                    "SELECT * FROM registration WHERE user_id=? AND hackathon_id=?";

            PreparedStatement checkPs = con.prepareStatement(checkQuery);

            checkPs.setInt(1, userId);
            checkPs.setInt(2, hackathonId);

            ResultSet checkRs = checkPs.executeQuery();

            if (checkRs.next()) {

                System.out.println("You are already registered!");
                con.close();
                return;
            }

            // Capacity
            int current = 0;
            int max = 0;

            String capacityQuery =
                    "SELECT current_participants,max_participants FROM hackathons WHERE hackathon_id=?";

            PreparedStatement capPs = con.prepareStatement(capacityQuery);

            capPs.setInt(1, hackathonId);

            ResultSet capRs = capPs.executeQuery();

            if (capRs.next()) {

                current = capRs.getInt("current_participants");
                max = capRs.getInt("max_participants");
            }

            String status;
            int waitlistPosition = 0;

            if (current < max) {

                status = "Registered";

                String updateQuery =
                        "UPDATE hackathons SET current_participants=current_participants+1 WHERE hackathon_id=?";

                PreparedStatement updatePs =
                        con.prepareStatement(updateQuery);

                updatePs.setInt(1, hackathonId);

                updatePs.executeUpdate();

            } else {

                status = "Waitlisted";

                String waitQuery =
                        "SELECT COUNT(*) FROM registration WHERE hackathon_id=? AND status='Waitlisted'";

                PreparedStatement waitPs =
                        con.prepareStatement(waitQuery);

                waitPs.setInt(1, hackathonId);

                ResultSet waitRs = waitPs.executeQuery();

                if (waitRs.next()) {

                    waitlistPosition = waitRs.getInt(1) + 1;
                }
            }

            // Register
            String registerQuery =
                    "INSERT INTO registration(user_id,hackathon_id,status,waitlist_position) VALUES(?,?,?,?)";

            PreparedStatement registerPs =
                    con.prepareStatement(registerQuery);

            registerPs.setInt(1, userId);
            registerPs.setInt(2, hackathonId);
            registerPs.setString(3, status);

            if (status.equals("Registered"))
                registerPs.setNull(4, Types.INTEGER);
            else
                registerPs.setInt(4, waitlistPosition);

            registerPs.executeUpdate();

            if (status.equals("Registered")) {
                mail_for_joining m = new mail_for_joining();
                m.mail_join(userId, hackathonId);
                System.out.println("Hackathon Registration Successful!");
            } else {
                System.out.println("Hackathon Full!");
                System.out.println("You are Waitlisted.");
                System.out.println("Waitlist Position : " + waitlistPosition);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}