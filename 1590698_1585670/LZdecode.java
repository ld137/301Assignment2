

/*
 * Lleyton Damon - 1585670
 * Jake Postlewaight - 1590698
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;

public class LZdecode {
    public static void main(String[] args) {
        //startup the input scanner
        Scanner scanner = new Scanner(System.in);
        //read into an array of lines
        ArrayList<String> lines = new ArrayList<>();
        char stopChar = '$';
        //startup the output string builder
        StringBuilder output = new StringBuilder();
        //create a new hashmap
        Map<Integer, String> dictionary = new HashMap<>();
        int nextIndex = 1;
        // Read input until the stop character is encountered
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.indexOf(stopChar) != -1) {
                // Stop character found, append input up to stop character and stop
                lines.add(line.substring(0, line.indexOf(stopChar)) + stopChar);
                break;
            } else {
                // Stop character not found, add line to the array
                lines.add(line);
            }
        }
        scanner.close();
        for (String line : lines) {
            if (line.compareTo("") == 0) continue; //If line is blank
            if (line.compareTo("\n") == 0) continue; //If line is only a new line

            String[] parts = line.split(" "); //Split the integer pairs [0] = the Phrase [1] = Character
            //End of line character to know we are done reading
            if (parts[0].compareTo("$") == 0) break;
            //Get the phrase
            int phase = Integer.parseInt(parts[0]);
            //Get the character from the integer to a hex
            String character = parts[1].compareTo("$") == 0 ? "$" : Integer.toHexString(Integer.parseInt(parts[1]));

            //to store the output from the hashmap
            String decodedString;
            //When a new phrase is being added
            if (phase == 0) {
                decodedString = character;
            } else {
                //Make sure we arent at the stop character
                if (character.compareTo("$") == 0){
                    decodedString = dictionary.get(phase);
                    output.append(decodedString);
                    break;
                }else {
                    //get the decoded dictionary aswell as the new character we are adding
                    decodedString = dictionary.get(phase) + character;
                }
            }
            //append to the output
            output.append(decodedString);
            //save to the map
            dictionary.put(nextIndex, decodedString);
            //incriment the index
            nextIndex++;
        }
        //create a new byte array that we will print out later
        byte[] byteArray = new byte[output.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            //populate the byte array
            int index = i * 2;
            int value = Integer.parseInt(output.substring(index, index + 2), 16);
            byteArray[i] = (byte) value;
        }

        // next, convert the byte array into a string, I would imagine we could have output the string instead
        //but initially I thought we were outputting it as a byte array
        String text = new String(byteArray, StandardCharsets.UTF_8);
        System.out.println(text.toString());
    }
}