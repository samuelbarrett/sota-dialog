import java.io.File;

import dataprocessors.CSVWriter;
import dataprocessors.Convolve;
import dataprocessors.Log10;
import dataprocessors.RMS;
import dataprocessors.GaussianSmooth;
import dataproviders.DataProvider;

import dataproviders.MicAudioProvider;
import eventsystem.AbstractEventGenerator;
import eventsystem.EventDispatcher;

public class Main {
    public static void main(String[] args) {
        
        EventDispatcher dispatcher = new EventDispatcher();
        AbstractEventGenerator.setDispatcher(dispatcher);
       
        DataProvider provider = new MicAudioProvider(4000, 1024);
        //DataProvider provider = new FileAudioProvider(new File("test.wav"), 1024);

        RMS rms = new RMS(1000);
        provider.addListener(rms);

        Log10 log = new Log10();
        rms.addListener(log);

        GaussianSmooth s = new GaussianSmooth(1, 4000);
        log.addListener(s);

        Convolve c = new Convolve(new double[]{-1, -1, 1, 1});
        s.addListener(c);

        provider.addListener(new CSVWriter("./samples.csv"));
        rms.addListener(new CSVWriter("./rms.csv"));
        log.addListener(new CSVWriter("./log.csv"));
        s.addListener(new CSVWriter("./smoothed.csv"));
        c.addListener(new CSVWriter("./derivative.csv"));

        provider.start();
        dispatcher.run();
    }



}

