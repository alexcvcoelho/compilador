package AST;

public class WriteStatement extends Statement {
    private Expr expr;

    public WriteStatement(Expr expr) {
        this.expr = expr;
    }
}
