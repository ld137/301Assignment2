//Impliment

//Impliment
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Scanner;

public class LZpack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<byte[]> outputBytes = new ArrayList<byte[]>();
        ArrayList<String> lines = new ArrayList<>();
        char stopChar = '$';
        // Read input until the stop character is encountered
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            System.err.println(line);
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
        String outputBinaryString = "";
        int currentMaxDict = 0;
        for (String line : lines) {
            String[] parts = line.split(" ");
            int phrase = Integer.parseInt(parts[0]);
            int hexCode = Character.digit(parts[1].charAt(0), 16);
            int log2x = (int) Math.ceil(Math.log(currentMaxDict == 0 ? 1 : currentMaxDict) / Math.log(2));
            // int phraseMask = (1 << log2x) - 1;
            int phraseMask = (1 << log2x) - (phrase == 0 ? 0 : 1);
            int phraseResult = phrase & phraseMask;

            int hexMask = 0b1111;
            int hexResult = (hexCode == -1 ? 0 : hexCode) & hexMask;


            System.err.println("phrase:" + phrase);
            System.err.println("hexCode:" + hexCode);
            System.err.println("log2x:" + log2x);
            String paddedHex = String.format("%1$" + 4 + "s", Integer.toBinaryString(hexResult)).replace(' ', '0');
            System.err.println("hexResult:" + paddedHex);
            String paddedPhrase = String
                    .format("%1$" + (log2x == 0 ? 1 : log2x) + "s", Integer.toBinaryString(phraseResult))
                    .replace(' ', '0');
            System.err.println("phraseResult:" + paddedPhrase);
            System.err.println(paddedPhrase + paddedHex);
            outputBinaryString += paddedPhrase + paddedHex;
            System.err.println("");
            currentMaxDict++;

        }
        System.err.println(outputBinaryString);

        BitSet bitSet = new BitSet(outputBinaryString.length());

        for (int i = 0; i < outputBinaryString.length(); i++) {
            if (outputBinaryString.charAt(i) == '1') {
                bitSet.set(i);
            }
        }

        byte[] byteArray = bitSet.toByteArray();
        System.out.print(byteArray);

    }
}