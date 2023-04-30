import java.io.*;
import java.util.ArrayList;

public class LZunpack {
    public static void main(String[] args) throws IOException {

        InputStream in = System.in;
        int b;
        int maxLen = 0;
        String hexStream = "";
        boolean loop = true;

        // While reading
        // Take in the bytes, current dict = 1 until Ceil log P > 1
        // bits 0-log p in the upper are the dict, and then next 4 are the hex
        // rinse and repeat until end of stream, when you get to the end of stream,
        // there might be leftover space,
        // so when reading in the 'final' phrase, if you get past the current log p
        // value disregard progress and consider the read complete

        int currentMaxDict = 0;
        int fullInput = 0;
        int currentBytes = 1;
        int remainder = 0;
        ArrayList<String> output = new ArrayList<>();
        int log2x = (int) Math.ceil(Math.log(currentMaxDict == 0 ? 1 : currentMaxDict) / Math.log(2));
        while ((b = in.read()) != -1) {
            remainder = remainder + 8;
            // System.err.println("Remainder: " + remainder + " Log2x: " + log2x);
            // Get the current minimum size the phrase can be in bits
            log2x = (int) Math.ceil(Math.log(currentMaxDict == 0 ? 1 : currentMaxDict) / Math.log(2));
            // if the phrase is 0 set it to 1;
            log2x = (log2x == 0 ? 1 : log2x);

            int shiftMe = b << ((32) - remainder);
            fullInput = fullInput | shiftMe;

            if (remainder < (log2x+4)) continue;

            int phrase = getXMSBits(log2x, fullInput);
            fullInput = fullInput << log2x;
            int hex = getXMSBits(4, fullInput);
            output.add(phrase + " " + hex);
            fullInput = fullInput << 4;

            //Modify the bit remainder so that we dont overwrite our other bits
            remainder = remainder - log2x - 4;
            currentMaxDict++;

        }
        // System.err.println(fullInput);
        if (fullInput != 0) {
            int phrase = getXMSBits(log2x, fullInput);
            output.add(phrase + " " + "$");
        }

        StringBuilder sb = new StringBuilder();
        for (String item : output) {
            sb.append(item).append("\n");
        }
        int lineCounter = 0;
        StringBuilder debugString = new StringBuilder();
        for (String item : output) {
            debugString.append("[" + lineCounter + "]" + item).append("\n");
            lineCounter++;
        }

        String newLineSeparatedString = sb.toString();
        // System.err.println(debugString.toString());
        System.out.println(newLineSeparatedString);
    }

    public static void printPaddedBinary(int binaryNum, int padding, String display) {
        String paddedString = String.format("%" + padding + "s", Integer.toBinaryString(binaryNum)).replace(' ', '0');
        System.err.println(display + ": " + paddedString);
    }

    public static int getXMSBits(int numBits, int num) {
        int shiftedNum = num >> (32 - numBits);
        int maskedNum = shiftedNum & ((1 << numBits) - 1);
        return (int) maskedNum;
    }
}