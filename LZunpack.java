import java.io.*;

public class LZunpack {
    public static void main(String[] args) throws IOException {

        InputStream in = System.in;
        int b;
        int maxLen = 0;
        String hexStream = "";
        boolean loop = true;
        
        //While reading
        //Take in the bytes, current dict = 1 until Ceil log P > 1
        //bits 0-log p in the upper are the dict, and then next 4 are the hex
        //rinse and repeat until end of stream, when you get to the end of stream, there might be leftover space, 
        //so when reading in the 'final' phrase, if you get past the current log p value disregard progress and consider the read complete

        int currentMaxDict = 0;

        while (loop) {
            
            int log2x = (int) Math.ceil(Math.log(currentMaxDict == 0 ? 1 : currentMaxDict) / Math.log(2));
            
            b = in.read();
            // get high bits
            int highBits = (b >> 4) & 0xf;
            // get low bits
            int lowBits = b & 0xf;

            if ((lowBits & 0) != 0) {
                maxLen += 8;
            } else {
                loop = false;
            }

            if ((highBits & 0) == 0) {
                loop = false;
            }
        }

        // Read input to end
        while ((b = in.read()) != -1) {
            hexStream = "";
            for (int i = 0; i < maxLen / 8; i++) {
                // get high bits
                int highBits = (b >> 4) & 0xf;
                // get low bits
                int lowBits = b & 0xf;
                // convert them to a char and concat them
                String hexDigit = Integer.toHexString(highBits) + Integer.toHexString(lowBits);
                hexStream += hexDigit;
            }
            System.err.println(hexStream);
        }
    }
}
