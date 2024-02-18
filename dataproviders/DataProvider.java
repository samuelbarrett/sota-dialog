package dataproviders;

import eventsystem.AbstractEventGenerator;
import eventsystem.EventDispatcher;
import eventsystem.EventGenerator;

public abstract class DataProvider extends AbstractEventGenerator implements EventGenerator, Runnable {

    public DataProvider(EventDispatcher dispatcher) {
        super(dispatcher);
    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
    }   
}
