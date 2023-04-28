
//Impliment
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.charset.StandardCharsets;

public class LZdecode {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();
        char stopChar = '$';
        StringBuilder output = new StringBuilder();
        Map<Integer, String> dictionary = new HashMap<>();
        int nextIndex = 1;
        // Read input until the stop character is encountered
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.indexOf(stopChar) != -1) {
                // Stop character found, append input up to stop character and stop
                lines.add(line.substring(0, line.indexOf(stopChar)) + stopChar);
                break;
            } else {
                // Stop character not found, add line to the array
                lines.add(line);
            }
        }

        for (String line : lines) {
            if (line.compareTo("") == 0) continue;
            if (line.compareTo("\n") == 0) continue;
            System.err.println("Decoding: " + line);
            String[] parts = line.split(" ");
            if (parts[0].compareTo("$") == 0) break;
            int phase = Integer.parseInt(parts[0]);
            String character = parts[1];

            String decodedString;
            if (phase == 0) {
                decodedString = character;
            } else {
                if (character.compareTo("$") == 0){
                    decodedString = dictionary.get(phase);
                }else {
                    decodedString = dictionary.get(phase) + character;
                }
            }

            output.append(decodedString);
            dictionary.put(nextIndex, decodedString);
            nextIndex++;
        }

        byte[] byteArray = new byte[output.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            int index = i * 2;
            int value = Integer.parseInt(output.substring(index, index + 2), 16);
            byteArray[i] = (byte) value;
        }

        // next, convert the byte array into a string
        String text = new String(byteArray, StandardCharsets.UTF_8);
        System.out.println(text.toString());
    }
}