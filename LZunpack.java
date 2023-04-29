import java.io.*;
import java.util.ArrayList;

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
        long fullInput = 0;
        int currentBytes = 1;
        ArrayList<String> output = new ArrayList<>();
        while ((b = in.read()) != -1){
            System.err.println(Integer.toBinaryString(b));
            int log2x = (int) Math.ceil(Math.log(currentMaxDict == 0 ? 1 : currentMaxDict) / Math.log(2));

            fullInput += b;

            if(log2x+4 > (currentBytes * 8)){
                fullInput = fullInput << 8;
                currentBytes++;
                continue;
            }
            
            long bitshift = ((2^log2x)-1) << (64-log2x);
            fullInput = fullInput << (8 - currentBytes) * 8;
            int phrase = (int)(fullInput & bitshift);
            fullInput = fullInput << log2x;
            int hex = ((int)(fullInput >> 60)) & 0x000000FF;
            
            output.add(phrase + " " + hex);
            
            currentMaxDict++;
            currentBytes = 1;
        }
        StringBuilder sb = new StringBuilder();
            for (String item : output) {
                sb.append(item).append("\n");
            }

        String newLineSeparatedString = sb.toString();
        System.out.println(newLineSeparatedString);
    }
}
