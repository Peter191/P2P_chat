import java.io.*;
import java.util.ArrayList;

public class Peer {
    private String username;
//    ArrayList<PeerThread> peerThreads = new ArrayList<>();
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
        this.username = username;
        serverThread.setPeer(this);
        String str;
        while (true) {
            System.out.println(StringCollection.MESSAGEENTERPEERINFORMATION.getText());
            str = bufferedReader.readLine();
            if (str.equals(StringCollection.COMMANDCONTINUE.getText())) {
                System.out.println(StringCollection.MESSAGEENTERIPADDRESS.getText());
                String ipAddress = bufferedReader.readLine();
                System.out.println(StringCollection.MESSAGEENTERPORTOFPEER.getText());
                String portNr = bufferedReader.readLine();
                try {
                    serverThread.addPeer(ipAddress, Integer.parseInt(portNr));
                } catch (Exception e) {
                    System.out.println(StringCollection.ERRORINVALIDINPUT.getText());
                }
            } else if (str.equals(StringCollection.COMMANDSKIP.getText())) {
                break;
            }
        }
        communicate(bufferedReader, username, serverThread);
    }

    private void communicate(BufferedReader bufferedReader, String username, ServerThread serverThread) {
        try {
            System.out.println(StringCollection.MESSAGEYOUCANCOMMUNICATE.getText());
            while (true) {
                String message = bufferedReader.readLine();
                if (message.equals(StringCollection.COMMANDCONTINUE.getText()))
                    updatePeerListen(bufferedReader, username, serverThread);
                else if (message.equals(StringCollection.COMMANDSTOP.getText())) {
                    if (!listenMode) {
                        stopProcedure(username, serverThread, message);
                    } else
                        System.out.println(StringCollection.MESSAGELISTENMODERESTRICTIONREMINDER.getText());
                } else if (message.equals(StringCollection.COMMANDEXIT.getText())) {
                    break;
                } else {
                    if (!listenMode) {
                        sendMessage(username, serverThread, message);
                    } else
                        System.out.println(StringCollection.MESSAGELISTENMODERESTRICTIONREMINDER.getText());
                }
            }
            System.exit(0);
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    private void stopProcedure(String username, ServerThread serverThread, String message) {
        sendMessage(username, serverThread, message);
        StringWriter stringWriterStop = new StringWriter();
        if (listenModeAdmin) {
            sendMessage(StringCollection.FIELDSYSTEM.getText(), serverThread, StringCollection.MESSAGELISTEMODEDEACTIVATED.getText() + username);
            listenModeAdmin = false;
        } else {
            sendMessage(StringCollection.FIELDSYSTEM.getText(), serverThread, StringCollection.MESSAGELISTEMODEACTIVATED.getText() + username);
            listenModeAdmin = true;
        }
        String jsonMessage = stringWriterStop.toString();
        serverThread.sendMsg(jsonMessage);
    }

    private void sendMessage(String username, ServerThread serverThread, String message) {
        message = username + " :" + message;
        serverThread.sendMsg(message);
    }

    public boolean isListenMode() {
        return listenMode;
    }

    public void setListenMode(boolean listenMode) {
        this.listenMode = listenMode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
