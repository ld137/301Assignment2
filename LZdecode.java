//Impliment
import java.io.*;

public class LZdecode{
    public static void main(String[] args) throws IOException{
        InputStream in = System.in;
        int b;
        String hexStream = "";
        //Read input to end
        while ((b = in.read()) != -1) {
            //get high bits
            int highBits = (b >> 4) & 0xf;
            //get low bits
            int lowBits = b & 0xf;
            //convert them to a char and concat them
            String hexDigit = Integer.toHexString(highBits) + Integer.toHexString(lowBits);
            hexStream += hexDigit;
            //printing to see our results
            System.out.printf("%x %x ", highBits, lowBits);
        }
        System.out.println("");
        System.out.println(hexStream);
    }
}