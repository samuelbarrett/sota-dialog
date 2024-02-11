package dataproviders;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import eventdispatcher.DataProcessor;


public class FileAudioProvider extends DataProvider   {
    private DataProcessor processor;
    private File audioFile;
    private int bufferSize;

    public FileAudioProvider(File audioFile, int bufferSize, DataProcessor processor) {
        this.audioFile = audioFile;
        this.bufferSize = bufferSize;
        this.processor = processor;
    } 

    @Override
    public void run() {
        try {

            byte[] buffer = new byte[this.bufferSize];
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.audioFile);

            while(audioStream.read(buffer) >=0) {
                processor.process(null);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
