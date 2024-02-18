import java.io.File;

import dataprocessors.Logger;
import dataprocessors.RMS;
import dataproviders.DataProvider;
import dataproviders.FileAudioProvider;
import dataproviders.MicAudioProvider;
import eventsystem.EventDispatcher;

public class Main {
    public static void main(String[] args) {
        
        EventDispatcher dispatcher = new EventDispatcher();
       
        DataProvider provider = new MicAudioProvider(dispatcher, 22050, 1024);
        //DataProvider provider = new FileAudioProvider(dispatcher, new File("test.wav"), 1024);

        RMS rms = new RMS(dispatcher);
        Logger logger = new Logger(dispatcher);

        provider.addListener(rms);
        rms.addListener(logger);

        provider.start();
        dispatcher.run();
    }

}

