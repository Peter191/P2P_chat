import java.io.IOException;
import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws Exception {
//        serverUdp.main(new String[]{StringCollection.PORT.label});
//        clientUdp.main(new String[]{StringCollection.PETERIP.label, StringCollection.PORT.label});
        Peer.main(new String[]{"f","Peter",StringCollection.PORTPETER.label});
        Peer.main(new String[]{"f","Alexander",StringCollection.PORTALEXANDER.label});
        Peer.main(new String[]{"f","Markus",StringCollection.PORTMARKUS.label});
    }
}
