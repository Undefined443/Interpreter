package lexer;

public class DFA {
    public DFA() {}

    public int get_start_state() {
        return 0;
    }  // 初态为 0

    public int move(int state_src, char ch) {
        switch (state_src) {
            case 0 -> {
                if (Character.isLetter(ch) || ch == '_') return 1;
                if (Character.isDigit(ch)) return 2;
                if (ch == '*') return 4;
                if (ch == '/') return 6;
                if (ch == '-') return 7;
                if (ch == '+') return 8;
                if (ch == ',') return 9;
                if (ch == ';') return 10;
                if (ch == '(') return 11;
                if (ch == ')') return 12;
            }
            case 1 -> {
                if (Character.isLetter(ch) || Character.isDigit(ch) || ch == '_') return 1;
            }
            case 2 -> {
                if (Character.isDigit(ch)) return 2;
                if (ch == '.') return 3;
            }
            case 3 -> {
                if (Character.isDigit(ch)) return 3;
            }
            case 4 -> {
                if (ch == '*') return 5;
            }
            case 6 -> {
                if (ch == '/') return 13;
            }
            case 7 -> {
                if (ch == '-') return 13;
            }
            default -> {
            }
        }
        return -1;  // 无状态转移，返回 -1
    }

    // 判断状态类型
    public TokenType stateType(int state) {
        return switch (state) {
            case 1 -> TokenType.ID;
            case 2, 3 -> TokenType.CONST_ID;
            case 4 -> TokenType.MUL;
            case 5 -> TokenType.POWER;
            case 6 -> TokenType.DIV;
            case 7 -> TokenType.MINUS;
            case 8 -> TokenType.PLUS;
            case 9 -> TokenType.COMMA;
            case 10 -> TokenType.SEMICO;
            case 11 -> TokenType.L_BRACKET;
            case 12 -> TokenType.R_BRACKET;
            case 13 -> TokenType.COMMENT;
            default -> TokenType.ERRTOKEN;  // 非法输入
        };
    }
}
