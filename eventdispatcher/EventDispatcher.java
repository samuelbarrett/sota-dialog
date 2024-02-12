package eventdispatcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import eventlisteners.EventListener;
import events.PCMDataEvent;
import events.Event;

public class EventDispatcher implements DataProcessor {
   
    private Map<Class<? extends Event>, List<EventListener>> listenerMap = new HashMap<>();
    private BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>();

    public void registerEventListener(Class<? extends Event> eventType, EventListener listener) {
        listenerMap.computeIfAbsent(eventType, k -> new ArrayList<EventListener>()).add(listener);
        listener.setDispatcher(this);
    }

    public void scheduleEvent(Event event) {
        eventQueue.add(event);
    }

    public void dispatchEvent(Event event) {
        List<EventListener> listeners = listenerMap.getOrDefault(event.getClass(), Collections.emptyList());
        listeners.forEach(l -> l.handleEvent(event));
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
    public void process(short[] data) {
        scheduleEvent(new PCMDataEvent(data));
    }
}
