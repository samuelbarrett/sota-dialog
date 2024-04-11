package samples;

import dataprocessors.network.UDPSender;
import dataprocessors.sota.SotaOutputController;
import dataproviders.DataProvider;
import dataproviders.audio.MicAudioProvider;
import dataproviders.network.UDPReceiver;
import eventsystem.EventDispatcher;

//demo of sending data and receiving data on Sota using UDP
//note: MicAudioProvider currently outputs double data, which makes data sent over the network larger than necessary
//if better peformance is needed then short data should be sent instead
public class SotaUDPDemo {
    public static void main(String [] args) {
        EventDispatcher dispatcher = new EventDispatcher();
     

        DataProvider mic = new MicAudioProvider(4000, 1024);

        UDPSender sender = new UDPSender("140.193.235.90", 7777);
        mic.addListener(sender);

        DataProvider receiver = new UDPReceiver(8888, 6000);
        receiver.addListener(new SotaOutputController());
     
        mic.start();
        receiver.start();

        dispatcher.run();
    }
    
}
