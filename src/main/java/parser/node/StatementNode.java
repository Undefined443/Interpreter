package parser.node;

import lexer.Token;

import java.util.ArrayList;

public class StatementNode implements Node {
    private Token token;
    private final ArrayList<Node> children;

    public StatementNode() {
        children = new ArrayList<>();
    }

    public StatementNode(Token t) {
        this();
        token = t;
    }

    public void addChildren(Node node) {
        children.add(node);
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public Token getToken() {
        return token;
    }

    public void printTree(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        if (token == null) {
            System.out.println("E");
        } else {
            System.out.println(token.getType());
        }
        for (Node node : children) {
            if (node instanceof StatementNode) {
                node.printTree(depth + 1);
            } else if (node instanceof ExpressionNode) {
                node.printTree(depth + 1);
            }
        }
    }
}
