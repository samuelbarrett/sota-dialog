
package eventlisteners;
import events.Event;

public class DataEventListener extends EventListener {
 
    @Override
    public void handleEvent(Event e) {
        System.out.println("data input event");
        // try {
        //     Thread.sleep(500);
        // } catch (Exception ex) {};
    }

}