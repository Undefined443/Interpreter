package test;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;

import java.util.Scanner;

public class TestLexer {
    public static void main(String[] args) {
        String path;
        if (args.length != 1) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("File Name:");
            path = scanner.next();
        } else {
            path = args[0];
        }

        Lexer lexer;
        try {
            lexer = new Lexer(path);
        } catch (Exception e) {
            System.out.println("File not found!");
            return;
        }

        System.out.printf("|%-10s|%10s|%10s|%10s|%16s|\n", "TYPE", "LEXEME", "VALUE", "FUNCTION", "POSITION");
        System.out.println("|----------|----------|----------|----------|----------------|");
        Token t;
        while ((t = lexer.getToken()).getType() != TokenType.NONTOKEN) {
            System.out.printf("|%-10s|%10s|%10f|%10s|%16s|\n", t.getType(), t.getLexeme(), t.getValue(), t.getFunction(), t.getPos());
        }
    }
}
