package NotUsed;// client.java

import java.net.*;
import java.io.*;

public class clientUdp {
    public static void main(String[] args) throws IOException {
//      String serverName = args[0];
        InetAddress serverName = InetAddress.getByName(args[0]);
        int port = Integer.parseInt(args[1]);

        //console stream
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        try {
            //client datagram socket and packet
            DatagramSocket client = new DatagramSocket();
            DatagramPacket packet;

            //input and output streams
            byte[] buffer;
            String outMessage;
            String inMessage = "";

            while (!inMessage.equals("EXIT")) {
                //entering the message to send
                System.out.println("Please type message to send : ");
                outMessage = stdIn.readLine();

                //sending message
                buffer = outMessage.getBytes();
                packet = new DatagramPacket(buffer, buffer.length, serverName, port);
                client.send(packet);

                //receiving message
                packet = new DatagramPacket(buffer, buffer.length);
                client.receive(packet);

                inMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println("[Received message] -> " + inMessage);
            }

            //closing socket
            client.close();
            System.out.println("Socket closed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
