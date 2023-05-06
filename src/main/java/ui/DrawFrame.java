/*
 * 功能：画图
 */

package ui;

import javax.swing.*;
import java.awt.*;
import static ui.Canvas.points;

public class DrawFrame extends JFrame {
    private static final int W = 960;
    private static final int H = 540;
    JPanel jp = null;

    public DrawFrame() {
        setSize(W, H + 50);
        setResizable(false);
        setTitle("图形");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void drawAll() {
        jp = new JPanel() {
            @Override
            public void paint(Graphics graphics) {
                Graphics2D g = (Graphics2D) graphics;
                super.paint(graphics);
                for (MyPoint p : points) {
                    g.setColor(p.getColor());
                    g.fillOval(p.getX() - p.getRadius(), p.getY() - p.getRadius(), 2 * p.getRadius(), 2 * p.getRadius());  // 填充
                }
            }
        };
        jp.setBackground(Color.WHITE);
        this.add(jp);
        this.setVisible(true);
    }
}
