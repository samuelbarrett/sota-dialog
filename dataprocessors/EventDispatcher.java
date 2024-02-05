package dataprocessors;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import eventhandlers.EventHandler;
import events.DataEvent;
import events.Event;

public class EventDispatcher implements DataProcessor {
   
    private Map<Class<? extends Event>, EventHandler> eventHandlers = new HashMap<>(); 
    private BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>();

    public void registerEventHandler(Class<? extends Event> eventType, EventHandler eventHandler) {
        eventHandlers.put(eventType, eventHandler);
    }

    public void scheduleEvent(Event event) {
        eventQueue.add(event);
    }

    public void dispatchEvent(Event event) {
        eventHandlers.get(event.getClass()).handle(event);
    }

    public void run() {        
        try {
            while(true) {
                dispatchEvent(eventQueue.take());
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }    
    }

    @Override
    public void process(double[] data) {
        scheduleEvent(new DataEvent(data));
    }
}
