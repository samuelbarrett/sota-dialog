package dataprocessors;

import dataprocessors.SilenceDetector.SilenceStatusData;
import dataprocessors.SilenceDetector.Status;
import datatypes.Data;
import eventsystem.EventGenerator;
import jp.vstone.RobotLib.CSotaMotion;
import jp.vstone.RobotLib.CRobotMem;
import jp.vstone.RobotLib.CRobotPose;

import java.awt.Color;

public class SotaOutputController extends DataProcessor {

    private CRobotPose pose;
    private CSotaMotion motion;

    private Status currentStatus = null;

    public SotaOutputController() {
        CRobotMem mem = new CRobotMem();
		this.motion = new CSotaMotion(mem);
		this.pose = new CRobotPose();

        mem.Connect();
        this.motion.InitRobot_Sota();
        this.motion.ServoOn();
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
            if(status == Status.STARTUP) {
                pose.setLED_Sota(Color.GRAY, Color.GRAY, 0, Color.GRAY);
            } else if(status == Status.TALKING) {
                pose.setLED_Sota(Color.GREEN, Color.GREEN, 0, Color.GREEN);
            } else if (status == Status.PAUSED) {
                pose.setLED_Sota(Color.YELLOW, Color.YELLOW, 0, Color.YELLOW);
            } else if (status == Status.STOPPED) {
                pose.setLED_Sota(Color.RED, Color.RED, 0, Color.RED);
            }
            motion.play(pose, 250);
        }
    }
}
