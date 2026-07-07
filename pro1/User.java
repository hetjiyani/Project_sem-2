import java.util.ArrayList;
import java.util.Scanner;

public class User {

    // private int userId;
    private String name;
    private String email;
    private String password;
    private String city;
    private String Intrest;
    private ArrayList<String> skills = new ArrayList<>();

    Scanner sc = new Scanner(System.in);

    // Add New User
    public void addNewUser() {
        // System.out.print("Enter User ID: ");
        // userId = sc.nextInt();
        // sc.nextLine();

        System.out.print("Enter Name: ");
        name = sc.nextLine();

        System.out.print("Enter Email: ");
        email = sc.nextLine();

        System.out.print("Enter Password: ");
        password = sc.nextLine();

        System.out.print("Enter City: ");
        city = sc.nextLine();

        System.out.print("Enter Intrest field: ");
        Intrest = sc.nextLine();

        System.out.print("Enter Skill: ");
        skills.add(sc.nextLine());

        System.out.println("User Added Successfully!");
    }

    // Edit Profile
    public void editProfile() {
        System.out.print("Enter New City: ");
        city = sc.nextLine();

        System.out.print("Enter New Domain: ");
        Intrest = sc.nextLine();

        System.out.print("Enter New Skill: ");
        skills.clear();
        skills.add(sc.nextLine());

        System.out.println("Profile Updated Successfully!");
    }

    // View Profile
    public void viewProfile() {
        System.out.println("\n----- User Profile -----");
        // System.out.println("User ID : " + userId);
        System.out.println("Name    : " + name);
        System.out.println("Email   : " + email);
        System.out.println("City    : " + city);
        System.out.println("Domain  : " + Intrest);
        System.out.println("Skills  : " + skills);
    }
}