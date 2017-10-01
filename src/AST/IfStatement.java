package AST;

public class IfStatement extends Statement {
    private final Expr ifOp;
    private final StatementList statementList;
    private final StatementList elseStatementList;

    public IfStatement(Expr ifOp, StatementList statementList, StatementList elseStatementList) {
        this.ifOp = ifOp;
        this.statementList = statementList;

        this.elseStatementList = elseStatementList;
    }
}
