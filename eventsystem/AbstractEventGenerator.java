package eventsystem;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEventGenerator implements EventGenerator {

    private EventDispatcher dispatcher;
    private List<EventListener> listeners = new ArrayList<EventListener>();

    public AbstractEventGenerator(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void notifyListeners(double[] data) {
        for(EventListener l : this.listeners) {
            dispatcher.scheduleEvent(new Event(data, l));
        }
    }

    @Override
    public void addListener(EventListener l) {
        this.listeners.add(l);
    }
}