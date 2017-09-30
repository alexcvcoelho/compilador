package AST;

import java.util.List;

public class AddExpr extends MultExpr{

    public AddExpr(List<Expr> exprs) {
        super(exprs);
    }
}
