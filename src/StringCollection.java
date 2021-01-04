public enum StringCollection {
    TEST("string"),
    PORT("9009"),
    PORTPETER("9001"),
    PETERIP("192.168.1.57"),
    PORTMARKUS("9002"),
    MARKUSIP("192.168.1.27"),
    PORTALEXANDER("9003"),
    ALEXANDERIP("????"),
    MESSAGEENTERUSERNAME("Enter username:"),
    MESSAGEENTERPORT("Enter port:"),
    MESSAGEENTERPEERINFORMATION("Enter information of peers to communicate with (write 's' to skip and 'c' to continue)"),
    MESSAGEENTERIPADDRESS("Enter ip address:"),
    MESSAGEYOUCANCOMMUNICATE("you can communicate 'e' for exit, 'c' for change and 'stop' to make others listen"),
    ERRORINVALIDINPUT("Invalid input");

    public final String label;

    StringCollection(String label) {
        this.label = label;
    }
}
