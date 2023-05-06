package lexer;

import function.BaseFunction;

public class Token {
    private BaseToken basic = new BaseToken();
    private Position pos = new Position();

    public Token() {
        pos.setCol(0);
        pos.setLine(0);
    }

    public Token(BaseToken basic) {
        this.basic = basic;
        pos.setLine(0);
        pos.setCol(0);
    }

    public void setType(TokenType t) {
        basic.setType(t);
    }

    // 判断词素是否为空
    public boolean isEmpty() {
        return basic.getLexeme().length() < 1;
    }

    public void setLexeme(int c) {
            String lexeme = "" + (char)c;
            basic.setLexeme(lexeme);
    }

    public void setValue(double v) {
        if (basic.getType() == TokenType.CONST_ID)
            basic.setValue(v);
    }

    public void setFunction(BaseFunction f) {
        if (basic.getType() == TokenType.FUNC)
            basic.setFunction(f);
    }

    public void appendLexeme(char c) {
        String lexeme = basic.getLexeme();
        lexeme += c;
        basic.setLexeme(lexeme);
    }

    public void setPos(Position pos) {
        this.pos = new Position(pos);
    }

    public String getLexeme() {
        return basic.getLexeme();
    }

    public TokenType getType() {
        return basic.getType();
    }

    public double getValue() {
        return basic.getValue();
    }

    public BaseFunction getFunction() {
        return basic.getFunction();
    }

    public Position getPos() {
        return pos;
    }

    String getUpperText() {
        return basic.getLexeme().toUpperCase();
    }

    public BaseToken getBasic() {
        return basic;
    }

    @Override
    public String toString() {
        return "{ basic: " + basic +
                ", where: " + pos +
                " }";
    }
}
