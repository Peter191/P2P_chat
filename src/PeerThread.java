import java.net.*;
import java.io.*;

import javax.json.*;


public class PeerThread extends Thread {
    BufferedReader bufferedReader;
    Peer peer;
    boolean listenMode = false;

    //    String username,ip_address;
//    int port;
    public PeerThread(Socket socket, Peer peer) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.peer = peer;
    }

    public void run() {
        boolean flag = true;
        while (flag) {
            try {
                JsonObject jsonObject = Json.createReader(bufferedReader).readObject();
                if (jsonObject.containsKey(StringCollection.FIELDUSERNAME.getText()))
                    System.out.println("[" + jsonObject.getString(StringCollection.FIELDUSERNAME.getText()) + "]: " + jsonObject.getString((StringCollection.FIELDMESSAGE.getText())));
                if (jsonObject.getString((StringCollection.FIELDMESSAGE.getText())).equals(StringCollection.COMMANDSTOP.getText()))
                    changeListenMode();
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

    public Peer getPeer() {
        return peer;
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }

    private void changeListenMode() {
        if (isListenMode() && getPeer().isListenMode()) {
            getPeer().setListenMode(false);
            setListenMode(false);
        } else if (!isListenMode() && !getPeer().isListenMode()) {
            getPeer().setListenMode(true);
            setListenMode(true);
        }
    }
}
