public enum StringCollection {
    TEST("string"),PORT("9900"),PETERIP("192.168.137.1"),MARKUSIP("192.168.1.27");

    public final String label;

    private StringCollection(String label) {
        this.label = label;
    }
}
