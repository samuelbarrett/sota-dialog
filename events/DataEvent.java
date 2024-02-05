package events;
public class DataEvent implements Event {
    private double[] data;

    public DataEvent(double[] data) {
        this.data = data;    
    }
}