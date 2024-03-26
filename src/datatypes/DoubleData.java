package datatypes;

import java.util.Arrays;

public class DoubleData extends Data {
    public final double[] data;

    public DoubleData(double[] data) {
        this.data = data;
    }

    public String toString() {
        return Arrays.toString(this.data);
    }
}