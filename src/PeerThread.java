import java.net.*;
import java.io.*;



public class PeerThread extends Thread {
    Peer peer;
    DatagramSocket datagramSocket;
    byte[] buffer = new byte[1460];
    DatagramPacket packet;
    boolean listenMode = false;
    String inMessage = "";

    public PeerThread(Peer peer) throws IOException {
        this.peer = peer;
        this.datagramSocket = new DatagramSocket();
    }

    public void run() {
        boolean flag = true;
        while (flag) {
            try {
                packet = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packet);
                if (packet != null) {
                    inMessage = new String(packet.getData(), 0, packet.getLength());
                    String[] split = inMessage.split(":");
                    String message = split[split.length - 1];
                    if (message.equals(StringCollection.COMMANDSTOP.getText()))
                        changeListenMode();
                }
            } catch (Exception e) {
                flag = false;
                interrupt();
            }
            buffer=new byte[1460];
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
