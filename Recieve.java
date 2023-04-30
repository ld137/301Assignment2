import java.io.IOException;
import java.util.Arrays;
import java.io.*;
import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.io.IOException;

public class Recieve {
    public static void main(String[] args) throws IOException{
        BufferedInputStream bis = new BufferedInputStream(System.in);
        
        
        byte[] buffer = new byte[1024];
        int bytesRead = 0;

        System.err.println("Start");

        while ((bytesRead = bis.read(buffer)) != -1) {
            StringBuilder binaryString = new StringBuilder();
            for (int i = 0; i < bytesRead; i++) {
                String byteString = Integer.toBinaryString(buffer[i] & 0xFF);
                while (byteString.length() < 8) {
                    byteString = "0" + byteString;
                }
                binaryString.append(byteString);
            }
            System.err.println("");
            System.err.println(binaryString);
            System.err.println("");
        }
        
    }
}
