import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private ServerSocket serverSocket;
    private ArrayList<ServerThreadThread> serverThreadThreads = new ArrayList<>();

    public ServerThread(String portNr) throws IOException {
        serverSocket = new ServerSocket(Integer.parseInt(portNr));
    }

    public void run() {
        try {
            ServerThreadThread serverThreadThread = new ServerThreadThread(serverSocket.accept(), this);
            serverThreadThreads.add(serverThreadThread);
            serverThreadThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void sendMsg(String message) {
        try {
            serverThreadThreads.forEach(t -> t.getPrintWriter().println(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ServerThreadThread> getServerThreadThreads() {
        return serverThreadThreads;
    }

    public void setServerThreadThreads(ArrayList<ServerThreadThread> serverThreadThreads) {
        this.serverThreadThreads = serverThreadThreads;
    }
}
