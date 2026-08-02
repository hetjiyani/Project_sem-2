package pro1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class User {

     String name;
     String email;
     String password;
     String city;
     String interest;

    Scanner sc = new Scanner(System.in);
    private int skillId;

    // Register User
    String registerUser() {

        System.out.println("\n===== User Registration =====");

        System.out.print("Enter Name: ");
        name = sc.nextLine();

        System.out.print("Enter Email: ");
        email = sc.nextLine();

        System.out.print("Enter Password: ");
        password = sc.nextLine();

        System.out.print("Enter City: ");
        city = sc.nextLine();

        System.out.print("Enter Interest: ");
        interest = sc.nextLine();



//update data base
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hackthone","root","");// Enter url

            String query = "INSERT INTO users(name, email, password_hash, city) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, city);

            int r=ps.executeUpdate();

            int choice=1;

            do {
                addSkill();

                System.out.println("\n1. Add Another Skill");
                System.out.println("2. Finish");
                System.out.print("Enter Choice: ");
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline


            } while (choice == 1);
            System.out.println("User Registered Successfully!");

            con.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return email;
    }



    // User Login
public boolean loginUser(String loginEmail,String loginPassword) {

        email=loginEmail;
        password=loginPassword;
    try {

        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hackthone",
                "root",
                "");

        System.out.println("\n===== User Login =====");



        String sql = "SELECT * FROM users WHERE email=? AND password_hash=?";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, loginEmail);
        pst.setString(2, loginPassword);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {

            System.out.println("Login Successful.");

            rs.close();
            pst.close();
            con.close();

            return true;
        }
        else {

            System.out.println("Invalid Email or Password.");

            rs.close();
            pst.close();
            con.close();

            return false;
        }

    }
    catch (Exception e) {

        e.printStackTrace();
        return false;

    }

}

    // Edit Profile
    public void editProfile() {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hackthone","root","");

            System.out.println("\n===== Edit Profile =====");
            System.out.println("1. Change City");
            System.out.println("2. Change Password");
            System.out.println("3. Add Skill");
            System.out.println("4. Remove Skill");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter New City: ");
                    city = sc.nextLine();

                    String cityQuery =
                            "UPDATE users SET city=? WHERE email=?";

                    PreparedStatement cityPs =
                            con.prepareStatement(cityQuery);

                    cityPs.setString(1, city);
                    cityPs.setString(2, email);

                    cityPs.executeUpdate();

                    System.out.println("City Updated Successfully!");
                    break;

                case 2:

                    System.out.print("Enter New Password: ");
                    password = sc.nextLine();

                    String passQuery =
                            "UPDATE users SET password_hash=? WHERE email=?";

                    PreparedStatement passPs =
                            con.prepareStatement(passQuery);

                    passPs.setString(1, password);
                    passPs.setString(2, email);

                    passPs.executeUpdate();

                    System.out.println("Password Updated Successfully!");
                    break;

                case 3:

                    addSkill();

                    break;

                case 4:

                    removeSkill();

                    break;

                default:

                    System.out.println("Invalid Choice.");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add Skill
    public void addSkill() {

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hackthone",
                    "root",
                    ""
            );

            System.out.print("Enter Skill: ");
            String skill = sc.nextLine();

            int userId = 0;
            int skillId = 0;

            // Get User ID
            String userQuery = "SELECT user_id FROM users WHERE email = ?";

            PreparedStatement userPs = con.prepareStatement(userQuery);
            userPs.setString(1, email);

            ResultSet userRs = userPs.executeQuery();

            if (userRs.next()) {
                userId = userRs.getInt("user_id");
            } else {
                System.out.println("User Not Found!");
                con.close();
                return;
            }

            // Check if skill exists
            String skillQuery = "SELECT skill_id FROM skills WHERE skill_name = ?";

            PreparedStatement skillPs = con.prepareStatement(skillQuery);
            skillPs.setString(1, skill);

            ResultSet skillRs = skillPs.executeQuery();

            if (skillRs.next()) {

                skillId = skillRs.getInt("skill_id");

            } else {

                // Insert new skill
                String insertSkill = "INSERT INTO skills(skill_name) VALUES(?)";

                PreparedStatement insertPs = con.prepareStatement(insertSkill);
                insertPs.setString(1, skill);
                insertPs.executeUpdate();

                // Get skill_id
                PreparedStatement getSkillPs = con.prepareStatement(skillQuery);
                getSkillPs.setString(1, skill);

                ResultSet newSkillRs = getSkillPs.executeQuery();

                if (newSkillRs.next()) {
                    skillId = newSkillRs.getInt("skill_id");
                }
            }

            // Check if user already has this skill
            String checkUserSkill =
                    "SELECT * FROM userskills WHERE user_id=? AND skill_id=?";

            PreparedStatement checkPs = con.prepareStatement(checkUserSkill);

            checkPs.setInt(1, userId);
            checkPs.setInt(2, skillId);

            ResultSet checkRs = checkPs.executeQuery();

            if (checkRs.next()) {

                System.out.println("Skill Already Exists!");

            } else {

                String userSkillQuery =
                        "INSERT INTO userskills(user_id, skill_id, proficiency_level) VALUES(?,?,?)";

                PreparedStatement userSkillPs = con.prepareStatement(userSkillQuery);

                userSkillPs.setInt(1, userId);
                userSkillPs.setInt(2, skillId);
                userSkillPs.setString(3, "Beginner");

                userSkillPs.executeUpdate();

                System.out.println("Skill Added Successfully!");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Remove Skill
    public void removeSkill() {

        try {

            Connection con = con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hackthone","root","");

            System.out.print("Enter Skill to Remove: ");
            String skill = sc.nextLine();

            PreparedStatement userPs =
                    con.prepareStatement("SELECT user_id FROM users WHERE email=?");

            userPs.setString(1, email);

            ResultSet userRs = userPs.executeQuery();

            int userId = 0;

            if (userRs.next()) {
                userId = userRs.getInt("user_id");
            }

            PreparedStatement skillPs =
                    con.prepareStatement("SELECT skill_id FROM skills WHERE skill_name=?");

            skillPs.setString(1, skill);

            ResultSet skillRs = skillPs.executeQuery();

            if (!skillRs.next()) {

                System.out.println("Skill Not Found!");
                return;

            }

            int skillId = skillRs.getInt("skill_id");

            PreparedStatement deletePs =
                    con.prepareStatement(
                            "DELETE FROM userskills WHERE user_id=? AND skill_id=?");

            deletePs.setInt(1, userId);
            deletePs.setInt(2, skillId);

            int rows = deletePs.executeUpdate();

            if (rows > 0){
//                skills.remove(skill);
                System.out.println("Skill Removed Successfully!");
            }

            else
                System.out.println("Skill Not Found!");

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    // View Profile
    public void viewProfile() {

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hackthone",
                    "root",
                    ""
            );

            // Display User Information
            String userQuery = "SELECT * FROM users WHERE email=?";

            PreparedStatement userPs = con.prepareStatement(userQuery);
            userPs.setString(1, email);

            ResultSet userRs = userPs.executeQuery();

            if (!userRs.next()) {
                System.out.println("User Not Found!");
                con.close();
                return;
            }

            int userId = userRs.getInt("user_id");

            System.out.println("\n===== User Profile =====");
            System.out.println("Name      : " + userRs.getString("name"));
            System.out.println("Email     : " + userRs.getString("email"));
            System.out.println("City      : " + userRs.getString("city"));

            // Display Skills
            System.out.print("Skills    : ");

            String skillQuery =
                    "SELECT s.skill_name " +
                            "FROM userskills us " +
                            "JOIN skills s ON us.skill_id = s.skill_id " +
                            "WHERE us.user_id=?";

            PreparedStatement skillPs = con.prepareStatement(skillQuery);
            skillPs.setInt(1, userId);

            ResultSet skillRs = skillPs.executeQuery();

            boolean found = false;

            while (skillRs.next()) {

                found = true;
                System.out.print(skillRs.getString("skill_name") + "  ");

            }

            if (!found) {
                System.out.print("No Skills Added");
            }

            System.out.println();

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}