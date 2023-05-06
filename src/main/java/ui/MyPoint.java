package ui;

import java.awt.*;

public class MyPoint {
    private int x;
    private int y;
    private Color color;
    private int radius;

    public MyPoint(int x, int y, Color c, int r) {
        this.x = x;
        this.y = y;
        this.color = c;
        this.radius = r;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
