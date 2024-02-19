package datatypes;

import java.util.Arrays;

public class DoubleData implements Data {
    public double[] data;

    public DoubleData(double[] data) {
        this.data = data;
    }

    public String toString() {
        return Arrays.toString(this.data);
    }
}