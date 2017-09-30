package AST;

public class AssignmentStatement extends Statement{
    private final IdentType identType;
    private final Expr or;

    public AssignmentStatement(IdentType identType, Expr or) {
        this.identType = identType;
        this.or = or;
    }
}
