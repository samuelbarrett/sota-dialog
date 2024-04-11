package samples;

import dataprocessors.Convolve;
import dataprocessors.Derivative;
import dataprocessors.GaussianSmooth;
import dataprocessors.Log10;
import dataprocessors.RMS;
import dataprocessors.SilenceDetector;
import dataprocessors.sota.SotaOutputController;
import dataproviders.DataProvider;
import dataproviders.audio.MicAudioProvider;
import eventsystem.EventDispatcher;

public class SotaDemo {
    public static void main(String [] args) {
        EventDispatcher dispatcher = new EventDispatcher();
     
        DataProvider provider = new MicAudioProvider(4000, 1024);
     
        RMS rms = new RMS(1000);
        provider.addListener(rms);

        Log10 log = new Log10();
        rms.addListener(log);

        GaussianSmooth g = new GaussianSmooth(1, 4000);
        log.addListener(g);

        Derivative d = new Derivative();
        g.addListener(d);

        SilenceDetector s = new SilenceDetector(8000, 8000, 0.0003);
        d.addListener(s);

        s.addListener(new SotaOutputController());

        provider.start();
        dispatcher.run();
    }
    
}
