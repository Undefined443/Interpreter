package ui;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.*;
import static java.lang.Math.round;

public class Canvas {
    private static double[] originPos = {0.0, 0.0};
    private static double rots = 0.0;
    private static double[] scale = {100.0, 100.0};
    private static Color color = Color.RED;
    private static double radius = 5.0;
    static ArrayList<MyPoint> points = new ArrayList<>();

    public static void draw(double x, double y) {
        // 比例变换
        x *= Canvas.getScale()[0];
        y *= Canvas.getScale()[1];

        // 旋转变换
        double R = sqrt(pow(x, 2) + pow(y, 2));  // 半径
        double radian;  // 弧度
        if (x != 0) {
            radian = atan(y / x);
        } else {
            if (y > 0) {
                radian = PI / 2;
            } else {
                radian = -PI / 2;
            }
        }


        if (x < 0) {  // atan() 只能取到 -90° ~ 90°，所以如果 x < 0，需要加上 180°
            radian += PI;
        }

        x = R * cos(radian - Canvas.getRots());
        y = R * sin(radian - Canvas.getRots());

        // 平移变换
        x += Canvas.getOriginPos()[0];
        y += Canvas.getOriginPos()[1];

        // 画点
        points.add(new MyPoint(round((float) x), round((float) y), Canvas.getColor(), round((float) Canvas.getRadius())));
    }

    // Sets & Gets
    public static void setOriginPos(double[] originPos) { Canvas.originPos = originPos; }

    public static void setRots(double rots) {
        Canvas.rots = rots;
    }

    public static void setScale(double[] scale) {
        Canvas.scale = scale;
    }

    public static void setColor(Color c) { color = c; }

    public static void setRadius(double r) { radius = r; }

    public static double[] getOriginPos() {
        return originPos;
    }

    public static double getRots() {
        return rots;
    }

    public static double[] getScale() {
        return scale;
    }

    public static Color getColor() {
        return color;
    }

    public static double getRadius() { return radius; }
}
