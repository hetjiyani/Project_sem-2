package pro1;

import java.sql.*;
import java.util.Scanner;

public class organization {

    Scanner sc = new Scanner(System.in);
    Connection con;

    public organization() throws Exception {

        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hackthone",
                "root",
                "");

    }

    // ===================== MAIN MENU =====================
    public void menu() throws Exception {

        while (true) {

            System.out.println("\n========== ORGANIZATION ==========");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter Choice : ");

            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    registerOrganization();
                    break;

                case 2:

                    int id = loginOrganization();

                    if (id != -1)
                        organizationMenu(id);

                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }

        }

    }

    // ===================== REGISTER =====================
    public void registerOrganization() throws SQLException {

        System.out.print("Enter Organization ID : ");
        int id = sc.nextInt();
        sc.nextLine();   // Consume newline

        System.out.print("Enter Organization Name : ");
        String name = sc.nextLine();

        System.out.print("Enter Password : ");
        String password = sc.nextLine();

        // Check if organization already exists
        String check = "SELECT * FROM organization WHERE organization_id=?";

        PreparedStatement pst = con.prepareStatement(check);
        pst.setInt(1, id);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {

            System.out.println("Organization already registered.");

            rs.close();
            pst.close();
            return;
        }

        rs.close();
        pst.close();

        // Insert new organization
        String sql = "INSERT INTO organization(organization_id, passward, name) VALUES(?,?,?)";

        pst = con.prepareStatement(sql);

        pst.setInt(1, id);
        pst.setString(2, password);
        pst.setString(3, name);

        int rows = pst.executeUpdate();

        if (rows > 0) {
            System.out.println("Registration Successful.");
        } else {
            System.out.println("Registration Failed.");
        }

        pst.close();
    }

    // ===================== LOGIN =====================
    public int loginOrganization() throws SQLException {

        System.out.print("Enter Organization ID : ");
        int id = sc.nextInt();

        System.out.print("Enter Password : ");
        String password = sc.next();

        String sql =
                "SELECT * FROM organization WHERE organization_id=? AND passward=?";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);
        pst.setString(2, password);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {

            System.out.println("Login Successful.");

            rs.close();
            pst.close();

            return id;

        }

        rs.close();
        pst.close();

        System.out.println("Invalid ID or Password.");
        System.out.println("Please Register First.");

        return -1;

    }

    // ===================== ORGANIZATION MENU =====================
    public void organizationMenu(int organizationId) throws Exception {

        Hackathon h = new Hackathon();

        while (true) {

            System.out.println("\n========== ORGANIZATION PANEL ==========");
            System.out.println("1. Add Hackathon");
            System.out.println("2. Delete Hackathon");
            System.out.println("3. View My Hackathons");
            System.out.println("4. Logout");
            System.out.print("Enter Choice : ");

            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    h.addHackathon(organizationId);
                    break;

                case 2:
                    h.deleteHackathon(organizationId);
                    break;

                case 3:
                    h.viewHackathon(organizationId);
                    break;

                case 4:
                    System.out.println("Logged Out Successfully.");
                    return;

                default:
                    System.out.println("Invalid Choice.");

            }

        }

    }

}