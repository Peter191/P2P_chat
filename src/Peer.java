import javax.json.Json;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Peer {
    //    private String username;
    ArrayList<PeerThread> peerThreads = new ArrayList<>();
    boolean listenMode = false;
    boolean listenModeAdmin = false;

    public static void main(String[] args) throws Exception {
        boolean fast = false;
        if (args.length != 0 && args[0].equals(StringCollection.COMMANDFAST.getText())) {
            fast = true;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(StringCollection.MESSAGEENTERUSERNAME.getText());
        String username;
        if (!fast) {
            username = bufferedReader.readLine();
        } else {
            username = args[1];
        }
        System.out.println(StringCollection.MESSAGEENTERPORT.getText());
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
            System.out.println(StringCollection.MESSAGEENTERPEERINFORMATION.getText());
            str = bufferedReader.readLine();
            if (str.equals(StringCollection.COMMANDCONTINUE.getText())) {
                System.out.println(StringCollection.MESSAGEENTERIPADDRESS.getText());
                String ipAddress = bufferedReader.readLine();
                System.out.println(StringCollection.MESSAGEENTERPORT.getText());
                String portNr = bufferedReader.readLine();
                Socket socket = null;
                try {
                    socket = new Socket(ipAddress, Integer.parseInt(portNr));
                    PeerThread peerThread = new PeerThread(socket, this);
                    peerThreads.add(peerThread);
                    peerThread.start();
                } catch (Exception e) {
                    if (socket != null) socket.close();
                    else System.out.println(StringCollection.ERRORINVALIDINPUT.getText());
                }
            } else if (str.equals(StringCollection.COMMANDSTOP.getText())) {
                break;
            }
        }
        communicate(bufferedReader, username, serverThread);
    }

    private void communicate(BufferedReader bufferedReader, String username, ServerThread serverThread) {
        try {
            System.out.println(StringCollection.MESSAGEYOUCANCOMMUNICATE.getText());
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
                        if (!listenMode) {
                            StringWriter stringWriterStop = new StringWriter();
                            createMsg(username, message, stringWriterStop);
                            serverThread.sendMsg((stringWriterStop.toString()));
                            stringWriterStop = new StringWriter();
                            if (listenModeAdmin) {
                                createMsg(StringCollection.FIELDSYSTEM.getText(), StringCollection.MESSAGELISTEMODEDEACTIVATED.getText() + username, stringWriterStop);
                                listenModeAdmin = false;
                            } else {
                                createMsg(StringCollection.FIELDSYSTEM.getText(), StringCollection.MESSAGELISTEMODEACTIVATED.getText() + username, stringWriterStop);
                                listenModeAdmin = true;
                            }
                            serverThread.sendMsg((stringWriterStop.toString()));
                        }
                        break;
                    default:
                        if (!listenMode) {
                            StringWriter stringWriter = new StringWriter();
                            createMsg(username, message, stringWriter);
                            serverThread.sendMsg((stringWriter.toString()));
                        }
                        break;
                }
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createMsg(String username, String message, StringWriter stringWriter) {
        Json.createWriter(stringWriter).writeObject(Json.createObjectBuilder()
                .add(StringCollection.FIELDUSERNAME.getText(), username)
                .add(StringCollection.FIELDMESSAGE.getText(), message)
                .build());
    }

    public boolean isListenMode() {
        return listenMode;
    }

    public void setListenMode(boolean listenMode) {
        this.listenMode = listenMode;
    }
}
