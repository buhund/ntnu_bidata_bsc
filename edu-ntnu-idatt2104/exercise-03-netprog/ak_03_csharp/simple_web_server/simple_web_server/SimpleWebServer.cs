using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;

namespace simple_web_server;

/**
 * Simple Web Server.
 * When connecting to this server with a web browser, the browser will send a HTTP request:
 * -- HTTP request line (GET / HTTP/1.1)
 * -- HTTP Request Header
 * -- Empty line to signal end of header section.
 * The server will use StreamReader to read the requests from the browser.
 * Each line of header is read and appended to the StringBuilder object, wrapping each line
 * in <li> tags to format as a list.
 * The empty line signals end of request, and the server stops reading from the client.
 *
 * The server then sends a HTTP response:
 * -- HTTP Status Line (HTTP/1.0 200 OK), which indicates successful request.
 * -- HTTP Response Header
 * -- Empty line to separate header and body.
 * -- HTML content. Here, it is the welcome message, and the request headers (StringBuilder object, as <ul>).
 * This is sent with the StreamWriter, writing to the network stream connected to the client.
 * Using client.Close() to terminate the connection.
 */
class SimpleWebServer {
  static void Main(string[] args) {
    int port = 8080; // 8080 doesn't require elevated privileges to use.
    TcpListener listener = new TcpListener(IPAddress.Any, port);

    // Starting the TCP listener.
    try {
      listener.Start();
      Console.WriteLine($"Server is listening on port {port}...");

      // Keep the listener running in a loop, and accept any connection.
      while (true) {
        Console.WriteLine("Waiting for a connection...");
        TcpClient client = listener.AcceptTcpClient();
        Console.WriteLine("Connected!");

        // "using" will ensure proper disposal of streams after termination.
        // Get a stream from/to client. Then create StreamReader and -Writer for the stream.
        // "AutoFlush = true" will ensure messages are sent immediately, not waiting for buffer to fill up.
        using (NetworkStream stream = client.GetStream())
        using (StreamReader reader = new StreamReader(stream))
        using (StreamWriter writer = new StreamWriter(stream) { AutoFlush = true }) {

          // StringBuilder for writing out the HTTP request.
          StringBuilder requestHeaders = new StringBuilder();
          string line;
          while ((line = reader.ReadLine()) != null && line != "")
          {
            requestHeaders.AppendLine($"<LI>{line}</LI>");
          }

          // Write out the HTTP status, header, body etc.
          writer.WriteLine("HTTP/1.0 200 OK");
          writer.WriteLine("Content-Type: text/html; charset=utf-8");
          writer.WriteLine(); // Empty line to separate headers from body
          writer.WriteLine(); // Empty line to separate headers from body
          writer.WriteLine("<HTML><BODY>");
          writer.WriteLine("<H1> Vær hilset! Du har koblet deg opp til min enkle web-tjener </H1>");
          writer.WriteLine("Header fra klient er:");
          writer.WriteLine("<UL>");
          writer.Write(requestHeaders.ToString());
          writer.WriteLine("</UL>");
          writer.WriteLine("</BODY></HTML>");

          // Close the connection
          client.Close();
          Console.WriteLine("Connection closed.");
        }
      }
    } catch (Exception e) {
      Console.WriteLine("Error: " + e.Message);
    } finally {
      listener.Stop();
    }
  }
}