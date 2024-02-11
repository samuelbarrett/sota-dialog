package dataproviders;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import eventdispatcher.DataProcessor;

public class MicAudioProvider extends DataProvider {
    private DataProcessor processor;
    private int sampleRate;
    private int bufferSize;

    public MicAudioProvider(int sampleRate, int bufferSize, DataProcessor processor) {
        this.sampleRate = sampleRate;
        this.bufferSize = bufferSize;
        this.processor = processor;
    } 

    @Override
    public void run() {
        try {
            AudioFormat audioFormat = new AudioFormat(this.sampleRate, 16, 1, true, false);
            DataLine.Info lineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            TargetDataLine dataline = (TargetDataLine)AudioSystem.getLine(lineInfo);
            AudioInputStream audioStream = new AudioInputStream(dataline);

            dataline.open(audioFormat);
            dataline.start();
    
            byte[] buffer = new byte[this.bufferSize];
            short[] samples = new short[this.bufferSize / 2];

            ShortBuffer shortBuffer = ByteBuffer.wrap(buffer).asShortBuffer();

            while(audioStream.read(buffer) >= 0) {
                System.out.println("new audio");
                shortBuffer.position(0);
                shortBuffer.get(samples);
            
                processor.process(samples);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
