import java.io.File;

import dataproviders.MicAudioProvider;
import eventdispatcher.EventDispatcher;
import eventlisteners.RMS;
import eventlisteners.DataEventListener;
import eventlisteners.Logger;
import events.PCMDataEvent;
import events.RMSEvent;
import events.LogEvent;

public class Main {
    public static void main(String[] args) {
        
        EventDispatcher dispatcher = new EventDispatcher();

        dispatcher.registerEventListener(PCMDataEvent.class, new DataEventListener());
    
        //FileAudioProvider provider = new FileAudioProvider(new File("test.wav"), 10000, dispatcher);
        MicAudioProvider provider = new MicAudioProvider(22050, 1024, dispatcher);
        provider.start();
        
        dispatcher.run();
    }

}

