package Multi_Threaded;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

  public Runnable getRunnable() {
    return () -> {
      int port = 9331;
      try {
        InetAddress address = InetAddress.getByName("localhost");
        Socket socket = new Socket(address, port);

        try (
            PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
          toSocket.println("Hello from Client " + socket.getLocalSocketAddress());

          System.out.println("Response from Server:");
          String line;
          while ((line = fromSocket.readLine()) != null) {
            System.out.println(line);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }

        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    };
  }

  public static void main(String[] args) {
    Client client = new Client();

    for (int i = 0; i < 100; i++) {
      try {
        Thread thread = new Thread(client.getRunnable());
        thread.start();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}