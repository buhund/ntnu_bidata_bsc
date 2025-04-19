#include <boost/asio.hpp>
#include <iostream>
#include <string>
#include <sstream>

using namespace std;
using namespace boost::asio::ip;

class SimpleWebServer {
private:
  class Connection {
  public:
    tcp::socket socket;
    Connection(boost::asio::io_service &io_service) : socket(io_service) {}
  };

  boost::asio::io_service io_service;
  tcp::endpoint endpoint;
  tcp::acceptor acceptor;

  void handle_request(shared_ptr<Connection> connection) {
    auto read_buffer = make_shared<boost::asio::streambuf>();

    async_read_until(connection->socket, *read_buffer, "\r\n",
      [this, connection = std::move(connection), read_buffer](const boost::system::error_code &ec, size_t) {
        if (!ec) {
          istream read_stream(read_buffer.get());
          string request;
          getline(read_stream, request);
          request.pop_back(); // Removes "\r" from the end of message

          // Logging the request to make it easier to see if things are happening in the console.
          cout << "Received request: " << request << endl;

          // Parsing the GET request.
          if (request.find("GET / HTTP/1.1") == 0) {
            send_response(connection, "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nDette er hovedsiden");
          } else if (request.find("GET /en_side HTTP/1.1") == 0) {
            send_response(connection, "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nDette er en side");
          } else {
            send_response(connection, "HTTP/1.1 404 Not Found\r\nContent-Type: text/plain\r\n\r\n404 Not Found");
          }
        } else {
          cout << "Error receiving request: " << ec.message() << endl;
        }
      });
  }


  void send_response(shared_ptr<Connection> connection, const string &response) {
    auto write_buffer = make_shared<boost::asio::streambuf>();
    ostream write_stream(write_buffer.get());
    write_stream << response;

    async_write(connection->socket, *write_buffer,
      [this, connection, write_buffer](const boost::system::error_code &ec, size_t) {
        if (!ec) {
          handle_request(connection);
        }
      });
  }

  void accept() {
    auto connection = make_shared<Connection>(io_service);
    acceptor.async_accept(connection->socket, [this, connection](const boost::system::error_code &ec) {
      accept();
      if (!ec) {
        handle_request(connection);
      }
    });
  }

public:
  SimpleWebServer() : endpoint(tcp::v4(), 8080), acceptor(io_service, endpoint) {}

  void start() {
    accept();
    io_service.run();
  }
};

int main() {
  SimpleWebServer server;
  cout << "Starting simple web server on http://localhost:8080" << endl;
  cout << endl;
  cout << "Instruksjoner fra øving:" << endl;
  cout << "GET / HTTP/1.1: I en nettleser (http://localhost:8080) skal følgende tekst vises: Dette er hovedsiden" << endl;
  cout << "GET /en_side HTTP/1.1: I en nettleser (http://localhost:8080/en_side) skal følgende tekst vises: Dette er en side" << endl;
  cout << "Andre forespørsler skal gi statuskoden 404 Not Found" << endl;
  cout << endl;

  server.start();
}
