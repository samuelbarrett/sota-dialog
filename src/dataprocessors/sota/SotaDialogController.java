package dataprocessors.sota;

import dataprocessors.DataProcessor;
import dataprocessors.SilenceDetector.SilenceStatusData;
import dataprocessors.SilenceDetector.Status;
import datatypes.Data;
import eventsystem.EventGenerator;
import jp.vstone.RobotLib.*;

import java.awt.Color;

/**
 * A controller for the Sota robot that manages its output for dialog.
 * Interacts with the Sota through the vstone API directly.
 */
public class SotaDialogController extends DataProcessor {
    
    static final String TAG = "SotaDialogController";
    private CRobotPose pose;
    private CSotaMotion motion;
    private CRobotMem mem;

    private Status currentStatus = null;
    private boolean backchannelled;

    public SotaDialogController() {
        this.mem = new CRobotMem();
		this.motion = new CSotaMotion(mem);
		this.pose = new CRobotPose();
        this.backchannelled = false;

        mem.Connect();
        this.motion.InitRobot_Sota();
        this.motion.ServoOn();
        this.updateStatus(Status.STARTUP);
    }
    
    @Override
    protected Data process(Data input, EventGenerator sender) {
        SilenceStatusData silenceStatus = (SilenceStatusData)input;
        updateStatus(silenceStatus.data);
        return null;
    }

    public void updateStatus(Status status) {
        if(this.currentStatus != status) {
            this.currentStatus = status;
            System.out.println(status);
            if(status == Status.STARTUP) {
                pose.setLED_Sota(Color.GRAY, Color.GRAY, 0, Color.GRAY);
            } else if(status == Status.TALKING) {
                pose.setLED_Sota(Color.GREEN, Color.GREEN, 0, Color.GREEN);
            } else if (status == Status.PAUSED) {
                pose.setLED_Sota(Color.YELLOW, Color.YELLOW, 0, Color.YELLOW);
                if (!this.backchannelled) {
                    CPlayWave.PlayWave("../resources/minecraft-villager-complete-trade.wav");
                    this.backchannelled = true;
                }
            } else if (status == Status.STOPPED) {
                pose.setLED_Sota(Color.RED, Color.RED, 0, Color.RED);
                this.backchannelled = false;
            }
            motion.play(pose, 100);
        }
    }
}