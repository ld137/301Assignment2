
/*
 * Lleyton Damon - 1585670
 * Jake Postlewaight - 1590698
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Scanner;

public class LZpack {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in); //Initialize the reader
        ArrayList<String> lines = new ArrayList<>(); //initialize the list to hold the input
        char stopChar = '$'; //the character that will be transmitted when we reach the end

        // Read input until the stop character is encountered
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            //System.err.println(line);
            if (line.indexOf(stopChar) != -1) {
                // Stop character found, append input up to stop character and stop
                lines.add(line.substring(0, line.indexOf(stopChar)) + stopChar);
                break;
            } else {
                // Stop character not found, add line to the array
                lines.add(line);
            }
        }
        //close our scanner
        scanner.close();
        //create a binary output string (bit easier to manipulate)
        String outputBinaryString = "";
        int currentMaxDict = 0;
        for (String line : lines) {
            //split the line to get the phrase and the char
            String[] parts = line.split(" ");
            //check we arent at the end
            if (parts[0].compareTo("$") == 0) break;
            //get the phrase number out
            int phrase = Integer.parseInt(parts[0]);
            //get the character number out, or 0 if we are at the end 
            int hexCode = parts[1].compareTo("$") == 0 ? 0 : Integer.parseInt(parts[1]);
            //calculate how many bits we need to store this value
            int log2x = (int) Math.ceil(Math.log(currentMaxDict == 0 ? 1 : currentMaxDict) / Math.log(2));
            //get the phrase into some bits
            int phraseMask = (1 << log2x) - (phrase == 0 ? 0 : 1);
            int phraseResult = phrase & phraseMask;
            //get the hex into some bits
            int hexMask = 0b1111;
            int hexResult = (hexCode == -1 ? 0 : hexCode) & hexMask;

            //padd out the hex to make sure its 4 characters long
            String paddedHex = String.format("%1$" + 4 + "s", Integer.toBinaryString(hexResult)).replace(' ', '0');
            //pad out the phrase so its X characters long (where x is the value of log2x)
            String paddedPhrase = String
                    .format("%1$" + (log2x == 0 ? 1 : log2x) + "s", Integer.toBinaryString(phraseResult))
                    .replace(' ', '0');
            //add to the output string
            outputBinaryString += paddedPhrase + paddedHex;
            //incriment the dictionary
            currentMaxDict++;
        }
        //create a new bitset to create the byte array for output
        BitSet bitSet = new BitSet(outputBinaryString.length());
        //loop over the output and turn on bits on where the string has a 1
        for (int i = 0; i < outputBinaryString.length(); i++) {
            if (outputBinaryString.charAt(i) == '1') {
                bitSet.set(i);
            }
        }
        //Create a byte array big enough to fit it all
        int numBytes = (int) Math.ceil((bitSet.length()+7) / 8.0);
        byte[] byteArray = new byte[numBytes];
        //For whatever reason bitset.tobytearray messes up the bits, so need to iterate over it to make this work properly
        for (int i = 0; i < bitSet.length(); i++) {
            if (bitSet.get(i)) {
                int byteIndex = i / 8;
                int bitIndex = i % 8;
                byteArray[byteIndex] |= (1 << (7 - bitIndex));
            }
        }
        //write out the byteArray
        System.out.write(byteArray);
        System.out.flush();

    }
}