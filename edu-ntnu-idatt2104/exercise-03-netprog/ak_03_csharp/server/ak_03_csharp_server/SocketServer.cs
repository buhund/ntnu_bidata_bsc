using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace ak_03_csharp_server;

class SocketServer {

  /**
   * Server starts listening on the specified port, for any IP address.
   *
   * While loop:
   * Goes into a loop, where it waits for anyone connecting.
   * When encountering a possible client, Accept Tcp Client connects it.
   * Then starts a an instance of HandleClient, which is started in a separate thread.
   * The threaded HandleClient instance is then given the TcpClient that was just connected.
   */
  public static void Main() {
    const int PortNumber = 1250;
    TcpListener server = new TcpListener(IPAddress.Any, PortNumber);
    server.Start();
    Console.WriteLine("Server started. Waiting for connections...");

    try {
      while (true) {
        TcpClient client = server.AcceptTcpClient();
        Console.WriteLine("Connection established.");
        Thread clientThread = new Thread(HandleClient);
        clientThread.Start(client);
      }
    } finally {
      server.Stop();
    }
  }

  /**
   * HandleClient will run in separate threads for each client (as per while loop in Main).
   * Reads requests from the client, processes, and return the appropriate response.
   * Each client is given their own thread/instance of the method to work in.
   *
   * All communication is sent as SingleLine ReadLine and WriteLine between Server and Client.
   * This ensures a 1:1 correspondence, which is required for proper communication between them.
   * For each 1 WriteLine, there must be 1 ReadLine, else the message would go "outside the page" or something.
   *
   * Using try-catch-finally for managing resources and clean-up for closing the connection and errors.
   */
  static void HandleClient(object obj) {
    TcpClient client = (TcpClient)obj;
    try {
      // "using" will ensure proper disposal of streams after termination.
      using (NetworkStream stream = client.GetStream())
      using (StreamReader reader = new StreamReader(stream))
      using (StreamWriter writer = new StreamWriter(stream) { AutoFlush = true }) {
        string request;
        // Reading the request from the network stream, sent by the client.
        // Reads a single line server-side, from a single line client-side.
        while ((request = reader.ReadLine()) != null) {
          // Splits the concatenated request, e.g. [add,1,4], into a string array.
          string[] parts = request.Split(',');
          if (parts.Length == 3) {
            try {
              // Extracts the parts of the calculation from the string array.
              string operation = parts[0];
              int num1 = int.Parse(parts[1]);
              int num2 = int.Parse(parts[2]);
              // Fancy ternary ? operator
              // If user input operation "add" then n1+n2; else n1-n2
              // Basically any input other than "add" will result in subtract x)
              int result = operation == "add" ? num1 + num2 : num1 - num2;
              // Single-line WriteLine sending the response back to the Client.
              writer.WriteLine("Result: " + result);
            } catch {
              writer.WriteLine("Error: Invalid calculation.");
            }
          } else {
            writer.WriteLine("Error: Invalid request format.");
          }
        }
      }
    } catch (Exception ex) {
      Console.WriteLine("An error occurred with a client: " + ex.Message);
    } finally {
      client.Close();
    }
  }
}