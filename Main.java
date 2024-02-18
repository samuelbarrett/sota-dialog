import dataprocessors.Logger;
import dataprocessors.RMS;
import dataproviders.MicAudioProvider;
import eventsystem.EventDispatcher;

public class Main {
    public static void main(String[] args) {
        
        EventDispatcher dispatcher = new EventDispatcher();
        MicAudioProvider provider = new MicAudioProvider(dispatcher, 22050, 1024);

        RMS rms = new RMS(dispatcher);
        Logger logger = new Logger(dispatcher);

        provider.addListener(rms);
        rms.addListener(logger);

        provider.start();
        dispatcher.run();
    }

}

