package dataprocessors;

import eventsystem.EventDispatcher;

public class Logger extends DataProcessor {

    public Logger(EventDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    protected double[] process() {
        System.out.println("Hi!");
        return null;
    }
}
