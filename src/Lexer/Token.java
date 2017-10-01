package Lexer;

/**
 * Created by alexcoelho on 30/09/17.
 */
public class Token {
    public int id;
    public int pos;
    public int line;
    public String name;

    public Token(int id, int pos, int line, String name){
        this.id = id;
        this.pos = pos;
        this.line = line;
        this.name = name;
    }
}
