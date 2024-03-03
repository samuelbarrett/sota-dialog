package dataprocessors;

import datatypes.Data;
import datatypes.DoubleData;

public class Log10 extends DataProcessor {

    @Override
    protected Data process(Data input) {
        DoubleData doubleInput = (DoubleData)input; 
        double[] data = doubleInput.data;
        double[] output = new double[data.length];

        for(int i = 0; i < data.length; i++) {
            output[i] = Math.log10(data[i]);
        }

        return new DoubleData(output);
    }
}
