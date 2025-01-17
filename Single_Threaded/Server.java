package Single_Threaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  public void run() {
    int port = 9331;
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      serverSocket.setSoTimeout(10000);
      System.out.println("Server started on port " + port);
      while (true) {
        System.out.println("Server is listening on port: " + port);
        Socket accConn = serverSocket.accept();
        System.out.println("Connection accepted: " + accConn.getRemoteSocketAddress());
        PrintWriter towardsClient = new PrintWriter(accConn.getOutputStream());
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(accConn.getInputStream()));
        towardsClient.println("Hi! Nice to meet you! I am the server");
        towardsClient.close();
        fromClient.close();
        accConn.close();
      }

    } catch (IOException e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Server server = new Server();
    try {
      server.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
