package dataprocessors;

public class Logger extends DataProcessor {
    @Override
    protected double[] process() {
        System.out.println("Hi!");
        return null;
    }
}
