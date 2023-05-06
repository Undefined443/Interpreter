package function;

public class Tan implements BaseFunction {
    @Override
    public double calculate(double para) {
        return Math.tan(para);
    }

    @Override
    public String toString() {
        return "tan";
    }
}
