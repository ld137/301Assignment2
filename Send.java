import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;

public class Send {
    public static void main(String[] args) throws IOException {
        // Create a byte array to send
        byte[] dataToSend = {0x01, 0x02, 0x03, 0x04, 0x05};
        StringBuilder binaryString = new StringBuilder();
        for (byte b : dataToSend) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binaryString.append((val & 0b10000000) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        BitSet bitSet = new BitSet(binaryString.length());

        for (int i = 0; i < binaryString.length(); i++) {
            if (binaryString.charAt(i) == '1') {
                bitSet.set(i);
            }
        }

        int numBytes = (int) Math.ceil(bitSet.length() / 8.0);
        byte[] byteArray = new byte[numBytes];
        for (int i = 0; i < bitSet.length(); i++) {
            if (bitSet.get(i)) {
                int byteIndex = i / 8;
                int bitIndex = i % 8;
                byteArray[byteIndex] |= (1 << (7 - bitIndex));
            }
        }

        System.out.write(byteArray);
        System.out.flush();

    }

}
