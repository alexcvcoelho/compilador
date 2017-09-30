package AST;

public class ReadStatement extends Statement {


    private IdentType identType;

    public ReadStatement(IdentType identType) {

        this.identType = identType;
    }
}
