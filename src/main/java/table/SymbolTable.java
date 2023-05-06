package table;

import function.*;
import lexer.BaseToken;
import lexer.Token;
import lexer.TokenType;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    public static Map<String, BaseToken> symTab = new HashMap<>();

    static {
        symTab.put("PI", new BaseToken(TokenType.CONST_ID, "PI", 3.1415926, null));
        symTab.put("E", new BaseToken(TokenType.CONST_ID, "E", 2.71828, null));
        symTab.put("COS", new BaseToken(TokenType.FUNC, "COS", 0, new Cos()));
        symTab.put("SIN", new BaseToken(TokenType.FUNC, "SIN", 0, new Sin()));
        symTab.put("TAN", new BaseToken(TokenType.FUNC, "TAN", 0, new Tan()));
        symTab.put("EXP", new BaseToken(TokenType.FUNC, "EXP", 0, new Exp()));
        symTab.put("SQRT", new BaseToken(TokenType.FUNC, "SQRT", 0, new Sqrt()));
        symTab.put("ORIGIN", new BaseToken(TokenType.ORIGIN, "ORIGIN", 0, null));
        symTab.put("SCALE", new BaseToken(TokenType.SCALE, "SCALE", 0, null));
        symTab.put("ROT", new BaseToken(TokenType.ROT, "ROT", 0, null));
        symTab.put("IS", new BaseToken(TokenType.IS, "IS", 0, null));
        symTab.put("FOR", new BaseToken(TokenType.FOR, "FOR", 0, null));
        symTab.put("FROM", new BaseToken(TokenType.FROM, "FROM", 0, null));
        symTab.put("TO", new BaseToken(TokenType.TO, "TO", 0, null));
        symTab.put("T", new BaseToken(TokenType.T, "T", 0, null));
        symTab.put("STEP", new BaseToken(TokenType.STEP, "STEP", 0, null));
        symTab.put("DRAW", new BaseToken(TokenType.DRAW, "DRAW", 0, null));
        symTab.put("COLOR", new BaseToken(TokenType.COLOR, "COLOR", 0, null));
        symTab.put("SIZE", new BaseToken(TokenType.SIZE, "SIZE", 0, null));
    }
}
