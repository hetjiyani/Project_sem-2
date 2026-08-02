package pro1;


import jakarta.mail.*;
        import jakarta.mail.internet.*;
        import java.util.Properties;

public class Mailer extends Thread{

    // Replace with your Gmail
    private static final String FROM_EMAIL = "hetjiyanipro@gmail.com";

    // Replace with your 16-character App Password
    private static final String APP_PASSWORD = "reev gjal ywje ynji";

    String to;
    String subject;
    String body;

    public Mailer(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        start();
    }

    public void run() {

        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM_EMAIL));

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to));

            message.setSubject(subject);

            message.setText(body);

            Transport.send(message);

//            System.out.println("Email sent successfully!");

        } catch (Exception e) {
            System.out.println("Email sending failed.");
            e.printStackTrace();
        }
    }

//________________________________________________________________________
//    public static void main(String[] args) {
//
//        Mailer.sendEmail(
//                "hetjiyani24@gmail.com",
//                "Java Email Test",
//                "Hello!\n\nThis email is sent from Java using Gmail SMTP.\n\nThank you!"
//        );
//
//    }
    //_____________________________________________________________________________________
}