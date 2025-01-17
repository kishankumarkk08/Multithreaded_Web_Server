package Single_Threaded;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  private static final int PORT = 9331;
  private static final String FILE_PATH = "response.txt";

  public void run() {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      serverSocket.setSoTimeout(10000);
      System.out.println("Server started on port " + PORT);

      while (true) {
        System.out.println("Server is listening on port: " + PORT);
        Socket accConn = serverSocket.accept();
        System.out.println("Connection accepted: " + accConn.getRemoteSocketAddress());

        PrintWriter towardsClient = new PrintWriter(accConn.getOutputStream(), true);
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(accConn.getInputStream()));

        File file = new File(FILE_PATH);
        if (file.exists() && file.isFile()) {
          System.out.println("Sending file content...");
          try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
              towardsClient.println(line);
            }
          }
        } else {
          System.out.println("File not found!");
          towardsClient.println("Error: File not found!");
        }
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