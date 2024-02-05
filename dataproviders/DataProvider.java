package dataproviders;
public abstract class DataProvider implements Runnable {
    public void start() {
        Thread t = new Thread(this);
        t.start();
    }   
}
