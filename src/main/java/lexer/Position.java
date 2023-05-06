package lexer;

public class Position {
    private int line = 1;
    private int col = 0;

    public Position() {
    }

    public Position(Position p) {
        line = p.line;
        col = p.col;
    }

    public Position(int line, int col) {
        this.line = line;
        this.col = col;
    }

    void nextLine() {
        line++;
        col = 0;
    }

    void nextCol() {
        ++col;
    }

    void prevCol() {
        if (--col < 0) col = 0;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "{line:" + line + ",col:" + col + "}";
    }
}
