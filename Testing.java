import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

public class Testing {
    public static void main(String[] args) {
        // example input list of strings
        List<String> inputLines = Arrays.asList(
                "1754 0",
                "7104 2",
                "40090 7",
                "5340 f",
                "8747 0",
                "45671 6",
                "55522 1",
                "18370 0",
                "14214 b",
                "57334 7",
                "22309 9",
                "41883 6",
                "31836 2",
                "7272 7",
                "71102 7",
                "25359 5",
                "48074 2",
                "76951 6",
                "62189 7",
                "1707 2",
                "45104 9",
                "41233 7",
                "21974 7",
                "56780 7",
                "67171 0",
                "63043 5",
                "47792 6",
                "68108 c");

        // number of bytes that num1 takes up
        int num1SizeBytes = 3;

        /*
         * Max Phrase: 79389
         * Phrase Length in bits: 17
         * Phrase and Hex min bytes : 3
         * Phrase min bytes : 3
         */
        int phraseBits = 17;

        // process each line in the input list
        int packedByteSize = num1SizeBytes + 1;
        // ByteBuffer buffer = ByteBuffer.allocate(inputLines.size() *
        // packedByteSize).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer buffer = ByteBuffer.allocate((int)Math.ceil((phraseBits*inputLines.size())+(4*inputLines.size())/8));
        for (String line : inputLines) {
            String[] parts = line.split("\\s+");
            long num1 = Long.parseLong(parts[0]);
            int num2 = Integer.parseInt(parts[1], 16);

            // byte buffer is equal to Math.ceil(((bits * lines) + (4 * lines)) / 8)
        }

        // get the packed bytes as a byte array
        byte[] packedBytes = buffer.array();

        // convert the packed bytes to a space-separated binary string
        StringBuilder binaryStringBuilder = new StringBuilder();
        for (byte b : packedBytes) {
            String binaryString = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            binaryStringBuilder.append(binaryString).append(' ');
        }
        String binaryString = binaryStringBuilder.toString().trim();

        // print the binary string
        System.out.println(binaryString);
    }
}
