package Compiler;

import AST.*;
import Lexer.*;
import sun.awt.geom.AreaOp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.SynchronousQueue;


public class Compiler {

    private Lexer lexer;

    public Compiler(Lexer lexer) {
        this.lexer = lexer;
    }

    /**
     * Funcao equivalente ao:
     * Number ::= [’+’|’-’] Digit { Digit }
     *
     * @return NumberType
     * @throws CompilerError Se ocorreu um erro na hora na analize.
     */
    NumberType number() throws CompilerError {

        int sinal = -1;
        if (lexer.getToken() == Symbol.PLUS) {
            sinal = Symbol.PLUS;
        } else if (lexer.getToken() == Symbol.MINUS) {
            sinal = Symbol.MINUS;
        }

        if (sinal != -1)
            lexer.nextToken();

        List<DigitType> list = new ArrayList<>();
        list.add(digit());

        lexer.nextToken();
        while (lexer.getToken() == Symbol.NUMBER) {
            list.add(digit());
        }
        return new NumberType(sinal, list);
    }

    /**
     * Funcao equivalente ao:
     * Digit ::= ’0’| ’1’ | ... | ’9’
     *
     * @return Um {@link DigitType} que representa um digito
     * @throws CompilerError Se ocorreu um erro na hora da analize.
     */
    DigitType digit() throws CompilerError {

        if (lexer.getToken() == Symbol.NUMBER) {
            return new DigitType(lexer.getTokenValue());
        }

        return throwError(Symbol.NUMBER);
    }

    /**
     * Funcao equivalente ao:
     * MultOp ::= ’*’ | ’/’ | ’%’
     *
     * @return Um {@link MultOpType que representa uma operaçao matematica de mesma precedencia que a multiplicacao}
     * @throws CompilerError
     */
    MultOpType multOp() throws CompilerError {
        lexer.nextToken();

        if (lexer.getToken() == Symbol.MULT ||
                lexer.getToken() == Symbol.DIV ||
                lexer.getToken() == Symbol.REMAINDER) {
            return new MultOpType(lexer.getTokenValue());
        }

        return throwError(Symbol.MULT);
    }

    /**
     * Funçao equivalente ao:
     * Ident ::= Letter { Letter }
     *
     * @return Um {@link IdentType que representa uma palavra}
     * @throws CompilerError
     */
    IdentType ident() throws CompilerError {
        List<LetterType> letters = new ArrayList<>();
        letters.add(letter());

        while (lexer.getToken() == Symbol.CHARACTER) {
            lexer.nextToken();
            letters.add(letter());
        }

        return new IdentType(letters);
    }

    Expr simpleExpr() throws CompilerError {
        lexer.nextToken();

        if (lexer.getToken() == Symbol.NUMBER ||
                lexer.getToken() == Symbol.PLUS ||
                lexer.getToken() == Symbol.MINUS) {
            return new SimpleExpr(SimpleExpr.NUMBER);
        }
        if (lexer.getToken() == Symbol.CHARACTER) {
            return new SimpleExpr(SimpleExpr.VAR);
        }
        if (lexer.getToken() == Symbol.TRUE) {
            return new SimpleExpr(SimpleExpr.BOOL, true);
        }
        if (lexer.getToken() == Symbol.FALSE) {
            return new SimpleExpr(SimpleExpr.BOOL, false);
        }
        if (lexer.getToken() == Symbol.LEFTPAR) {
            orExpr();
            lexer.nextToken();
            if (lexer.getToken() != Symbol.RIGHTPAR)
                return throwError(Symbol.RIGHTPAR);
        }
        if (lexer.getToken() == Symbol.NOT) {
            return new UnaryExpr(simpleExpr(), Symbol.NOT);
        }
        if (lexer.getToken() == Symbol.PLUS ||
                lexer.getToken() == Symbol.MINUS) {
            return simpleExpr();
        }

        return null;
    }

    Statement statement() throws CompilerError {
        lexer.nextToken();

        if (lexer.getToken() == Symbol.CHARACTER)
            return assignmentStatement();
        if (lexer.getToken() == Symbol.IF)
            return ifStatement();
        if (lexer.getToken() == Symbol.READ)
            return readStatement();
        if (lexer.getToken() == Symbol.WRITE)
            return writeStatement();

        return null;

    }

    Program program() throws CompilerError {
        lexer.nextToken();
        VarDecList varDecList = null;
        if (lexer.getToken() == Symbol.VAR) {
            varDecList = varDecList();
        }

        CompositeStatement compositeStatement = compositeStatement();

        return new Program(varDecList, compositeStatement);
    }

    Expr orExpr() throws CompilerError {
        List<Expr> exprs = new ArrayList<>();

        exprs.add(andExpr());

        exprs.add(relExpr());

        if (lexer.getToken() == Symbol.OR) {
            int token = lexer.getToken();
            exprs.add(new UnaryExpr(relExpr(), token));
        }

        return new OrExpr(exprs);
    }

    CompositeStatement compositeStatement() throws CompilerError {

        lexer.getToken();
        if (lexer.getToken() != Symbol.BEGIN)
            return throwError(Symbol.BEGIN);

        StatementList statementList = statementList();

        lexer.getToken();
        if (lexer.getToken() != Symbol.END)
            return throwError(Symbol.END);

        return new CompositeStatement(statementList);
    }

    StatementList statementList() throws CompilerError {
        Statement statement = statement();
        lexer.getToken();
        if (lexer.getToken() != Symbol.SEMICOLON)
            return throwError(Symbol.SEMICOLON);

        return new StatementList(statement, statementList());

    }


    AssignmentStatement assignmentStatement() throws CompilerError {

        IdentType ident = ident();

        lexer.getToken();
        if (lexer.getToken() != Symbol.ASSIGN)
            return throwError(Symbol.ASSIGN);

        Expr expr = orExpr();

        return new AssignmentStatement(ident, expr);

    }

    IfStatement ifStatement() throws CompilerError {

        if (lexer.getToken() != Symbol.IF)
            return throwError(Symbol.IF);

        lexer.getToken();
        Expr or = orExpr();

        lexer.getToken();
        if (lexer.getToken() != Symbol.THEN)
            return throwError(Symbol.THEN);

        StatementList ifList = statementList();
        StatementList elseList = null;


        lexer.getToken();
        if (lexer.getToken() == Symbol.ELSE) {
            elseList = statementList();
        }

        return new IfStatement(or, ifList, elseList);

    }

    ReadStatement readStatement() throws CompilerError {

        if (lexer.getToken() != Symbol.READ)
            return throwError(Symbol.READ);

        lexer.getToken();
        if (lexer.getToken() != Symbol.LEFTPAR)
            return throwError(Symbol.LEFTPAR);

        IdentType ident = ident();

        lexer.getToken();
        if (lexer.getToken() != Symbol.RIGHTPAR)
            return throwError(Symbol.RIGHTPAR);

        return new ReadStatement(ident);
    }

    WriteStatement writeStatement() throws CompilerError {

        if (lexer.getToken() != Symbol.WRITE)
            return throwError(Symbol.WRITE);

        lexer.getToken();
        if (lexer.getToken() != Symbol.LEFTPAR)
            return throwError(Symbol.LEFTPAR);

        Expr e = orExpr();

        lexer.getToken();
        if (lexer.getToken() != Symbol.RIGHTPAR)
            return throwError(Symbol.RIGHTPAR);

        return new WriteStatement(e);
    }

    VarDecList varDecList() throws CompilerError {
        List<VarDecList2> varDecList2s = new ArrayList<>();
        varDecList2s.add(varDecList2());

        lexer.nextToken();
        while (lexer.getToken() == Symbol.CHARACTER) {
            varDecList2s.add(varDecList2());
        }

        return new VarDecList(varDecList2s);
    }

    VarDecList2 varDecList2() throws CompilerError {
        List<IdentType> identTypes = new ArrayList<>();
        Type type = null;
        lexer.nextToken();
        identTypes.add(ident());
        while (lexer.getToken() == Symbol.COMMA) {
            lexer.nextToken();
            identTypes.add(ident());
        }

        if (lexer.getToken() == Symbol.COLON) ;
        {
            lexer.nextToken();
            type = type();
        }

        lexer.nextToken();
        if (lexer.getToken() != Symbol.SEMICOLON)
            return throwError(Symbol.SEMICOLON);
        return new VarDecList2(identTypes, type);

    }

    Type type() throws CompilerError {
        if (lexer.getToken() == Symbol.INTEGER)
            return new Type(Type.INTEGER);
        if (lexer.getToken() == Symbol.BOOLEAN)
            return new Type(Type.BOOLEAN);
        if (lexer.getToken() == Symbol.CHAR)
            return new Type(Type.CHAR);

        return throwError(Symbol.INTEGER);
    }

    Expr andExpr() throws CompilerError {
        List<Expr> exprs = new ArrayList<>();

        exprs.add(relExpr());

        if (lexer.getToken() == Symbol.AND) {
            int token = lexer.getToken();
            exprs.add(new UnaryExpr(relExpr(), token));
        }

        return new AndExpr(exprs);
    }

    Expr relExpr() throws CompilerError {
        List<Expr> exprs = new ArrayList<>();

        exprs.add(addEpxr());

        if (lexer.getToken() == Symbol.GT ||
                lexer.getToken() == Symbol.GE ||
                lexer.getToken() == Symbol.LE ||
                lexer.getToken() == Symbol.LE ||
                lexer.getToken() == Symbol.EQ ||
                lexer.getToken() == Symbol.NEQ) {
            int token = lexer.getToken();
            exprs.add(new UnaryExpr(addEpxr(), token));
        }

        return new RelExpr(exprs);
    }

    Expr addEpxr() throws CompilerError {
        List<Expr> exprs = new ArrayList<>();
        exprs.add(multEpxr());

        while (lexer.getToken() == Symbol.PLUS ||
                lexer.getToken() == Symbol.MINUS) {
            int token = lexer.getToken();
            exprs.add(new UnaryExpr(multEpxr(), token));
        }

        return new AddExpr(exprs);
    }

    Expr multEpxr() throws CompilerError {
        List<Expr> exprs = new ArrayList<>();

        exprs.add(simpleExpr());

        while (lexer.getToken() == Symbol.MULT ||
                lexer.getToken() == Symbol.DIV ||
                lexer.getToken() == Symbol.REMAINDER) {
            int token = lexer.getToken();
            return new UnaryExpr(simpleExpr(), token);
        }

        return new MultExpr(exprs);
    }

    RelOpType relOp() throws CompilerError {
        switch (lexer.getToken()) {
            case Symbol.GE:
            case Symbol.GT:
            case Symbol.LE:
            case Symbol.LT:
            case Symbol.EQ:
            case Symbol.NEQ:
                return new RelOpType(lexer.getToken());
            default:
                return throwError(Symbol.GE);
        }
    }

    /**
     * Funçao equivalente ao:
     * AddOp ::= ’+’| ’-’
     *
     * @return Um {@link AddOpType que representa uma operacao de suma ou subtraçao}
     * @throws CompilerError
     */
    AddOpType addOp() throws CompilerError {

        if (lexer.getToken() == Symbol.PLUS ||
                lexer.getToken() == Symbol.MINUS) {
            return new AddOpType(lexer.getToken());
        }

        return throwError(Symbol.PLUS);
    }

    /**
     * Funçao equivalente ao:
     * Letter ::= ’A’ | ’B’| ... | ’Z’| ’a’| ’b’ | ... | ’z’
     *
     * @return Um {@link LetterType que representa uma letra}
     * @throws CompilerError
     */
    LetterType letter() throws CompilerError {
        if (lexer.getToken() == Symbol.CHARACTER) {
            return new LetterType(lexer.getTokenValue());
        }

        return throwError(Symbol.CHAR);
    }

    private <T> T throwError(int token) throws CompilerError {
        throw new CompilerError(String.format("Esperando um %s mas recebeu um %s \n Linha: %s",
                Symbol.toString(token), Symbol.toString(lexer.getToken()), lexer.getTokenLine()));
    }

}
