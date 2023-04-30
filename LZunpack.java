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
            printPaddedBinary(b, 8, "Read In");
            log2x = (int) Math.ceil(Math.log(currentMaxDict == 0 ? 1 : currentMaxDict) / Math.log(2));
            log2x = (log2x == 0 ? 1 : log2x);
            System.err.println("log2x: " + log2x);
            int shiftMe = b<<((3*8)-remainder);
            System.err.println("Remainder: " + remainder);
            fullInput = fullInput | shiftMe;
            remainder = (8+remainder) - log2x - 4;
            if (log2x >= (currentBytes*8)) {
                currentBytes++;
                continue;
            };
            currentBytes = 1;
            // Shift the fullInput up by (3x8) - Bit remainder
            //fullInput = fullInput << ((3*8)-remainder);
            printPaddedBinary(fullInput, 32, "Fullinput");
            int phrase = getXMSBits(log2x, fullInput);
            fullInput = fullInput << log2x;
            int hex = getXMSBits(4, fullInput);
            output.add(phrase + " " + hex);
            fullInput = fullInput << 4;
            printPaddedBinary(phrase, log2x, "Phrase");
            printPaddedBinary(hex, 4, "Hex");
            printPaddedBinary(fullInput, 32, "Fullinput");
            System.err.println("-");
            currentMaxDict++;

        }
        //System.err.println(fullInput);
        if (fullInput != 0){
            int phrase = getXMSBits(log2x, fullInput);
            output.add(phrase + " " + "$");
        }

        StringBuilder sb = new StringBuilder();
        for (String item : output) {
            sb.append(item).append("\n");
        }

        String newLineSeparatedString = sb.toString();
        System.out.println(newLineSeparatedString);
    }

    public static void printPaddedBinary(int binaryNum, int padding, String display) {
        String paddedString = String.format("%" + padding + "s", Integer.toBinaryString(binaryNum)).replace(' ', '0');
        System.err.println(display + ": " + paddedString);
    }

    public static int getXMSBits(int numBits, int num) {
        int shiftedNum = num >> (Integer.SIZE - numBits);
        int maskedNum = shiftedNum & ((1 << numBits) - 1);
        return maskedNum;
    }
}