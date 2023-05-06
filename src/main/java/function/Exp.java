package function;

public class Exp implements BaseFunction {
    @Override
    public double calculate(double para) {
        return Math.exp(para);
    }

    @Override
    public String toString() {
        return "exp";
    }
}

    

