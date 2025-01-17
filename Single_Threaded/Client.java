package Single_Threaded;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
  public void run() throws UnknownHostException, IOException {
    int port = 9331;
    InetAddress add = InetAddress.getByName("localhost");
    Socket serverSocket = new Socket(add, port);

    PrintWriter towardsServer = new PrintWriter(serverSocket.getOutputStream(), true);
    BufferedReader fromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

    towardsServer.println("Hi! Nice to meet you! I am the Client");

    System.out.println("Response from the server:");
    String line;
    while ((line = fromServer.readLine()) != null) {
      System.out.println(line);
    }
    towardsServer.close();
    fromServer.close();
    serverSocket.close();
  }

  public static void main(String[] args) {
    try {
      Client client = new Client();
      client.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}