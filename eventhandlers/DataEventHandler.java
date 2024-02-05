
package eventhandlers;
import events.Event;

public class DataEventHandler implements EventHandler {
 
    @Override
    public void handle(Event e) {
        System.out.println("data input event");
        // try {
        //     Thread.sleep(500);
        // } catch (Exception ex) {};
    }

}