package dataprocessors;

import datatypes.Data;
import datatypes.DoubleData;

public class RMS extends DataProcessor {
    @Override
    protected Data process(Data input) {

        //TODO handle ClassCastException
        DoubleData doubleInput = (DoubleData)input; 
        double[] data = doubleInput.data;

        double sum = 0;
        for(double d : data) {
            sum += d*d;
        }
        double rms = Math.sqrt(sum / data.length);
        
        return new DoubleData(new double[]{rms});
    }
}
