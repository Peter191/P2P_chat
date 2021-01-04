import java.io.IOException;
import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws Exception {
//        serverUdp.main(new String[]{StringCollection.PORT.getText()});
//        clientUdp.main(new String[]{StringCollection.PETERIP.getText(), StringCollection.PORT.getText()});
        Peer.main(new String[]{StringCollection.COMMANDFAST.getText(), "Peter", StringCollection.PORTPETER.getText()});
//        Peer.main(new String[]{StringCollection.COMMANDFAST.getText(),"Alexander",StringCollection.PORTALEXANDER.getText()});
//        Peer.main(new String[]{StringCollection.COMMANDFAST.getText(),"Markus",StringCollection.PORTMARKUS.getText()});
    }
}
