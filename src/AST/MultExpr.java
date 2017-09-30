package AST;

import java.util.List;

public class MultExpr extends Expr {
    private List<Expr> exprs;

    public MultExpr(List<Expr> exprs) {
        this.exprs = exprs;
    }
}
