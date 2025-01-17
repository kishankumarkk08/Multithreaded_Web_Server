package Multi_Threaded;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
  private static final String FILE_PATH = "response.txt";

  public Consumer<Socket> getConsumer() {
    return (clientSocket) -> {
      try (PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true)) {
        System.out.println("Connected to: " + clientSocket.getInetAddress());

        File file = new File(FILE_PATH);
        if (file.exists() && file.isFile()) {
          System.out.println("Sending file content to client...");
          try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
              toClient.println(line);
            }
          }
        } else {
          System.out.println("File not found!");
          toClient.println("Error: File not found!");
        }

        clientSocket.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    };
  }

  public static void main(String[] args) {
    int port = 9331;
    Server server = new Server();

    try (ServerSocket serverSocket = new ServerSocket(port)) {
      serverSocket.setSoTimeout(70000);
      System.out.println("Server is listening on port " + port);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket));
        thread.start();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}