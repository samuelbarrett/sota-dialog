import java.io.File;

import dataprocessors.Convolve;
import dataprocessors.Log10;
import dataprocessors.RMS;
import dataprocessors.SilenceDetector;
import dataprocessors.loggers.CSVWriter;
import dataprocessors.loggers.Logger;
import dataprocessors.network.UDPSender;
import dataprocessors.plotters.MultiPlotter;
import dataprocessors.plotters.Plotter;
import dataprocessors.sota.SotaOutputController;
import dataprocessors.GaussianSmooth;
import dataproviders.DataProvider;
import dataproviders.audio.MicAudioProvider;
import dataproviders.network.UDPReceiver;
import eventsystem.AbstractEventGenerator;
import eventsystem.EventDispatcher;

public class Main {
    public static void main(String[] args) {
        
        EventDispatcher dispatcher = new EventDispatcher();
       
        DataProvider provider = new UDPReceiver(5000, 6000);

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

        s.addListener(new UDPSender("140.193.121.14", 5001));
     
        provider.start();
        dispatcher.run();
    }



}

