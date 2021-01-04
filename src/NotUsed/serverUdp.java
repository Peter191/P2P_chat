package NotUsed;// server.java

import java.net.*;
import java.io.*;

public class serverUdp {


    public static void main(String[] args) {
        int rcv_port = Integer.parseInt(args[0]);

        while (true) {
            try {
                //server datagram socket and packet
                DatagramSocket server = new DatagramSocket(rcv_port);
                DatagramPacket packet;
                //input and output streams
                byte[] buffer = new byte[256];
                String outMessage;
                String inMessage;

//	    while (!inMessage.equals("exit")){

                //receiving message
                packet = new DatagramPacket(buffer, buffer.length);
                System.out.println("Waiting for message on port " + rcv_port + "...");
                server.receive(packet);
                inMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println("[Received message] -> " + inMessage);

                //sending message
                InetAddress address = packet.getAddress();
                int rep_port = packet.getPort();
                outMessage = inMessage.toUpperCase();
                System.out.println("[Replied message] -> " + outMessage);
                buffer = outMessage.getBytes();
                packet = new DatagramPacket(buffer, buffer.length, address, rep_port);
                server.send(packet);
//	    }

                //closing socket
                server.close();
                System.out.println("Connection closed");

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }


}
