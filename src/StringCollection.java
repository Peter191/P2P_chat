public enum StringCollection {
    TEST("string"),PORT("9009"),PORTPETER("9001"),PETERIP("192.168.137.1"),PORTMARKUS("9002"),MARKUSIP("192.168.1.27");

    public final String label;

    private StringCollection(String label) {
        this.label = label;
    }
}
