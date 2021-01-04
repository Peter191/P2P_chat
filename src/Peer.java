import javax.json.Json;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Peer {
    //    private String username;
    ArrayList<PeerThread> peerThreads = new ArrayList<>();
    boolean listenMode = false;

    public static void main(String[] args) throws Exception {
        boolean fast = false;
        if (args.length != 0 && args[0].equals("f")) {
            fast = true;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(StringCollection.MESSAGEENTERUSERNAME.label);
        String username;
        if (!fast) {
            username = bufferedReader.readLine();
        } else {
            username = args[1];
        }
        System.out.println(StringCollection.MESSAGEENTERPORT.label);
        String port;
        if (!fast) {
            port = bufferedReader.readLine();
        } else {
            port = args[2];
        }
        ServerThread serverThread = new ServerThread(port);
        serverThread.start();
        new Peer().updatePeerListen(bufferedReader, username, serverThread);
    }

    public void updatePeerListen(BufferedReader bufferedReader, String username, ServerThread serverThread) throws Exception {
        String str;
        while (true) {
            System.out.println(StringCollection.MESSAGEENTERPEERINFORMATION.label);
            str = bufferedReader.readLine();
            if (str.equals("c")) {
                System.out.println(StringCollection.MESSAGEENTERIPADDRESS.label);
                String ipAddress = bufferedReader.readLine();
                System.out.println(StringCollection.MESSAGEENTERPORT.label);
                String portNr = bufferedReader.readLine();
                Socket socket = null;
                try {
                    socket = new Socket(ipAddress, Integer.parseInt(portNr));
                    PeerThread peerThread = new PeerThread(socket);
                    peerThreads.add(peerThread);
                    peerThread.start();
//                    new PeerThread(socket).start();
                } catch (Exception e) {
                    if (socket != null) socket.close();
                    else System.out.println(StringCollection.ERRORINVALIDINPUT.label);
                }
            } else if (str.equals("s")) {
                break;
            }
        }
        communicate(bufferedReader, username, serverThread);
    }

    private void communicate(BufferedReader bufferedReader, String username, ServerThread serverThread) {
        try {
            System.out.println(StringCollection.MESSAGEYOUCANCOMMUNICATE.label);
            label:
            while (true) {
                String message = bufferedReader.readLine();
                switch (message) {
                    case "e":
                        break label;
                    case "c":
                        updatePeerListen(bufferedReader, username, serverThread);
                        break;
                    case "stop":
                        break label;
                    //implement listen mode
                    default:
                        StringWriter stringWriter = new StringWriter();
                        Json.createWriter(stringWriter).writeObject(Json.createObjectBuilder()
                                .add("username", username)
                                .add("message", message)
                                .build());
                        serverThread.sendMsg((stringWriter.toString()));
                        break;
                }
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
