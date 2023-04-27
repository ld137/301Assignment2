//Impliment
import java.io.*;
import java.nio.charset.StandardCharsets;

public class LZdecode{
    public static void main(String[] args) {
        try {
            InputStream inputStream = System.in;
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            String output = "";
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                String line = new String(buffer, 0, bytesRead, StandardCharsets.US_ASCII);
                if (line.contains("\n")) {
                    // Found newline character
                    line = line.substring(0, line.indexOf('\n'));
                }
                byte[] asciiBytes = line.getBytes(StandardCharsets.US_ASCII);
                // Do something with the line of ASCII-encoded bytes
                String lineText = new String(asciiBytes, StandardCharsets.US_ASCII);
                output = output + "-" + lineText + '\n';
                System.out.println("Read line as ASCII bytes: " + lineText);
                if (line.contains("\n")) {
                    // Exit the loop if a complete line has been read
                    break;
                }
            }
            System.out.println(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}