package function;

public class Sqrt implements BaseFunction {
    @Override
    public double calculate(double para) {
        return Math.sqrt(para);
    }

    @Override
    public String toString() {
        return "sqrt";
    }
}
