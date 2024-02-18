package eventsystem;

public interface EventGenerator {
    public void addListener(EventListener l);
    public void notifyListeners(double[] data);
}
