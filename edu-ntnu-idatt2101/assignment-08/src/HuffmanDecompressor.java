import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class HuffmanDecompressor {

  private static final int EXTENDED_ASCII_RANGE = 256; // 256 ASCII characters
  private static final byte PERIOD = 46; // ASCII code for '.'

  public static void main(String[] args) throws IOException {
      String inputFilePath;
      String outputFilePath;

      // Check if arguments are passed via CLI, otherwise use default paths for IDE
      if (args.length >= 2) {
        // CLI based input output
        inputFilePath = args[0];
        outputFilePath = args[1];
      } else {
        // Direct-in-code input and output for IDE
        inputFilePath = "diverse.txt.huff"; // Type in the name of the file to be decompressed.
        outputFilePath = inputFilePath + ".decomp"; // Add .decomp suffix to file to not overwrite original file.
      }

      try {
        decompress(inputFilePath, outputFilePath);
        System.out.println("Original file: " + inputFilePath + " --> Output file: " + outputFilePath);
      } catch (IOException e) {
        System.err.println("An error occurred during decompression: " + e.getMessage());
      }
    }

  public static byte[] decodeString(String s, HuffmanNode root) {
    ArrayList<Byte> list = new ArrayList<>();
    HuffmanNode current = root;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '0') {
        current = current.left;
      } else {
        current = current.right;
      }

        assert current != null;
        if (current.left == null && current.right == null) {
        list.add((byte) current.c);
        current = root;
      }
    }

    byte[] test = new byte[list.size()];
    for (int i = 0; i < list.size(); i++) {
      test[i] = list.get(i);
    }

    return test;
  }

  public static int getFirstPeriodIndex(byte[] allBytes) {
    int index = 0;
    for (int i = 0; i < allBytes.length; i++) {
      if (allBytes[i] == PERIOD) {
        index = i;
        break;
      }
    }
    return index;
  }

  public static int[] generateCharFreq(File file) {
    int[] charFreq = new int[EXTENDED_ASCII_RANGE]; // Extended ASCII
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = reader.readLine();
      byte[] allBytes = Files.readAllBytes(file.toPath());
      int index = getFirstPeriodIndex(allBytes);

      String[] freq = line.substring(0, index).split(",");

      for (int i = 0; i < freq.length; i++) {
        int number = Integer.parseInt(freq[i]);
        if (number > 0) {
          charFreq[i] = number;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return charFreq;
  }

  public static byte[] readBytes(File file) throws IOException {
    byte[] allBytes = Files.readAllBytes(file.toPath());
    int index = getFirstPeriodIndex(allBytes);
    return Arrays.copyOfRange(allBytes, index + 2, allBytes.length);
  }

  public static int ReadSubString(File file) {
    try {
      byte[] allBytes = Files.readAllBytes(file.toPath());
      return allBytes[getFirstPeriodIndex(allBytes) + 1];
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

public static String[] getBitArray(byte[] byteArr, int nSubString) {
    String[] bitArray = new String[byteArr.length];
    for (int i = 0; i < byteArr.length; i++) {
      String binaryString = Integer.toBinaryString(byteArr[i] & 0xFF);
      String paddedBinaryString = String.format("%8s", binaryString).replace(' ', '0');
      if (i == byteArr.length - 1) {
        // Remove padding bits from the last byte
        paddedBinaryString = paddedBinaryString.substring(0, 8 - nSubString);
      }
      bitArray[i] = paddedBinaryString;
    }
    return bitArray;
  }

    public static String generateDecodedString(String[] bitArray) {
        StringBuilder decodedString = new StringBuilder();
        for (String s : bitArray) {
            decodedString.append(s);
        }
        return decodedString.toString();
    }

    public static char[] generateCharArray(int[] charFreq) {
        int count = 0;
        for (int j : charFreq) {
            if (j > 0) {
                count++;
            }
        }
        char[] charArray = new char[count];
        int index = 0;
        for (int i = 0; i < charFreq.length; i++) {
            if (charFreq[i] > 0) {
                charArray[index++] = (char) i;
            }
        }
        return charArray;
    }

  /**
   * Read the frequency table.
   * Reconstruct the Huffman tree.
   * Read the encoded data, i.e. bytes after the frequency table and delimiter.
   * Decode the bit stream.
   * Write the decoded data to the output file.
   * @throws IOException if an I/O error occurs
   */
  public static void decompress(String inpath, String outpath) throws IOException {
    File file = new File(inpath);

    // Read the frequency table
    int[] charFrequency = generateCharFreq(file);

    // Read the encoded data
    byte[] byteArr = readBytes(file);

    // Read the number of padding bits
    int nSubString = ReadSubString(file);

    // Convert bytes to bit string
    String[] bitArray = getBitArray(byteArr, nSubString);

    // Generate decoded string
    String decodedString = generateDecodedString(bitArray);

    // Generate char array
    char[] charArray = generateCharArray(charFrequency);

    // Build Huffman Tree
    HuffmanNode root = HuffmanTree.buildTree(charArray, charFrequency, charArray.length);
    byte[] testBytes = decodeString(decodedString, root);

    // Delete old file (if exist) and create new file
    File compressedFile = new File(outpath);
    if (compressedFile.exists()) {
      compressedFile.delete();
      compressedFile.createNewFile();}

    // Write decompressed bytes to file
    writeDecompressedToFile(testBytes, compressedFile);

    System.out.println("Successfully decompressed file!");
  }

  public static void writeDecompressedToFile(byte[] bytes, File file) throws IOException {
    DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
    out.write(bytes);
  }

}
