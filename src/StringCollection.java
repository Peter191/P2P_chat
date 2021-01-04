public enum StringCollection {
    TEST("string"),
    PORT("9009"),
    PORTPETER("9001"),
    PETERIP("192.168.1.57"),
    PORTMARKUS("9002"),
    MARKUSIP("192.168.1.27"),
    PORTALEXANDER("9003"),
    ALEXANDERIP("????"),
    MESSAGEENTERUSERNAME("Enter your username:"),
    MESSAGEENTERPORT("Enter the port you want to use:"),
    MESSAGEENTERPORTOFPEER("Enter the port your peer is using:"),
    MESSAGEENTERPEERINFORMATION("Enter information of peers to communicate with (write 's' to skip and 'c' to continue)"),
    MESSAGEENTERIPADDRESS("Enter his ip address:"),
    MESSAGEYOUCANCOMMUNICATE("you can communicate 'e' for exit, 'c' for change and 'stop' to make others listen"),
    MESSAGELISTEMODEACTIVATED("Listen mode activated by user:"),
    MESSAGELISTEMODEDEACTIVATED("Listen mode deactivated by user:"),
    ERRORINVALIDINPUT("Invalid input"),
    FIELDUSERNAME("username"),
    FIELDMESSAGE("message"),
    FIELDSYSTEM("SYSTEM"),
    COMMANDSTOP("stop"),
    COMMANDFAST("f"),
    COMMANDCONTINUE("c"),
    COMMANDSKIP("s");

    private final String label;

    StringCollection(String label) {
        this.label = label;
    }
    public final String getText(){
        return label;
    }
}
