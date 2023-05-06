/*
 * 功能：完成语法分析和语义分析
 */

package parser;

import lexer.BaseToken;
import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import static lexer.TokenType.*;
import parser.node.ExpressionNode;
import parser.node.StatementNode;
import table.SymbolTable;
import ui.Canvas;

import java.awt.*;

public class Parser {
    private final Lexer lexer;
    private final StatementNode statementRoot;  // 语法树的根节点
    private Token currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        statementRoot = new StatementNode();
    }

    public void run() {
        fetchToken();
        program();
    }

    // 抓取一个记号
    public TokenType fetchToken() {
        TokenType tk;
        while (true) {
            currentToken = lexer.getToken();
            if ((tk = currentToken.getType()) == ERRTOKEN) {  // 词法错误
                syntaxError(6, currentToken, null);
                continue;
            }
            break;
        }
        return tk;
    }

    // 语法分析
    // 递归下降分析

    /*
     procedure L() is
     begin
        lookahead := lexan();
        while (lookahead != eof) loop
           E();
           match(';');
        end loop;
     end L;
     */

    // P -> { E ';' }
    public void program() {
        while (currentToken.getType() != NONTOKEN) {  // while (lookahead != eof)
            StatementNode node = statement();  // E()
            if (node != null) {
                if (currentToken.getType() != NONTOKEN) {  // 如果现在就碰到 EOF，说明该语句不完整，不加入语法树
                    statementRoot.addChildren(node);  // 将语句加入语法树
                }
            }
            matchToken(SEMICO);  // match(';')
        }
    }

    /*
        procedure E() is
        begin
            T();
            while (lookahead == '+' || lookahead == '-') loop
                match(lookahead);
                T();
            end loop;
        end E;
     */

    // E -> T { ( + | - ) T }
    public ExpressionNode expression() {
        ExpressionNode left;
        ExpressionNode right;

        left = term();  // T()
        while (currentToken.getType() == PLUS || currentToken.getType() == MINUS) {  // while (lookahead == '+' || lookahead == '-') loop
            Token t = currentToken;
            matchToken(currentToken.getType());  // match(lookahead);
            right = term();  // T()
            left = new ExpressionNode(t, left, right);  // 生成新的语法树节点
        }
        return left;
    }

    /*
        procedure T() is
        begin
            F();
            while (lookahead == '*' || lookahead == '/') loop
                match(lookahead);
                F();
            end loop;
        end T;
     */

    // T -> F { ( * | / ) F }
    public ExpressionNode term() {
        ExpressionNode left;
        ExpressionNode right;

        left = factor();  // F()
        while (currentToken.getType() == MUL || currentToken.getType() == DIV) {  // while (lookahead == '*' || lookahead == '/') loop
            Token t = currentToken;
            matchToken(currentToken.getType());  // match(lookahead);
            right = factor();  // F()
            left = new ExpressionNode(t, left, right);  // 生成新的语法树节点
        }
        return left;
    }

    // F -> [ ( + | - ) F ] | C
    public ExpressionNode factor() {
        ExpressionNode right;
        Token t = currentToken;
        if (t.getType() == PLUS) {
            matchToken(PLUS);
            right = factor();
        } else if (t.getType() == MINUS) {
            matchToken(MINUS);
            right = factor();
            right = new ExpressionNode(t, null, right);
        } else {
            right = component();
        }
        return right;
    }

    // C -> A [ ** C ]
    public ExpressionNode component() {
        ExpressionNode left;
        ExpressionNode right;
        left = atom();
        if (currentToken.getType() == POWER) {
            Token t = currentToken;
            matchToken(POWER);
            right = component();
            left = new ExpressionNode(t, left, right);
        }
        return left;
    }

    /*
        procedure F() is
        begin
            case lookahead is
                '(':
                    match('(');
                    E();
                    match(')');
                 id:
                    match(id);
                 num:
                    match(num);
                 other:
                    error("syntax error2");
            end case;
        end F;
     */

    // A -> ( E ) | id | num | func ( E )
    public ExpressionNode atom() {
        ExpressionNode root = null;
        Token t = currentToken;
        switch (currentToken.getType()) {  // case lookahead is
            case T -> {  // case id:
                matchToken(T);  // match(id);
                root = new ExpressionNode(t, null, null);
            }
            case FUNC -> {
                matchToken(FUNC);
                matchToken(L_BRACKET);
                root = new ExpressionNode(t, null, expression());
                matchToken(R_BRACKET);
            }
            case L_BRACKET -> {  // case '(':
                matchToken(L_BRACKET);  // match('(');
                root = expression();  // E()
                matchToken(R_BRACKET);  // match(')');
            }
            case CONST_ID -> {  // case num:
                matchToken(CONST_ID);  // match(num);
                root = new ExpressionNode(t, null, null);
            }
            default -> {  // case other:
                syntaxError(5, t, null);  // error("syntax error2");
            }
        }
        return root;
    }

    // 尝试匹配一个期望的记号并抓取新的记号。如果不匹配则报错并抓取下一个记号，直到匹配为止
    public void matchToken(TokenType expected) {
        Token previousToken = currentToken;
        if (currentToken.getType() == expected) {
            fetchToken();
        } else {
            while (true) {
                syntaxError(2, currentToken, expected);
                TokenType tk = fetchToken();
                if (tk == expected) {
                    previousToken = currentToken;
                    fetchToken();
                    break;
                } else if (tk == NONTOKEN)
                    break;
            }
        }
    }

    // 语法及语义分析，生成 statement 结点
    // 判断语句类型并调用相应处理函数来生成 statement 结点
    // S -> ORIGIN IS ( E , E )
    //    | SCALE IS ( E , E )
    //    | ROT IS E
    //    | COLOR IS ( E , E , E )
    //    | SIZE IS E
    //    | FOR T FROM E TO E DRAW ( E , E )
    public StatementNode statement() {
        StatementNode root = null;

        switch (currentToken.getType()) {
            case ORIGIN -> root = originStatement();
            case SCALE -> root = scaleStatement();
            case ROT -> root = rotStatement();
            case COLOR -> root = colorStatement();
            case SIZE -> root = sizeStatement();
            case FOR -> root = forStatement();
            default -> syntaxError(1, currentToken, null);
        }
        return root;
    }

    // 匹配一条 ORIGIN 语句
    public StatementNode originStatement() {
        StatementNode root = new StatementNode(currentToken);

        matchToken(ORIGIN);
        matchToken(IS);
        matchToken(L_BRACKET);

        ExpressionNode exp1 = expression();  // 匹配第一个算数表达式
        root.addChildren(exp1);

        matchToken(COMMA);

        ExpressionNode exp2 = expression();  // 匹配第二个算数表达式
        root.addChildren(exp2);

        matchToken(R_BRACKET);

        // 语义处理
        Canvas.setOriginPos(new double[]{exp1.getValue(exp1), exp2.getValue(exp2)});
        return root;
    }

    // 匹配一条 SCALE 语句
    public StatementNode scaleStatement() {
        StatementNode root = new StatementNode(currentToken);

        matchToken(SCALE);
        matchToken(IS);
        matchToken(L_BRACKET);

        ExpressionNode exp1 = expression();  // 匹配第一个算数表达式
        root.addChildren(exp1);

        matchToken(COMMA);

        ExpressionNode exp2 = expression();  // 匹配第二个算数表达式
        root.addChildren(exp2);

        matchToken(R_BRACKET);

        // 语义处理
        Canvas.setScale(new double[]{exp1.getValue(exp1), exp2.getValue(exp2)});
        return root;
    }

    // 匹配一条 ROT 语句
    public StatementNode rotStatement() {
        StatementNode root = new StatementNode(currentToken);

        matchToken(ROT);
        matchToken(IS);
        ExpressionNode exp = expression();
        root.addChildren(exp);

        // 语义处理
        Canvas.setRots(exp.getValue(exp));
        return root;
    }

    // 匹配一条 COLOR 语句
    public StatementNode colorStatement() {
        StatementNode root = new StatementNode(currentToken);

        matchToken(COLOR);
        matchToken(IS);
        matchToken(L_BRACKET);

        ExpressionNode exp1 = expression();
        root.addChildren(exp1);

        matchToken(COMMA);

        ExpressionNode exp2 = expression();
        root.addChildren(exp2);

        matchToken(COMMA);

        ExpressionNode exp3 = expression();
        root.addChildren(exp3);

        matchToken(R_BRACKET);

        // 语义处理
        int r = (int) exp1.getValue(exp1);
        int g = (int) exp2.getValue(exp2);
        int b = (int) exp3.getValue(exp3);
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            syntaxError(7, currentToken, null);
            return null;
        }
        Canvas.setColor(new Color((int) exp1.getValue(exp1), (int) exp2.getValue(exp2), (int) exp3.getValue(exp3)));
        return root;
    }

    // 匹配一条 SIZE 语句
    public StatementNode sizeStatement() {
        StatementNode root = new StatementNode(currentToken);

        matchToken(SIZE);
        matchToken(IS);
        ExpressionNode exp = expression();
        root.addChildren(exp);

        // 语义处理
        Canvas.setRadius(exp.getValue(exp));
        return root;
    }

    // 匹配一条 FOR 语句
    public StatementNode forStatement() {
        StatementNode root = new StatementNode(currentToken);

        matchToken(FOR);

        Token temp = currentToken;

        matchToken(T);
        matchToken(FROM);

        ExpressionNode exp1 = expression();
        root.addChildren(exp1);

        matchToken(TO);

        ExpressionNode exp2 = expression();
        root.addChildren(exp2);

        matchToken(STEP);

        ExpressionNode exp3 = expression();
        root.addChildren(exp3);

        matchToken(DRAW);
        matchToken(L_BRACKET);

        ExpressionNode exp4 = expression();
        root.addChildren(exp4);

        matchToken(COMMA);

        ExpressionNode exp5 = expression();
        root.addChildren(exp5);

        matchToken(R_BRACKET);

        // 直接进行语义处理
        double start = exp1.getValue(exp1);
        double end = exp2.getValue(exp2);
        double step = exp3.getValue(exp3);

        if (start > end)
            syntaxError(3, temp, null);
        if (step < 0)
            syntaxError(4, temp, null);
        else {
            // 查找符号表，设置 T 的值
            BaseToken t = SymbolTable.symTab.get("T");
            t.setValue(start);

            while (t.getValue() < end) {
                Canvas.draw(exp4.getValue(exp4), exp5.getValue(exp5));
                t.setValue(t.getValue() + step);
            }
        }
        return root;
    }

    // 出错处理
    void syntaxError(int type, Token t, TokenType expected) {
        switch (type) {
            case 1 -> System.out.println("Unexpected token \"" + t.getLexeme() + "\" at " + t.getPos() + ", expecting 'ORIGIN' ｜ 'SCALE' ｜ 'ROT' ｜ 'COLOR' | 'SIZE' | 'FOR'.");
            case 2 -> System.out.println("Syntax error at " + t.getPos() + ", expecting " + expected + ", got " + t.getType() + ".");
            case 3 -> System.out.println("FROM number need to be larger than TO number at line " + t.getPos().getLine() + ".");
            case 4 -> System.out.println("Step need to be positive at line " + t.getPos().getLine() + ".");
            case 5 -> System.out.println("Error expression at " + t.getPos() + ".");
            case 6 -> System.out.println("Unexpected token \"" + t.getLexeme() + "\" at " + t.getPos() + ".");
            case 7 -> System.out.println("Color value need to be in range [0, 255] at line " + t.getPos() + ".");
        }
    }

    public StatementNode getStatementRoot() {
        return statementRoot;
    }
}
