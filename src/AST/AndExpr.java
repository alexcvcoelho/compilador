package AST;

import java.util.List;

public class AndExpr extends MultExpr{
    public AndExpr(List<Expr> exprs) {
        super(exprs);
    }
}
