package Compiler;

import AST.SimpleExpr;
import Lexer.*;


public class Compiler {

    private Lexer lexer;

    public Compiler(Lexer lexer) {
        this.lexer = lexer;
    }

    Number number() {
        lexer.nextToken();
        if(lexer.token == Symbols.PLUS){

        }
        else if (lexer.token == Symbols.MINUS){

        }

    }

    /**
     * Funçao equivalento ao:
     * SimpleExpr ::= NumberType | Variable | "true" | "false" | Character | ’(’ Expr ’)’ | "not" SimpleExpr | AddOp SimpleExpr
     *
     * @return Seu equivalmente.
     */
    SimpleExpr simpleExpr() {
        lexer.nextToken();
        switch (lexer.token) {
            case Symbols.
        }

    }
}
