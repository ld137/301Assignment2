import java.io.*;

public class LZunpack {
    public static void main(String[] args) throws IOException {

        InputStream in = System.in;
        int b;
        int maxLen = 0;
        String hexStream = "";
        boolean loop = true;

        while(loop){
            b = in.read();

            // get high bits
            int highBits = (b >> 4) & 0xf;
            // get low bits
            int lowBits = b & 0xf;

            if((lowBits & 0) != 0){
                maxLen += 8;
            }
            else{
                loop = false;
            }

            if((highBits & 0) == 0){
                loop = false;
            }
        }

        // Read input to end
        while ((b = in.read()) != -1) {
            hexStream = "";
            for(int i = 0; i < maxLen / 8; i++){
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
}
