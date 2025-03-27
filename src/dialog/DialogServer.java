package dialog;

import dataprocessors.Convolve;
import dataprocessors.GaussianSmooth;
import dataprocessors.Log10;
import dataprocessors.RMS;
import dataprocessors.SilenceDetector;
import dataprocessors.network.UDPSender;
import dataproviders.DataProvider;
import dataproviders.network.UDPReceiver;
import eventsystem.EventDispatcher;

/**
 * Dialog server that runs audio processing on a laptop for Sota to use.
 * Paired with SotaDialog running device-side.
 */
public class DialogServer {

    public static void main(String[] args) {

        EventDispatcher dispatcher = new EventDispatcher();
     
        DataProvider receiver = new UDPReceiver(7777, 6000);
     
        RMS rms = new RMS(1000);
        receiver.addListener(rms);

        Log10 log = new Log10();
        rms.addListener(log);

        GaussianSmooth g = new GaussianSmooth(1, 4000);
        log.addListener(g);

        Convolve c = new Convolve(new double[]{-1, -1, 1, 1});
        g.addListener(c);

        SilenceDetector s = new SilenceDetector(8000, 4000, 0.0003);
        c.addListener(s);

        //return processed data
        UDPSender sender = new UDPSender("10.127.46.9", 8888);
        s.addListener(sender);
        
        receiver.start();
        dispatcher.run();
    }
}