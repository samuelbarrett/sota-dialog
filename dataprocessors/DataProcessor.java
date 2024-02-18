package dataprocessors;

import eventsystem.AbstractEventGenerator;
import eventsystem.EventDispatcher;
import eventsystem.EventGenerator;
import eventsystem.EventListener;

public abstract class DataProcessor extends AbstractEventGenerator implements EventGenerator, EventListener {

    public DataProcessor(EventDispatcher dispatcher) {
        super(dispatcher);
    }

    protected abstract double[] process(); //change to custom data type

    @Override
    public void handle() {
        double[] data = this.process();   
        this.notifyListeners(data);
    }
}