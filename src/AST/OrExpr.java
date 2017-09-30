package AST;

import java.util.List;

public class OrExpr extends MultExpr {
    public OrExpr(List<Expr> exprs) {
        super(exprs);
    }
}
