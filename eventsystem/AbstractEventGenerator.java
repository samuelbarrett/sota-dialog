package eventsystem;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEventGenerator implements EventGenerator {

    private static EventDispatcher dispatcher;
    private List<EventListener> listeners = new ArrayList<EventListener>();

    public static void setDispatcher(EventDispatcher dispatcher) {
        AbstractEventGenerator.dispatcher = dispatcher;
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