package dialog;

import datatypes.StateData;
import dataprocessors.SilenceDetector.SilenceStatusData;
import dataprocessors.SilenceDetector.Status;

/**
 * This class holds the state of the dialog system. Here we handle state updates as they come in
 * from the ToolkitAPI and SilenceDetector.
 */
public class DialogStateModel {
    
    private StateData state;

    public DialogStateModel() {
        this.state = new StateData(new ToolkitState(), new SilenceStatusData(Status.STARTUP));
    }

    public void setToolkitState(ToolkitState state) {
        this.state.setToolkitState(state);
    }

    public void setSilenceState(SilenceStatusData silenceStatus) {
        this.state.setSilenceStatus(silenceStatus);
    }

    public StateData getState() {
        return this.state;
    }
}
