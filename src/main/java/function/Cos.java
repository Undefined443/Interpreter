package function;

public class Cos implements BaseFunction {
    @Override
    public double calculate(double para) {
        return Math.cos(para);
    }

    @Override
    public String toString() {
        return "cos";
    }
}
