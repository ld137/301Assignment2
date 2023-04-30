
/*
 * Lleyton Damon - 1585670
 * Jake Postlewaight - 1590698
 */

import java.io.*;
import java.util.ArrayList;

// Class declaration
public class LZencode {

    // Main method
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try {
            //encoded from the standard input
            ArrayList<String> encoded = encodeFromSTD();
            StringBuilder sb = new StringBuilder();
            //create a new line seperated string
            for (String item : encoded) {
                sb.append(item).append("\n");
            }
            //output the string to the standard output
            String newLineSeparatedString = sb.toString();
            System.out.println(newLineSeparatedString);
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
        return processString(hexStream);
    }

    /*
     * Processes a string and adds it to a trie data structure to then go and encode it
     * 
     * @param encodeMe the number of bits to extract
     * @return An array list containing the encoded strings with the format - INT INT
     */
    public static ArrayList<String> processString(String encodeMe) {
        //create a new trie
        Trie trie = new Trie();
        int currentChar = 0; //current place we are in the string
        String currentEncode = ""; //the current set of characters we are encoding
        ArrayList<String> output = new ArrayList<String>();
        //while we arent finished reading keep reading
        while (currentChar <= encodeMe.length() - 1) {
            //current encode is the set of characters we are at encoding
            currentEncode = currentEncode + encodeMe.charAt(currentChar);
            //check if the current set of characters exists in the trie
            int exists = trie.find(currentEncode);
            //if it doesnt exist, we want to add it
            if (exists == -1) {
                int phaseInseted = trie.insert(currentEncode);
                output.add((phaseInseted) + " " + Character.digit(encodeMe.charAt(currentChar), 16));
                //we just inserted, so we can clear the string we are searching so far
                currentEncode = "";
                currentChar++;
                continue;
            };
            //increase the character we are at
            currentChar++;
        }
        //make sure we got it all if not add whatever is left over
        if (currentEncode.length() != 0) {
            int phaseInseted = trie.find(currentEncode);
            output.add((phaseInseted) + " $");
            currentEncode = "";
        }
        //add our end of stream character '$'
        String lastItem = output.get(output.size() - 1);
        if (!(lastItem.charAt(lastItem.length() - 1) == '$')) {
            // System.err.println("$");
            output.add("$");
        }
        System.err.println("Max Phase: " + trie.getMaxPhase());
        return output;
    }
}