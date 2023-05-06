package function;

public class Sin implements BaseFunction {
    @Override
    public double calculate(double para) {
        return Math.sin(para);
    }

    @Override
    public String toString() {
        return "sin";
    }
}
