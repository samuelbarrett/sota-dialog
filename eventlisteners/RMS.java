
package eventlisteners;
import events.Event;
import events.RMSEvent;

public class RMS extends EventListener {
    @Override
    public void handleEvent(Event e) {
        System.out.println("RMS");

        this.dispatcher.scheduleEvent(new RMSEvent(null));
    }
}