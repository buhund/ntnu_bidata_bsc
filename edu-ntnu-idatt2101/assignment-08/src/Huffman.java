import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;

class Huffman {

    private static final int EXTENDED_ASCII_RANGE = 256; // 256 ASCII characters
    private static String encodedString = "";   // Saves the Huffman value of each character in the file

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
      inputFilePath = "diverse.txt"; // Type in the name of the file to compress.
      outputFilePath = inputFilePath + ".huff"; // Add .huff suffix to file to not overwrite original file.
    }

    try {
      compress(inputFilePath, outputFilePath);
      System.out.println("Original file: " + inputFilePath + " --> Output file: " + outputFilePath);
    } catch (IOException e) {
      System.err.println("An error occurred during compression: " + e.getMessage());
    }
  }

  public static void compress(String inpath, String outpath) throws IOException {
      byte[] byteArr = readAllBytes(new File(inpath));

      int[] charFreq = getFreqArray(byteArr);

      char[] charArray = testCharArray(charFreq);

      HuffmanNode root = HuffmanTree.buildTree((charArray), charFreq, charArray.length);

      char[] fullCharArray = testFullCharArray(byteArr);

      File fileDelete = new File(outpath);
      if (fileDelete.exists()) {
          fileDelete.delete();
      }
      File fileToWrite = new File(outpath);

      for (char c : fullCharArray) {
          generateTree(root, c, "");
      }

      String[] strBitArray = getBitArray(encodedString);
      int nSubString = getSubString(strBitArray);

      byte[] bytes = convertBitToBytes(encodedString);
      writeToFile(fileToWrite, charFreq, bytes, nSubString);

      System.out.println("Successfully compressed file!");
  }

  private static char[] testFullCharArray(byte[] array) {
      ArrayList<Character> charList = new ArrayList<>();
      for (byte b : array) {
          if (b < 0) {
              int number = b & 0xff;
              charList.add((char) number);
          } else {
              charList.add((char) b);
          }
      }
      char[] charArray = new char[charList.size()];
      for (int i = 0; i < charList.size(); i++) {
          charArray[i] = charList.get(i);
      }
      return charArray;
  }

  private static char[] testCharArray(int[] charFreq1) {
      ArrayList<Character> charList = new ArrayList<>();
      for (int i = 0; i < charFreq1.length; i++) {
          if (charFreq1[i] > 0) {
              charList.add((char) i);
          }
      }

      char[] charArray = new char[charList.size()];
      for (int i = 0; i < charList.size(); i++) {
          charArray[i] = charList.get(i);
      }
      return charArray;
  }

  public static int getSubString(String[] strBitArray) {
      return strBitArray[strBitArray.length - 1].length();
  }

  public static void generateTree(HuffmanNode root, char character, String string) {
      if (root == null) {
          return;
      }
      if (root.c == character) {
          encodedString += string;
          return;
      }

      generateTree(root.left, character, string + "0");
      if (root.c == character) {
          encodedString += string;
          return;
      }

      generateTree(root.right, character, string + "1");
      if (root.c == character) {
          encodedString += string;
      }
  }

  public static String[] getBitArray(String string) {
      List<String> list = (Stream.of(string.split("(?<=\\G.{8})")).toList());
      return list.toArray(new String[0]);
  }

  public static byte[] convertBitToBytes(String string) {
      String[] bits = getBitArray(string);
      byte[] bytes = new byte[bits.length];
      for (int i = 0; i < bits.length; i++) {
          bytes[i] = new BigInteger(bits[i], 2).byteValue();
      }
      if (bits[bits.length - 1].length() < 8) {
          bytes[bytes.length - 1] = (byte) (bytes[bytes.length - 1] << (8 - bits[bits.length - 1].length()));
      }

      return bytes;
  }

  public static byte[] readAllBytes(File file) {
      try {
          DataInputStream dis = new DataInputStream(new FileInputStream(file));
          return dis.readAllBytes();
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
  }

  public static void writeBytes(File file, byte[] bytes, int n) {
      try {
          DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file, true));
          outputStream.write(n);
          outputStream.write(bytes);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  public static int[] getFreqArray(byte[] array) {
      int[] charFreq = new int[EXTENDED_ASCII_RANGE];
      for (byte b : array) {
          char character;
          if (b <= 0) {
              int number = b & 0xff;
              charFreq[number]++;
          } else {
              character = (char) (b);
              charFreq[character]++;
          }
      }
      return charFreq;
  }

  public static void writeToFile(File file, int[] charFreq, byte[] bytes, int n) throws IOException {
      writeCharFreq(file, charFreq);
      writeBytes(file, bytes, n);
  }

  // Save the frequency of each character in the file to be used in decompression later
  private static void writeCharFreq(File file, int[] charFreq) throws IOException {
      BufferedWriter outputWriter = new BufferedWriter(new FileWriter(file, true));
      for (int j : charFreq) {
          outputWriter.write(j + ",");
      }
      outputWriter.write(".");
      outputWriter.flush();
      outputWriter.close();
  }
}

