package Lexer;

import java.util.Hashtable;

/**
 * Created by alexcoelho on 30/09/17.
 */
public class Lexer {
    static private Hashtable<String,Integer> keywordsTable;
    private int pos;

    static {
        keywordsTable = new Hashtable();
        keywordsTable.put( "var", new Integer(Symbol.VAR) );
        keywordsTable.put( "begin", new Integer(Symbol.BEGIN) );
        keywordsTable.put( "end", new Integer(Symbol.END) );
        keywordsTable.put( "if", new Integer(Symbol.IF) );
        keywordsTable.put( "then", new Integer(Symbol.THEN) );
        keywordsTable.put( "else", new Integer(Symbol.ELSE) );
        keywordsTable.put( "endif", new Integer(Symbol.ENDIF) );
        keywordsTable.put( "read", new Integer(Symbol.READ) );
        keywordsTable.put( "write", new Integer(Symbol.WRITE) );
        keywordsTable.put( "integer", new Integer(Symbol.INTEGER) );
        keywordsTable.put("boolean", new Integer(Symbol.BOOLEAN) );
        keywordsTable.put("char", new Integer(Symbol.CHAR) );
        keywordsTable.put("true", new Integer(Symbol.TRUE) );
        keywordsTable.put("false", new Integer(Symbol.FALSE) );
        keywordsTable.put("and", new Integer(Symbol.AND) );
        keywordsTable.put("or", new Integer(Symbol.OR) );
        keywordsTable.put("not", new Integer(Symbol.NOT) );
    }

    public Lexer(char[] input){
        System.out.print(input);
    }

    public void nextToken(){

    }

    public int getToken(){
        return 0;
    }
}
