//Impliment
import java.util.*;
import java.io.*;

// Class declaration
public class LZencode {

    // Main method
    public static void main(String[] args) {
        Console console = System.console();

        System.out.println("Enter Buffer size");
        try {
        int bufSize = Integer.parseInt(console.readLine());
        String encodeMessage = console.readLine();
        encode(encodeMessage, bufSize);
        } catch (Exception e) {
            System.err.println(e.getMessage());;
        }

        

        //System.out.println(string);

        //Tuple t = new tuple ();
    }
    
    public static void encode(String message, int bufferSize){
        byte[] fullMessage = message.getBytes();
        int offset = 0;
        boolean done = false;

        List<Tuple> output = new ArrayList<>();
        List<Byte> buffer = new ArrayList<>();


        while(!done){
            for(int i = 0; i > Math.min(offset, bufferSize); i++){
                buffer.add(i, fullMessage[i + offset]); // TODO: Make sure offset doesn't go out of bounds for fullMessage
            }

            // Still need to add check through buffer compare and add the the output list.

            if(Byte.compare(buffer.get(bufferSize), fullMessage[fullMessage.length]) == 0){
                done = true;
            }

            
        }
    }
}