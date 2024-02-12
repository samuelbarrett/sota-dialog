package events;

public class RMSEvent implements Event {
    private double[] data;

    public RMSEvent(double[] data) {
        this.data = data;    
    }
}