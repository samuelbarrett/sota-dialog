import java.io.File;

import dataprocessors.Logger;
import dataprocessors.Plotter;
import dataprocessors.RMS;
import dataproviders.DataProvider;
import dataproviders.FileAudioProvider;
import dataproviders.MicAudioProvider;
import eventsystem.AbstractEventGenerator;
import eventsystem.EventDispatcher;

public class Main {
    public static void main(String[] args) {
        
        EventDispatcher dispatcher = new EventDispatcher();
        AbstractEventGenerator.setDispatcher(dispatcher);
       
        DataProvider provider = new MicAudioProvider(22050, 1024);
        //provider.addListener(new Plotter("Audio Samples", 0, 50000));

        RMS rms = new RMS();
        provider.addListener(rms);
        
        Plotter rmsPlotter = new Plotter("RMS", 0, 1000);
        rms.addListener(rmsPlotter);

        provider.start();
        dispatcher.run();
    }

}

