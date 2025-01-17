package Single_Threaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
  public void run() throws UnknownHostException, IOException {
    int port = 9331;
    InetAddress add = InetAddress.getByName("localhost");
    Socket serverSocket = new Socket(add, port);
    PrintWriter towardsServer = new PrintWriter(serverSocket.getOutputStream());
    BufferedReader fromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
    towardsServer.println("Hi! Nice to meet you! I am the Client");
    String mes = fromServer.readLine();
    System.out.println("Response from the server: " + mes);
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
