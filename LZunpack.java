import java.io.*;

public class LZunpack {
    public static void main(String[] args) throws IOException {

        InputStream in = System.in;
        int b;
        int maxLen = 0;
        String hexStream = "";
        boolean loop = true;

        //Loop through the input bits continuously until end of stream (in.read returns -1 when you get to the end)

        //Inside the loop,
        //We need to read in bytes until we get a byte that has any amount of '0s' in it
        //IE b = in.read() < 255 
        //counting each byte as we go over it.
        //then we need to get the byte that has less than the maximum amount of 1's in it, and count the ON bits
        //IE 11100000 we need to get the fact it has 3 on bits in it and add 3+ (8*the amount of bytes we have gone past that are all 1's)
        //So our first byte stream might be like this 11111111 11111111 11100000
        //We should get the number of ON bits from these 3 bytes, by having 2*8 from the first 2 and adding 3 from the third, meaning we have 19 bits
        //that will be the phrase length for everything going forward, so P is the phrase and C is the hex going forward
        // PPPPPPPP PPPPPPP PPPCCCCP PPPPPPP PPPPPPPP PPCCCCPP PPPPPPPP PPPPPPPP PCCCC etc etc
        //With that value we have the phrase length going forward and from the next byte we can read x length as the phrase then the next 4 as the hex chars byte value
        //continue until end of stream, 

        //the output stream will be integers in the same format the encoder outputs in
        //IE    MAXPHRASELENGTH a
        //                    1 a
        //                    2 a
        // ...
        //...
        // $


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
