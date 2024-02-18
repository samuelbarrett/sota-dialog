package dataproviders;

import java.io.File;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import eventsystem.EventDispatcher;

public class FileAudioProvider extends DataProvider   {
   
    private File audioFile;
    private int bufferSize;

    public FileAudioProvider(EventDispatcher dispatcher, File audioFile, int bufferSize) {
        super(dispatcher);
        this.audioFile = audioFile;
        this.bufferSize = bufferSize;
    } 

    @Override
    public void run() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.audioFile);

            byte[] buffer = new byte[this.bufferSize];
            double[] samples = new double[this.bufferSize / 2];

            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

            while(audioStream.read(buffer) >= 0) {
                System.out.println("new audio");
                byteBuffer.position(0);
                for(int i = 0; i < this.bufferSize/2; i++) {
                   samples[i] = (double)byteBuffer.getShort();
                }
                this.notifyListeners(samples);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
