import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private final DatagramSocket datagramSocket;
    private DatagramPacket packet;
    private final ArrayList<InetAddress> inetAddresses = new ArrayList<>();
    private final ArrayList<Integer> rep_ports = new ArrayList<>();
    byte[] buffer = new byte[576];
    String inMessage;
    private Peer peer;
    boolean listenMode = false;


    public ServerThread(String portNr) throws IOException {
        datagramSocket = new DatagramSocket(Integer.parseInt(portNr));
    }

    public void run() {
        try {
            packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                datagramSocket.receive(packet);
                inMessage = new String(packet.getData(), 0, packet.getLength());
                if (packet.getLength() != 0) {
                    inMessage = new String(packet.getData(), 0, packet.getLength());
                    String[] split = inMessage.split(":");
                    String message = split[split.length - 1];
                    if (message.equals(StringCollection.COMMANDSTOP.getText()))
                        changeListenMode();
                }
                System.out.println(inMessage);
                packet = new DatagramPacket(buffer, buffer.length);
                buffer = new byte[576];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void sendMsg(String message) {
        try {
            for (int i = 0; i < inetAddresses.size(); i++) {
                buffer = message.getBytes();
                packet = new DatagramPacket(buffer, buffer.length, inetAddresses.get(i), rep_ports.get(i));
                datagramSocket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addPeer(String address, Integer port) throws UnknownHostException {
        InetAddress intAddress = InetAddress.getByName(address);
        inetAddresses.add(intAddress);
        rep_ports.add(port);
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
