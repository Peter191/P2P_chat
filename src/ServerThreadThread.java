import java.net.*;
import java.io.*;

public class ServerThreadThread extends Thread {
    private final ServerThread serverThread;
    private final Socket socket;
    private PrintWriter printWriter;

    public ServerThreadThread(Socket socket, ServerThread serverThread) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(this.socket.getInputStream())));
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
            while (true) serverThread.sendMsg(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }
}
