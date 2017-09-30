package AST;

import java.util.List;

public class NumberType {
    private final int op;
    private final List<Integer> digits;

    /**
     * Cria uma uma representacao de um numero na AST
     * @param op Operador {@link java.text.DateFormatSymbols}
     * @param digits
     */
    public NumberType(int op, List<Integer> digits) {
        this.op = op;
        this.digits = digits;
    }
}
