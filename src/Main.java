import java.io.IOException;
import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws IOException {
//        serverUdp.main(new String[]{StringCollection.PORT.label});
        clientUdp.main(new String[]{StringCollection.PETERIP.label, StringCollection.PORT.label});
    }
}
