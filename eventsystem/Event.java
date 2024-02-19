package eventsystem;

import datatypes.Data;

public class Event {
    
    private Data data; 
    private EventListener listener;

    public Event(Data d, EventListener l) {
        this.data = d;
        this.listener = l;
    }

    public void handle() {
        this.listener.handle(this.data);
    }
}