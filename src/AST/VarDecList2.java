package AST;

import java.util.List;

public class VarDecList2 {
    private final List<IdentType> identTypes;
    private final Type type;

    public VarDecList2(List<IdentType> identTypes, Type type) {
        this.identTypes = identTypes;
        this.type = type;
    }
}
