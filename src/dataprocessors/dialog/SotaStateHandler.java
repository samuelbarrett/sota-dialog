package dataprocessors.dialog;

import dataprocessors.DataProcessor;
import dataprocessors.sota.SotaDialogController.SotaState;
import dataprocessors.sota.SotaDialogController.SotaStateData;
import datatypes.Data;
import eventsystem.EventGenerator;

/**
 * This class handles state communication from the Sota robot.
 * It receives state data from the Sota and stores it for the StateManager to access.
 */
public class SotaStateHandler extends DataProcessor {

    private SotaState sotaState;

    public SotaStateHandler() {        
        this.sotaState = null;
    }

    @Override
    protected Data process(Data input, EventGenerator sender) {
        SotaStateData stateData = (SotaStateData)input;
        this.sotaState = stateData.data;
        return null;
    }
    
    public SotaState getSotaState() {
        return this.sotaState;
    }
}
