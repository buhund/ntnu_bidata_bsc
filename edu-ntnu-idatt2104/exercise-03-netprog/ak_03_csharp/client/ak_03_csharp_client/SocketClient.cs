using System;
using System.IO;
using System.Net.Sockets;
using System.Text;

namespace ak_03_csharp_client;

class SocketClient {

  /**
   * Port number matching the one in the server code.
   * User must enter an IP or machine name (localhost if running on the same PC).
   *
   * Using TcpClient to create a connection with the address and port specified.
   * "using" makes sure the connection is properly disposed of when no longer open.
   * From the TcpClient instance, a NetworkStream is obtained. The stream is used for sending
   * and receiving the data from the server.
   * StreamReader and -Writer are used to read/write from/to the stream.
   * Using "AutoFlush = true" will ensure messages are sent immediately, not waiting for buffer to fill up.
   * -- Data streams in .NET often will wait for a certain amount of data before sending.
   * -- Generally this makes for less overhead but in an "interactive" program like this, we don't want
   * -- to wait for "a lot of data", we want it sent immediately.
   *
   * Using a while loop to manage the calculation and "menu".
   * User input first and second number, then chose operation (+/-), as separate strings.
   * Concat inputs to a single string and send (write) to the server with StreamWriter.
   * This ensures 1:1 correspondence between Read and Write, server and client side.
   * Waits for a response with StreamReader, and prints response to console.
   *
   * When exiting the menu/loop, the "using" blocks ensure that StreamWriter, StreamReader and
   * NetworkStream are properly closed and disposed of.
   */
  public static void Main() {
    const int PortNumber = 1250;
    Console.Write("Enter the name of the machine where the server program runs: ");
    string serverMachine = Console.ReadLine();

    using (TcpClient client = new TcpClient(serverMachine, PortNumber))
    using (NetworkStream stream = client.GetStream())
    using (StreamReader reader = new StreamReader(stream))
    using (StreamWriter writer = new StreamWriter(stream) { AutoFlush = true }) {
      Console.WriteLine("Connected to server.");

      while (true) {
        Console.Write("Enter first number: ");
        string num1 = Console.ReadLine();
        Console.Write("Enter second number: ");
        string num2 = Console.ReadLine();
        Console.Write("Enter operation (add/subtract): ");
        string operation = Console.ReadLine();

        // 1:1 Correspondence:
        // Operation is concatenated into a single line.
        // Then sent to the server as such, in a single WriteLine
        // On server-side, there's a single ReadLine that will receive the single WriteLine.
        writer.WriteLine($"{operation},{num1},{num2}");
        // Single-line ReadLine, reading the result/response sent from the Server.
        string response = reader.ReadLine();
        Console.WriteLine(response);

        Console.WriteLine("Do you want to perform another calculation? (1: Yes, 2: No)");
        // Unless you select explicitly "1: Yes", then you will run a "2: No".
        if (Console.ReadLine() != "1") {
          break;
        }
      }
    }
  }
}