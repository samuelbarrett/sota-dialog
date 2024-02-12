package events;
public class PCMDataEvent implements Event {
    private short[] data;

    public PCMDataEvent(short[] data) {
        this.data = data;    
    }
}