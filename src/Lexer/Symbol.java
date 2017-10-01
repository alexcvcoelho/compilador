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
        NOT = 37,
        //NEW SYMBOLS SHOULD BE ADDED HERE
        LastSymbol = 38;
}
