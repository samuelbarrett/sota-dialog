package dataprocessors;

import java.util.Arrays;

import datatypes.Data;
import datatypes.DoubleData;
import datatypes.ShortData;
import eventsystem.EventGenerator;

public class RMS extends DataProcessor {

    private double[] buffer;
    private int buffIndex = 0;

    public RMS(int windowSize) {
        this.buffer = new double[windowSize];
    }
    
    @Override
    protected Data process(Data input, EventGenerator sender) {

        double[] data;
        if(input instanceof ShortData) {
            ShortData shortInput = (ShortData)input;
            data = shortInput.asDoubleArray();
        } else {
            DoubleData doubleInput = (DoubleData)input; 
            data = doubleInput.data;
        }

        double[] output = new double[data.length];

        for(int i = 0; i < data.length; i++) {
            buffer[buffIndex] = data[i];
            output[i] = rms();
            buffIndex = (buffIndex + 1) % buffer.length;
        }

        return new DoubleData(output);
    }

    private double rms() {
        double sum = 0;
        for(double d : this.buffer) {
            sum += d*d;
        }
        return Math.sqrt(sum / this.buffer.length);
    }
}
