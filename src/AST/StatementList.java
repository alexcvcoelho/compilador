package AST;

public class StatementList {
    private final Statement statement;
    private final StatementList statementList;

    public StatementList(Statement statement, StatementList statementList) {
        this.statement = statement;
        this.statementList = statementList;
    }

    public StatementList() {

        statement = null;
        statementList = null;
    }
}
