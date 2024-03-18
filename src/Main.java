import java.io.File;


import dataprocessors.CSVWriter;
import dataprocessors.Convolve;
import dataprocessors.Log10;
import dataprocessors.Logger;
import dataprocessors.MultiPlotter;
import dataprocessors.Plotter;
import dataprocessors.RMS;
import dataprocessors.SilenceDetector;
import dataprocessors.SotaOutputController;
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
     
        RMS rms = new RMS(1000);
        provider.addListener(rms);

        Log10 log = new Log10();
        rms.addListener(log);

        GaussianSmooth g = new GaussianSmooth(1, 4000);
        log.addListener(g);

        Convolve c = new Convolve(new double[]{-1, -1, 1, 1});
        g.addListener(c);

        SilenceDetector s = new SilenceDetector(8000, 1800, 0.0003);
        c.addListener(s);

        s.addListener(new SotaOutputController());

        provider.start();
        dispatcher.run();
    }



}

