package AST;

import Lexer.Symbol;

public class UnaryExpr extends Expr {
    private Expr expr;
    private int op;

    public UnaryExpr(Expr expr, int op) {
        this.expr = expr;
        this.op = op;
    }
}
