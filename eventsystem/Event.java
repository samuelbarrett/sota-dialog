package eventsystem;

public class Event {
    
    private double[] data; //change to custom data type
    private EventListener listener;

    public Event(double[] data, EventListener l) {
        this.data = data;
        this.listener = l;
    }

    public double[] getData() {
        return this.data;
    }

    public EventListener getListener() {
        return this.listener;
    }
}