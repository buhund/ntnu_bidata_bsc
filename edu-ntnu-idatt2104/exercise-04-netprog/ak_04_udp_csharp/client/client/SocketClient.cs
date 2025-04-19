using System;
using System.Net;
using System.Net.Sockets;
using System.Text;

class SocketClient
{
  public static void Main()
  {
    const int PortNumber = 1250;
    Console.Write("Enter the IP of the server: ");
    string serverIP = Console.ReadLine();
    IPAddress ipAddress = serverIP.ToLower() == "localhost" ? IPAddress.Loopback : IPAddress.Parse(serverIP);

    UdpClient client = new UdpClient();
    IPEndPoint serverEndpoint = new IPEndPoint(ipAddress, PortNumber);

    while (true)
    {
      Console.WriteLine("Welcome to Addition and Subtraction Service (ASS)");
      Console.WriteLine("Only two numbers per calculation is allowed.");
      Console.Write("Enter calculation: ");
      string request = Console.ReadLine();

      byte[] bytesToSend = Encoding.ASCII.GetBytes(request);
      client.Send(bytesToSend, bytesToSend.Length, serverEndpoint);

      IPEndPoint from = new IPEndPoint(IPAddress.Any, 0);
      byte[] bytesReceived = client.Receive(ref from);
      string response = Encoding.ASCII.GetString(bytesReceived);
      Console.WriteLine(response);

      Console.WriteLine("Do you want to perform another operation? (1: Yes, 2: No)");
      if (Console.ReadLine() != "1")
      {
        break;
      }
    }

    client.Close();
  }
}