package AST;

public class Type {
    private int value;

    public Type(int value) {
        this.value = value;
    }

    public static final int
            INTEGER = 0,
            BOOLEAN = 1,
            CHAR = 2;
}
