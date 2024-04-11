package dataprocessors;

public class Derivative extends Convolve {
    public Derivative() {
        super(new double[]{-1, -1, 1, 1});
    }
}
