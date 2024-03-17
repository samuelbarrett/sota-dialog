package dataprocessors;

import datatypes.Data;
import datatypes.DoubleData;
import eventsystem.EventGenerator;

public class RMS extends DataProcessor {

    private double[] buffer;
    private int bufferIndex = 0;

    public RMS(int windowSize) {
        this.buffer = new double[windowSize];
    }
    
    @Override
    protected Data process(Data input, EventGenerator sender) {

        DoubleData doubleInput = (DoubleData)input; 
        double[] data = doubleInput.data;
        double[] output = new double[data.length];

        for(int i = 0; i < data.length; i++) {
            buffer[bufferIndex] = data[i];
            output[i] = rms();
            bufferIndex = (bufferIndex + 1) % buffer.length;
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
