import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private final DatagramSocket datagramSocket;
    private DatagramPacket packet;
    private ArrayList<InetAddress> inetAddresses = new ArrayList<>();
    private ArrayList<Integer> rep_ports = new ArrayList<>();
    byte[] buffer = new byte[1460];
    String outMessage;
    String inMessage;


    public ServerThread(String portNr) throws IOException {
        datagramSocket = new DatagramSocket(Integer.parseInt(portNr));
    }

    public void run() {
        try {
            packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                datagramSocket.receive(packet);
                inMessage = new String(packet.getData(), 0, packet.getLength());

                System.out.println(inMessage);
                packet = new DatagramPacket(buffer, buffer.length);
                buffer=new byte[1460];
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
}
