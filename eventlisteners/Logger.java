package eventlisteners;
import events.Event;

public class Logger extends EventListener {
    @Override
    public void handleEvent(Event event) {
        System.out.println("log data");
    } 
}
