
/*
 * Lleyton Damon - 1585670
 * Jake Postlewaight - 1590698
 */

import java.io.*;
import java.util.ArrayList;

public class LZunpack {
    public static void main(String[] args) throws IOException {

        InputStream in = System.in; //our input stream
        int b; //to hold the byte we read in
        int currentMaxDict = 0; //current max dictionary size
        int fullInput = 0; //where we are storing all our bits
        int remainder = 0; //how many bits are left over after we have pulled out what we need
        ArrayList<String> output = new ArrayList<>(); //to hold the output while we process

        //Log2(p) gives the minimum amount of bits that the phrase can possibly be
        int log2x = (int) Math.ceil(Math.log(currentMaxDict == 0 ? 1 : currentMaxDict) / Math.log(2));
        //read until end of stream
        while ((b = in.read()) != -1) {
            //incriment the remainder by 8 because we just read 8 bits
            remainder = remainder + 8;
            // Get the current minimum size the phrase can be in bits
            log2x = (int) Math.ceil(Math.log(currentMaxDict == 0 ? 1 : currentMaxDict) / Math.log(2));
            // if the phrase is 0 set it to 1;
            log2x = (log2x == 0 ? 1 : log2x);
            // Shift the int up the amount of bits we need to be able to add it to the
            // remainder we have stored in full input
            int shiftMe = b << ((32) - remainder);
            // Add the newly read & shifted value into the full input
            fullInput = fullInput | shiftMe;

            // If the remainder is less than how much we want to process do the loop again
            if (remainder < (log2x + 4))
                continue;

            // get the X most significant bits of full input
            int phrase = getXMSBits(log2x, fullInput);
            // shift out those bits we just got
            fullInput = fullInput << log2x;
            // get the hex codes value out
            int hex = getXMSBits(4, fullInput);
            // shift that out
            fullInput = fullInput << 4;
            // save our depacked values
            output.add(phrase + " " + hex);

            // Modify the bit remainder so that we dont overwrite our other bits
            remainder = remainder - log2x - 4;
            // increase our potential max dict
            currentMaxDict++;

        }
        // when we read the last byte we might have not completed yet, so just do a new
        // check
        if (fullInput != 0) {
            int phrase = getXMSBits(log2x, fullInput);
            output.add(phrase + " " + "$");
        }

        // Take our list and make it all into a new line seperated output
        StringBuilder sb = new StringBuilder();
        for (String item : output) {
            sb.append(item).append("\n");
        }

        String newLineSeparatedString = sb.toString();
        // print out the unpacked values
        System.out.println(newLineSeparatedString);
    }

    public static void printPaddedBinary(int binaryNum, int padding, String display) {
        String paddedString = String.format("%" + padding + "s", Integer.toBinaryString(binaryNum)).replace(' ', '0');
        System.err.println(display + ": " + paddedString);
    }

    /**
     * Extracts the specified number of bits from the input integer, starting from
     * the leftmost bit (most significant bit).
     *
     * @param numBits the number of bits to extract
     * @param num     the integer value from which to extract the bits
     * @return an integer value representing the extracted bits
     */
    public static int getXMSBits(int numBits, int num) {
        int shiftedNum = num >> (32 - numBits);
        int maskedNum = shiftedNum & ((1 << numBits) - 1);
        return (int) maskedNum;
    }
}