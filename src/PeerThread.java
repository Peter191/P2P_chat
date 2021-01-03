import java.net.*;
import java.io.*;

import javax.json.*;


public class PeerThread extends Thread {
    BufferedReader bufferedReader;
    boolean listenMode = false;

    //    String username,ip_address;
//    int port;
    public PeerThread(Socket socket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        boolean flag = true;
        while (flag) {
            try {
                JsonObject jsonObject = Json.createReader(bufferedReader).readObject();
                if (jsonObject.containsKey("username"))
                    System.out.println("[" + jsonObject.getString("username") + "]: " + jsonObject.getString(("message")));
                if (jsonObject.getString(("message")).equals("stop")&&!listenMode)
                    listenMode = true;
                else if (jsonObject.getString(("message")).equals("stop")&&listenMode)
                    listenMode = false;
            } catch (Exception e) {
                flag = false;
                interrupt();
            }
        }
    }

    public boolean isListenMode() {
        return listenMode;
    }

    public void setListenMode(boolean listenMode) {
        this.listenMode = listenMode;
    }
}
