package events;
public class PCMDataEvent extends Event {
    public PCMDataEvent(double[] data) {
        this.data = data;    
    }
}