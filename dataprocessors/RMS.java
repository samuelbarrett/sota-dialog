package dataprocessors;

import eventsystem.EventDispatcher;

public class RMS extends DataProcessor {

    public RMS(EventDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    protected double[] process() {
        System.out.println("RMS");
        return null;
    }
    
}
