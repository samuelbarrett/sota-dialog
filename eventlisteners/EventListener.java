package eventlisteners;
import eventdispatcher.EventDispatcher;
import events.Event;

public abstract class EventListener {

    protected EventDispatcher dispatcher;

    public abstract void handleEvent(Event event);

    public void setDispatcher(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}