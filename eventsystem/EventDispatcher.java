package eventsystem;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EventDispatcher {
   
    private BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>();

    public void scheduleEvent(Event event) {
        eventQueue.add(event);
    }

    //loops forever 
    public void run() {         
        try {
            while(true) {
                eventQueue.take().getListener().handle();
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }    
    }
}
