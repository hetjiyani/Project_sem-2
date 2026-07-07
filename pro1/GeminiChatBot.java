import java.io.*;
import java.net.*;
import java.util.Scanner;


public class GeminiChatBot {

    public static void main(String[] args) {

        String apiKey = "AIzaSyCpNbcbKidtJmB9kolhkeJ84Gw2Wk_6ECc";
        String model = "gemini-2.5-flash"; // ✅ ONLY THIS

        Scanner scanner = new Scanner(System.in);
        System.out.println("🤖 Gemini AI Chatbot (FREE)");
        System.out.println("Type 'exit' to quit");

        while (true) {
            System.out.print("\nYou: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) break;

            try {
                String endpoint =
                        "https://generativelanguage.googleapis.com/v1/models/"
                                + model + ":generateContent?key=" + apiKey;

                String jsonBody =
                        "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + input + "\" }] }] }";

                HttpURLConnection conn =
                        (HttpURLConnection) new URL(endpoint).openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(jsonBody.getBytes("UTF-8"));
                }

                int status = conn.getResponseCode();

                InputStream is = (status >= 400)
                        ? conn.getErrorStream()
                        : conn.getInputStream();

                BufferedReader br =
                        new BufferedReader(new InputStreamReader(is, "UTF-8"));

                String line;
                StringBuilder response = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                if (status >= 400) {
                    System.out.println("API Error (" + status + "): " + response);
                    continue;
                }

                String res = response.toString();
                int i = res.indexOf("\"text\":");
                int start = res.indexOf("\"", i + 7) + 1;
                int end = res.indexOf("\"", start);

                System.out.println("Gemini: " + res.substring(start, end));

            } catch (Exception e) {
                System.out.println("Java Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
