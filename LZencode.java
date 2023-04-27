import java.io.*;

// Class declaration
public class LZencode {

    // Main method
    public static void main(String[] args) {
        // encodeFromConsole();
        long startTime = System.currentTimeMillis();
        try {
            String encoded = encodeFromSTD();
            System.err.println("");
            // byte[] bytes = new byte[encoded.length() / 2];
            // for (int i = 0; i < encoded.length(); i += 2) {
            //     String hex = encoded.substring(i, i + 2);
            //     bytes[i / 2] = (byte) Integer.parseInt(hex, 16);
            // }
            // for (byte b : bytes) {
            //     System.out.print(Integer.toBinaryString(b & 0xFF) + " ");
            // }
        } catch (Exception ex) {
            System.err.println("Exception Occured");
            System.err.println(ex);
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.err.println("Execution time: " + duration + " milliseconds");
        System.err.println("Execution time: " + duration/1000 + " seconds");
    }

    public static String encodeFromSTD() throws IOException {
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
            // printing to see our results
            //System.err.printf("%x %x ", highBits, lowBits);
            //System.err.println("");
        }
        //
        // System.out.println("");
        // System.out.println("Hex Stream: " + hexStream);
        return encodeFromConsole(hexStream);
    }

    public static String encodeFromConsole(String encodeMe) {
        Trie trie = new Trie();
        int currentChar = 0;
        String currentEncode = "";
        String output = "";

        while (currentChar <= encodeMe.length() - 1) {
            currentEncode = currentEncode + encodeMe.charAt(currentChar);
            // System.out.println(currentEncode);
            int exists = trie.find(currentEncode);
            if (exists == -1) {
                int phaseInseted = trie.insert(currentEncode);
                System.err.println(phaseInseted + " " + encodeMe.charAt(currentChar));
                System.out.println(phaseInseted + " " + encodeMe.charAt(currentChar));
                output = output + "(" + (phaseInseted) + "," + encodeMe.charAt(currentChar) + ") ";
                currentEncode = "";
                currentChar++;
                continue;
            };

                currentChar++;
        }
        if (currentEncode.length() != 0) {
            int phaseInseted = trie.find(currentEncode);
            output = output + (phaseInseted) + "$";
            System.err.println(phaseInseted + " $");
            System.out.println(phaseInseted + " $");
            currentEncode = "";
        }    
        if (!(output.charAt(output.length() - 1) == '$')){
            System.out.println("$");
            System.err.println("$");
        }
        System.err.println("");
        //System.err.println(output);
        System.err.println("");
        System.err.println("Max Phase: " + trie.getMaxPhase());
        return output;
    }
}