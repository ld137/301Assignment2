import java.io.*;
import java.util.ArrayList;

// Class declaration
public class LZencode {

    // Main method
    public static void main(String[] args) {
        // encodeFromConsole();
        long startTime = System.currentTimeMillis();
        try {
            ArrayList<String> encoded = encodeFromSTD();
            StringBuilder sb = new StringBuilder();
            for (String item : encoded) {
                sb.append(item).append("\n");
            }

            String newLineSeparatedString = sb.toString();
            System.out.println(newLineSeparatedString);
            // #region
            // byte[] bytes = new byte[encoded.length() / 2];
            // for (int i = 0; i < encoded.length(); i += 2) {
            // String hex = encoded.substring(i, i + 2);
            // bytes[i / 2] = (byte) Integer.parseInt(hex, 16);
            // }
            // for (byte b : bytes) {
            // System.out.print(Integer.toBinaryString(b & 0xFF) + " ");
            // }
            // #endregion
        } catch (Exception ex) {
            System.err.println("Exception Occured");
            System.err.println(ex);
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.err.println("Execution time: " + duration + " milliseconds");
        System.err.println("Execution time: " + duration / 1000 + " seconds");
    }

    public static ArrayList<String> encodeFromSTD() throws IOException {
        // get the standard input stream
        InputStream in = System.in;
        int b;
        String hexStream = "";
        // Read input to end
        while ((b = in.read()) != -1) {
            // get high bits
            int highBits = (b >> 4) & 0xf;
            // get low bits
            int lowBits = b & 0xf;
            // convert them to a char and concat them
            String hexDigit = Integer.toHexString(highBits) + Integer.toHexString(lowBits);
            hexStream += hexDigit;

        }
        return encodeFromConsole(hexStream);
    }

    public static ArrayList<String> encodeFromConsole(String encodeMe) {
        Trie trie = new Trie();
        int currentChar = 0;
        String currentEncode = "";
        ArrayList<String> output = new ArrayList<String>();

        while (currentChar <= encodeMe.length() - 1) {
            currentEncode = currentEncode + encodeMe.charAt(currentChar);
            // System.out.println(currentEncode);
            int exists = trie.find(currentEncode);
            if (exists == -1) {
                int phaseInseted = trie.insert(currentEncode);
                System.err.println(phaseInseted + " " + encodeMe.charAt(currentChar));
                output.add((phaseInseted) + " " + Character.digit(encodeMe.charAt(currentChar), 16));
                currentEncode = "";
                currentChar++;
                continue;
            }
            ;
            currentChar++;
        }
        if (currentEncode.length() != 0) {
            int phaseInseted = trie.find(currentEncode);
            output.add((phaseInseted) + " $");
            System.err.println(phaseInseted + " $");
            currentEncode = "";
        }
        String lastItem = output.get(output.size() - 1);
        if (!(lastItem.charAt(lastItem.length() - 1) == '$')) {
            System.err.println("$");
            output.add("$");
        }
        System.err.println("Max Phase: " + trie.getMaxPhase());
        return output;
    }
}