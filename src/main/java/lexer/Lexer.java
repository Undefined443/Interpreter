/*
 * 功能：完成词法分析，向语法分析器提供记号
 */

package lexer;

import table.SymbolTable;

import java.io.*;
import java.util.ArrayList;

public class Lexer {
    private final Position currentPos;
    private final DFA dfa;
    private final ArrayList<Character> inputCharSequence;
    private int index;

    // 初始化构造器
    public Lexer(String fileName) throws Exception {
        dfa = new DFA();
        index = 0;

        File file = new File(fileName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        inputCharSequence = new ArrayList<>();

        int temp;
        while ((temp = bufferedReader.read()) != -1) {
            inputCharSequence.add((char) temp);  // 将文件中的字符读入到字符数组
        }

        bufferedReader.close();
        currentPos = new Position(1, 0);
    }

    // 获得一个记号，核心函数
    public Token getToken() {
        char firstChar;
        int lastState;
        int toBeContinue;  // 是否需要重新获取记号
        Token t;  // 记号

        do {
            t = new Token();
            firstChar = preProcess();  // 预处理，跳过空格和换行，获取第一个非空字符
            t.setPos(currentPos);

            if (firstChar == 0) {  // 读到文末，结束
                t.setType(TokenType.NONTOKEN);
                return t;
            }

            lastState = scan_move(t, firstChar);  // 进行状态转移，得到终态
            toBeContinue = postProcess(t, lastState);  // 处理记号的类型，若读取到的是注释，则重新获取一个记号
        } while (toBeContinue != 0);
        return t;  // 返回读取到到记号
    }

    // 从字符数组中获得一个字符
    public char getChar() {
        int length = inputCharSequence.size();
        if (index == length) return 0;  // 读到文末，结束

        currentPos.nextCol();  // 列号加一
        if (inputCharSequence.get(index) == '\n') {  // 进入下一行
            currentPos.nextLine();
        }

        return inputCharSequence.get(index++);  // 返回当前字符，并将指针后移
    }

    // 预处理，跳过空格和换行，返回第一个非空字符
    public char preProcess() {
        char current_char;
        do {
            current_char = getChar();  // 读取一个字符
            if (current_char == 0) return 0;  // 读到文末，结束
        } while (Character.isWhitespace(current_char));  // 跳过空格和换行
        return current_char;
    }

    // 开始一次新的状态转移
    public int scan_move(Token token, char firstChar) {
        int current_state;
        int next_state;
        char current_char = firstChar;
        current_state = dfa.get_start_state();  // current_state = 0

        // 从初态开始，不断读取字符，进行状态转移，直到无法再进行状态转移
        while (true) {
            next_state = dfa.move(current_state, current_char);
            if (next_state < 0) {  // 无法进行状态转移
                if (token.isEmpty()) {  // 尚未进行任何状态转移，说明读取到的字符不在字符集中
                    token.setLexeme(current_char);  // 将该字符作为记号的词素
                } else {
                    backChar(current_char);  // 回退一个字符并结束本次分析
                }
                break;
            }

            token.appendLexeme(current_char);
            current_state = next_state;
            current_char = getChar();  // 获取下一个字符
            if (current_char == 0) break;  // 读到文末了
        }
        return current_state;
    }

    // 处理记号的类型
    public int postProcess(Token t, int last_state) {
        int to_be_continue = 0;
        TokenType tk = dfa.stateType(last_state);  // 获得终态对应的记号类型
        t.setType(tk);

        // 根据记号类型进行后处理
        switch (tk) {
            case ID -> {
                if (SymbolTable.symTab.containsKey(t.getUpperText())) {  // 查找符号表，若存在该变量
                    BaseToken tmp = SymbolTable.symTab.get(t.getUpperText());  // 从符号表中获得该变量的信息
                    // 将变量信息赋给当前记号
                    t.setType(tmp.getType());
                    t.setValue(tmp.getValue());
                    t.setFunction(tmp.getFunction());
                }
            }
            case CONST_ID -> t.setValue(Double.parseDouble(t.getLexeme()));  // 将常量值赋给记号
            case COMMENT -> {  // 跳过注释
                char c;
                do {
                    c = getChar();
                } while (c != '\n' && c != '\r' && c != 0);
                to_be_continue = 1;  // 需要重新获取一个记号
            }
            default -> {  // 其他情况不需要处理
            }
        }
        return to_be_continue;
    }

    // 回退一个字符
    public void backChar(char c) {
        if (c == 0 || c == '\n') return;
        currentPos.prevCol();
        index--;
    }
}