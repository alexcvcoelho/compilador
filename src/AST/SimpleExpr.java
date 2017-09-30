package AST;

public class SimpleExpr extends Expr{

    private int type;
    private boolean bool;

    public SimpleExpr(int type) {
        this.type = type;
    }

    public SimpleExpr(int type, boolean bool) {
        this.type = type;
        this.bool = bool;
    }

    public final static int
            NUMBER = 0,
            IDENT = 1,
            VAR = 2,
            EXP = 3,
            NOT = 5,
            ADD  = 9,
            BOOL = 10;

}
