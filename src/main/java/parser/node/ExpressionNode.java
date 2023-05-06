package parser.node;

import lexer.Token;
import static table.SymbolTable.symTab;
import static java.lang.Math.pow;

public class ExpressionNode implements Node {
    private Token token;
    private ExpressionNode left;
    private ExpressionNode right;  // 只有单节点的话，默认用右节点当单节点

    public ExpressionNode(Token token, ExpressionNode left, ExpressionNode right) {
        this.token = token;
        this.left = left;
        this.right = right;
    }

    // 获得表达树的值
    public double getValue(ExpressionNode node) {
        if (node == null) return 0;

        double leftVal = getValue(node.left);
        double rightVal = getValue(node.right);

        return switch (node.token.getType()) {
            case PLUS -> leftVal + rightVal;
            case MINUS -> leftVal - rightVal;
            case MUL -> leftVal * rightVal;
            case DIV -> leftVal / rightVal;
            case POWER -> pow(leftVal, rightVal);
            case FUNC -> symTab.get(node.token.getLexeme().toUpperCase()).getFunction().calculate(rightVal);
            case T, ID -> symTab.get(node.token.getLexeme().toUpperCase()).getValue();
            case CONST_ID -> node.token.getValue();
            default -> Double.NaN;
        };
    }

    public void printTree(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.println(token.getType());
        if (left != null) {
            left.printTree(depth + 1);
        }
        if (right != null) {
            right.printTree(depth + 1);
        }
    }

    // Sets & Gets
    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public void setLeft(ExpressionNode left) {
        this.left = left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
    }
}
