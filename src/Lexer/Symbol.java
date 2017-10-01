package Lexer;

/**
 * Created by alexcoelho on 30/09/17.
 */
public class Symbol {
    public final static int
        EOF = 0,
        IDENT = 1,
        NUMBER = 2,
        PLUS = 3,
        MINUS = 4,
        MULT = 5,
        DIV = 6,
        LT = 7,
        LE = 8,
        GT = 9,
        GE = 10,
        NEQ = 11,
        EQ = 12,
        ASSIGN = 13,
        LEFTPAR = 14,
        RIGHTPAR = 15,
        SEMICOLON = 16,
        VAR = 17,
        BEGIN = 18,
        END = 19,
        IF = 20,
        THEN = 21,
        ELSE = 22,
        ENDIF = 23,
        COMMA = 24,
        READ = 25,
        WRITE = 26,
        COLON = 27,
        INTEGER = 28,
        BOOLEAN = 29,
        CHAR = 30,
        CHARACTER = 31,
        TRUE = 32,
        FALSE = 33,
        OR = 34,
        AND = 35,
        REMAINDER = 36,
        NOT = 37;



    public static String toString(int token) {
        switch (token) {
            case Symbol.EOF:
                return "EOF";
            case Symbol.IDENT:
                return "IDENT";
            case Symbol.NUMBER:
                return "NUMBER";
            case Symbol.PLUS:
                return "PLUS";
            case Symbol.MINUS:
                return "MINUS";
            case Symbol.MULT:
                return "MULT";
            case Symbol.DIV:
                return "DIV";
            case Symbol.LT:
                return "LT";
            case Symbol.LE:
                return "LE";
            case Symbol.GT:
                return "GET";
            case Symbol.GE:
                return "GE";
            case Symbol.NEQ:
                return "NEQ";
            case Symbol.EQ:
                return "EQ";
            case Symbol.ASSIGN:
                return "ASSIGN";
            case Symbol.LEFTPAR:
                return "LEFTPAR";
            case Symbol.RIGHTPAR:
                return "RIGHTPAR";
            case Symbol.SEMICOLON:
                return "SEMICOLON";
            case Symbol.VAR:
                return "VAR";
            case Symbol.BEGIN:
                return "BEGIN";
            case Symbol.END:
                return "END";
            case Symbol.IF:
                return "IF";
            case Symbol.THEN:
                return "THEN";
            case Symbol.ELSE:
                return "ELSE";
            case Symbol.ENDIF:
                return "ENDIF";
            case Symbol.COMMA:
                return "COMMA";
            case Symbol.READ:
                return "READ";
            case Symbol.WRITE:
                return "WRITE";
            case Symbol.COLON:
                return "COLON";
            case Symbol.INTEGER:
                return "INTEGER";
            case Symbol.BOOLEAN:
                return "BOOLEAN";
            case Symbol.CHAR:
                return "CHAR";
            case Symbol.CHARACTER:
                return "CHARACTER";
            case Symbol.TRUE:
                return "TRUE";
            case Symbol.FALSE:
                return "FALSE";
            case Symbol.OR:
                return "OR";
            case Symbol.AND:
                return "AND";
            case Symbol.REMAINDER:
                return "REMAINDER";
            case Symbol.NOT:
                return "NOT";
            default:
                return "ISSO NAO DEVIA APARECER";
        }
    }
}
