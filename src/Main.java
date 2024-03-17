import java.io.File;

import dataprocessors.CSVWriter;
import dataprocessors.Convolve;
import dataprocessors.Log10;
import dataprocessors.Plotter;
import dataprocessors.RMS;
import dataprocessors.SilenceDetectorTest;
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

        c.addListener(new SilenceDetectorTest());

        // provider.addListener(new CSVWriter("./samples.csv"));
        // rms.addListener(new CSVWriter("./rms.csv"));
        // log.addListener(new CSVWriter("./log.csv"));
        // s.addListener(new CSVWriter("./smoothed.csv"));
        // c.addListener(new CSVWriter("./derivative.csv"));

        //provider.addListener(new Plotter("samples", 0, 25000, -32000, 32000));
        //s.addListener(new Plotter("smoothed rms", 0, 25000, 0, 8.0));
        c.addListener(new Plotter("derivative", 0, 25000, -0.006, 0.006));

        provider.start();
        dispatcher.run();
    }



}

