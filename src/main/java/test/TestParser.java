package test;

import lexer.Lexer;
import parser.Parser;
import ui.DrawFrame;

import java.util.Scanner;

public class TestParser {
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
        Parser parser = new Parser(lexer);

        parser.run();
        parser.getStatementRoot().printTree(0);
    }
}
