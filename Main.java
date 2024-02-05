import java.io.File;

import dataprocessors.EventDispatcher;
import dataproviders.MicAudioProvider;
import eventhandlers.DataEventHandler;
import events.DataEvent;

public class Main {
    public static void main(String[] args) {
        
        EventDispatcher dispatcher = new EventDispatcher();
        dispatcher.registerEventHandler(DataEvent.class, new DataEventHandler());

        //FileAudioProvider provider = new FileAudioProvider(new File("test.wav"), 10000, dispatcher);
        MicAudioProvider provider = new MicAudioProvider(22050, 1024, dispatcher);
        provider.start();

        dispatcher.run();
    }

}

