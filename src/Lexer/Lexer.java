package Lexer;

import com.sun.javafx.scene.layout.region.Margins;
import com.sun.xml.internal.ws.commons.xmlutil.Converter;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by alexcoelho on 30/09/17.
 */
public class Lexer {
    private Token tokenObj;
    private char [] input;
    private int pos;
    private int line;
    private int token;
    private String error;
    static private Hashtable<String,Integer> keywordsTable;
    Queue<Token> tokens;

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
        token = 100;
        error = "";
        pos = 0;
        line = 1;
        this.input = input;
        tokens = new LinkedList<Token>();
        generateTokens();
    }

    /**
     * Generate all tokens and storage in queue
     */
    private void generateTokens() {
        int  aux = 0;
        char ch, chb;

        while(token != Symbol.EOF){
            ch = this.input[pos];

            if(ch == ' '){
                pos++;
                continue;
            }
            //line broke
            if(ch == '\n') {
                line++;
                pos++;
                continue;
            }
            //end of file
            if(ch == '\0') {
                token = Symbol.EOF;
                continue;
            }
            else{
                //ch Before
                chb = this.input[pos + 1];
                //comment
                if(ch == '/' && chb == '/' ){
                    if(!nextLine()) continue;
                }else{
                    //Identfy if is a seguence of characteres
                    if(Character.isLetter(ch)){
                        identfyCharacter();
                    }
                    //Identify if is a seguence of digits
                    else if (Character.isDigit(ch)){
                        identfyDigit();
                    }else{
                        //pos++;
                        identfyOp();
                    }
                }
            }

        }
        System.out.println("Tokens generated");

    }

    /**
     * Identify a operators
     */
    private void identfyOp() {
        char ch = this.input[pos];
        String val = "";
        switch (ch){
            case '+' :
                token = Symbol.PLUS;
                val = "+";
                break;
            case '-' :
                token = Symbol.MINUS;
                val = "-";
                break;
            case '*' :
                token = Symbol.MULT;
                val = "*";
                break;
            case '/' :
                token = Symbol.DIV;
                val = "/";
                break;
            case '%' :
                token = Symbol.REMAINDER;
                val = "%";
                break;
            case '<' :
                pos++;
                val = "<";
                ch = this.input[pos];
                if ( ch == '=' ) {
                    token = Symbol.LE;
                    val = "<=";
                }else if ( ch == '>' ) {
                    token = Symbol.NEQ;
                    val = "<>";
                }
                else
                    token = Symbol.LT;
                break;
            case '>' :
                pos++;
                val = ">";
                ch = this.input[pos];
                if ( ch == '=' ) {
                    token = Symbol.GE;
                    val = ">=";
                }
                else
                    token = Symbol.GT;
                break;
            case '=' :
                pos++;
                val = "=";
                ch = this.input[pos];
                if ( ch == '=' ) {
                    token = Symbol.EQ;
                    val = "==";
                }
                else
                    token = Symbol.ASSIGN;
                break;
            case '(' :
                token = Symbol.LEFTPAR;
                val = "(";
                break;
            case ')' :
                token = Symbol.RIGHTPAR;
                val = ")";
                break;
            case ',' :
                token = Symbol.COMMA;
                val = ",";
                break;
            case ';' :
                token = Symbol.SEMICOLON;
                val = ";";
                break;
            case ':' :
                token = Symbol.COLON;
                val = ":";
                break;
            case '\'' :
                token = Symbol.CHARACTER;
                pos++;
                if ( input[pos] != '\'' )
                    error = "Illegal literal character" + input[pos-1];
                pos++;
                break;
            default :
                error = "Invalid Character: '" + ch + "'";
        }

        tokens.add(new Token(token, pos, line, val));
        pos++;
    }

    /**
     * Identify Digits and insert in queue
     */
    private void identfyDigit() {
        StringBuffer str = new StringBuffer();
        char ch = this.input[pos];
        int value;

        while (Character.isDigit(ch)){
            str.append(ch);
            pos++;
            ch = this.input[pos];
        }

        token = Symbol.NUMBER;
        value = Integer.valueOf(str.toString());
        //not verify int limits because here is for generate tokens
        tokens.add(new Token(Symbol.NUMBER, pos, line, str.toString()));
    }

    /**
     * Find characteres and insert in queue
     */
    private void identfyCharacter() {
        StringBuffer str = new StringBuffer();
        char ch = this.input[pos];
        Queue<Token> aux = new LinkedList<Token>();

        while (Character.isLetter(ch)){
            str.append(ch);
            aux.add(new Token(Symbol.CHARACTER, pos, line, String.valueOf(ch)));
            pos++;
            ch = this.input[pos];
        }

        Object value = Lexer.keywordsTable.get(str.toString());
        if (value == null){
            for (int i = 0; i < str.length(); i++){
                tokens.add(aux.remove());
            }
            token = Symbol.IDENT;
        }else{
            tokens.add(new Token(((Integer) value).intValue(), pos, line, str.toString()));
            token = ((Integer) value).intValue();
        }
        if(Character.isDigit(ch)){
            error = "Word followed by number";
        }
    }

    /**
     * Increment line and pos until next line
     * @return true if this line have EOF
     */
    private boolean nextLine() {
        while(this.input[pos] != '\n'){
            pos++;

            if(this.input[pos] == '\0'){
                return false;
            }
        }
        line++;
        return true;
    }

    public void nextToken(){
        this.tokenObj = this.tokens.poll();
    }

    public int getToken(){
        return this.tokenObj.id;
    }

    public int getTokenLine(){return this.tokenObj.line;}
}
