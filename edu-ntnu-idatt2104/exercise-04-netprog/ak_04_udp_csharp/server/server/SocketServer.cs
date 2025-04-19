using System.Net;
using System.Net.Sockets;
using System.Text;

class SocketServer
{
  public static void Main()
  {
    const int PortNumber = 1250;
    UdpClient udpServer = new UdpClient(PortNumber);
    Console.WriteLine("UDP Server started on port " + PortNumber + ". Ready and waiting.");

    try
    {
      while (true)
      {
        IPEndPoint remoteEndPoint = new IPEndPoint(IPAddress.Any, 0);
        byte[] receivedBytes = udpServer.Receive(ref remoteEndPoint);
        string receivedData = Encoding.ASCII.GetString(receivedBytes);
        Console.WriteLine($"Received: {receivedData} from {remoteEndPoint}");

        string response = ProcessData(receivedData);

        byte[] responseBytes = Encoding.ASCII.GetBytes(response);
        udpServer.Send(responseBytes, responseBytes.Length, remoteEndPoint);
      }
    }
    catch (Exception exception)
    {
      Console.WriteLine("An error occurred: " + exception.Message);
    }
    finally
    {
      udpServer.Close();
    }
  }

  static string ProcessData(string data)
  {
    try
    {
      double num1, num2, result = 0;
      char operation = ' ';
      // Identify the operation and split the numbers
      if (data.Contains("+")) operation = '+';
      else if (data.Contains("-")) operation = '-';
      else if (data.Contains("*")) operation = '*';
      else if (data.Contains("/")) operation = '/';

      if (operation != ' ')
      {
        string[] parts = data.Split(operation);
        if (parts.Length == 2)
        {
          num1 = double.Parse(parts[0]);
          num2 = double.Parse(parts[1]);

          switch (operation)
          {
            case '+': result = num1 + num2; break;
            case '-': result = num1 - num2; break;
            case '*': result = num1 * num2; break;
            case '/':
              if (num2 != 0) result = num1 / num2;
              else return "Error: Division by zero.";
              break;
          }
          return $"Result: {result}";
        }
      }
      return "Error: Invalid request format.";
    }
    catch
    {
      return "Error: Invalid calculation.";
    }
  }

}