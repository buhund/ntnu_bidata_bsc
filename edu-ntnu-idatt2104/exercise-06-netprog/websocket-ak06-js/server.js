const net = require('net');
const crypto = require('crypto');

/**
 * HTTP Server to serve the test HTML page
 * Wrapping the http/html page inside a string.
  */

const httpServer = net.createServer(connection => {
  connection.on('data', () => {
    let content = `
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
</head>
<body>
  WebSocket test page
  <div id="messages"></div>
  <input id="messageInput" type="text" placeholder="Write a message...">
  <button id="sendMessage">Send</button>
  
<script>
  let ws = new WebSocket('ws://localhost:3001');

  ws.onmessage = event => {
    const message = JSON.parse(event.data);
    if (message.type === "ping") {
      console.log("Ping received:", message.data);
    } else if (message.type === "broadcast") {
      document.getElementById('messages').innerHTML += message.data + '<br>';
    }
  };

  ws.onopen = () => {
    console.log('Connection opened');
    ws.send(JSON.stringify({ type: 'broadcast', data: 'hello' }));
  };

  document.getElementById('sendMessage').onclick = () => {
    const input = document.getElementById('messageInput');
    const message = input.value; // Get the message from the input field
    ws.send(JSON.stringify({ type: 'broadcast', data: message })); // Send the message as a structured message
    input.value = ''; // Clear input field after sending the message byt setting to empty ''.
  };
</script>

</body>
</html>

`;
    connection.write('HTTP/1.1 200 OK\r\nContent-Length: ' + content.length + '\r\n\r\n' + content);
  });
});
httpServer.listen(3000, () => {
  console.log('HTTP server listening on port 3000');
});

// WebSocket Server
const wsServer = net.createServer();
let clients = []; // Store connected clients in an array

wsServer.on('connection', (socket) => {
  console.log('Client connected');

  socket.on('data', (buffer) => {
    if (buffer.toString().includes('GET / HTTP/1.1')) {
      // Perform the WebSocket handshake
      const webSocketKey = buffer.toString().match(/Sec-WebSocket-Key: (.+)/)[1].trim();
      const hash = crypto.createHash('sha1').update(webSocketKey + '258EAFA5-E914-47DA-95CA-C5AB0DC85B11', 'binary').digest('base64');
      const responseHeaders = [
        'HTTP/1.1 101 Switching Protocols',
        'Upgrade: websocket',
        'Connection: Upgrade',
        `Sec-WebSocket-Accept: ${hash}`,
        '\r\n'
      ];
      socket.write(responseHeaders.join('\r\n'));
      clients.push(socket); // Add socket to client list
    } else {
      // The message is masked and less than 126 bytes in length for easier implementation.
      // Websocket is limited to 126 bytes. Any messages longer than this must be split and sent as two packages.
      const isMasked = buffer[1] & 0x80;
      const payloadLength = buffer[1] & 0x7F;
      const maskStart = 2;
      const dataStart = maskStart + 4;
      let decodedMessage = '';

      if (isMasked && payloadLength <= 125 && buffer.length >= dataStart + payloadLength) {
        for (let i = 0; i < payloadLength; i++) {
          const byte = buffer[dataStart + i] ^ buffer[maskStart + (i % 4)];
          decodedMessage += String.fromCharCode(byte);
        }
        console.log('Received:', decodedMessage);

        // forEach to broadcast message to all connected clients, except the sender
        clients.forEach(client => {
          if (client !== socket) { // Not send the message to itself
            client.write(encodeWebSocketMessage(decodedMessage));
          }
        });
      }
    }
  });

  socket.on('close', () => {
    console.log('Client disconnected');
    clients = clients.filter(client => client !== socket); // Remove disconnected client
  });
});

wsServer.listen(3001, () => {
  console.log('WebSocket server listening on port 3001');
});

// Ping (message) from the server to all clients to keep the connection alive
setInterval(() => {
  clients.forEach(client => {
    const pingMessage = JSON.stringify({ type: "ping", data: "PING" });
    client.write(encodeWebSocketMessage(pingMessage));
  });
}, 10000); // Send every 10 seconds

// forEach to broadcast the message to all connected clients except the sender
clients.forEach(client => {
  if (client !== socket) { // Don't send back to the sender
    const messageObject = { type: "broadcast", data: decodedMessage };
    client.write(encodeWebSocketMessage(JSON.stringify(messageObject)));
  }
});

/**
 * Encoding the message sent via websocket.
 * Allocating buffer, setting first and second byte, then the message.
 * @param message
 * @returns {Buffer}
 */
function encodeWebSocketMessage(message) {
  // Encode text messages into WebSocket frames
  const length = Buffer.byteLength(message);
  const buffer = Buffer.alloc(2 + length); // Allocate buffer: 2 header bytes + message length
  buffer.writeUInt8(0x81, 0); // First byte: FIN bit set and text frame opcode
  buffer.writeUInt8(length, 1); // Second byte: payload length
  buffer.write(message, 2); // Write the message
  return buffer;
}
