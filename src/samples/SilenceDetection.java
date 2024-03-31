package samples;

import org.jfree.util.Log;

import dataprocessors.Convolve;
import dataprocessors.GaussianSmooth;
import dataprocessors.Log10;
import dataprocessors.RMS;
import dataprocessors.SilenceDetector;
import dataprocessors.loggers.Logger;
import dataprocessors.plotters.Plotter;
import dataproviders.DataProvider;
import dataproviders.audio.MicAudioProvider;
import dataproviders.network.UDPReceiver;
import eventsystem.AbstractEventGenerator;
import eventsystem.EventDispatcher;

public class SilenceDetection {

    public static void main(String[] args) {

        EventDispatcher dispatcher = new EventDispatcher();
        AbstractEventGenerator.setDispatcher(dispatcher);

        DataProvider provider = new MicAudioProvider(4000, 2048);
     
        RMS rms = new RMS(1000);
  
        rms.addListener(new Plotter("RMS", 0, 50000, -20000, 20000));

        Log10 log = new Log10();
        rms.addListener(log);

        GaussianSmooth g = new GaussianSmooth(1, 4000);
        log.addListener(g);

        Convolve c = new Convolve(new double[]{-1, -1, 1, 1});
        g.addListener(c);

        SilenceDetector s = new SilenceDetector(8000, 1800, 0.0003);
        c.addListener(s);

        s.addListener(new Logger());

        provider.start();
        dispatcher.run();
    }
    
}
