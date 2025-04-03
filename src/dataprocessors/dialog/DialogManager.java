package dataprocessors.dialog;

import dataprocessors.DataProcessor;
import dataprocessors.SilenceDetector.SilenceStatusData;
import datatypes.Data;
import dialog.DialogStateModel;
import eventsystem.EventGenerator;

/**
 * Manages the dialog system server-side for the Sota robot.
 * Implemented as a DataProcessor that receives data from the Sota (via UDPReceiver)
 * to simplify Sota sending updated state to the server.
 */
public class DialogManager extends DataProcessor {

    DialogStateModel stateModel;

    public DialogManager(DialogStateModel stateModel) {
        this.stateModel = stateModel;
    }

    @Override
    protected Data process(Data input, EventGenerator sender) {
        SilenceStatusData silenceStatus = (SilenceStatusData) input;
        this.stateModel.setSilenceState(silenceStatus);
        return this.stateModel.getState();
    }
}