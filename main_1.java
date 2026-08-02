package pro1;

import java.util.Scanner;

public class main_1 {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n==================================");
            System.out.println("      HACKATHON DISCOVERY PLATFORM");
            System.out.println("==================================");
            System.out.println("1. User");
            System.out.println("2. Organization");
            System.out.println("3. Exit");
            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    // ===== Your Existing User Code =====
                    // Example:
                    //
                    // User u = new User();
                    // u.menu();
                    //
                    // OR
                    // login/register menu

                    Main a=new Main();
                    a.main_user();
                    break;

                case 2:

                    organization org = new organization();
                    org.menu();
                    break;

                case 3:

                    System.out.println("Thank You!");
//                    sc.close();
                    return;

                default:

                    System.out.println("Invalid Choice.");

            }
        }
    }
}