package lexer;

import function.BaseFunction;

public class BaseToken {
    private TokenType type;  // 记号的类别
    private String lexeme;  // 记号的字符串
    private double value;  // 若为常数，记录常数的值
    private BaseFunction function;  // 若为函数，记录函数的指针

    public BaseToken() {
        type = TokenType.ERRTOKEN;
        lexeme = "";
        value = 0;
        function = null;
    }

    public BaseToken(TokenType type, String lexeme, double value, BaseFunction function) {
        this.type = type;
        this.lexeme = lexeme;
        this.value = value;
        this.function = function;
    }

    // Sets & Gets
    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getLexeme() {
        return lexeme + "";
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public BaseFunction getFunction() {
        return function;
    }

    public void setFunction(BaseFunction function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "{ type: " + type +
                ", lexeme: " + lexeme +
                ", value: " + value +
                ", function: " + function +
                " }";
    }
}
