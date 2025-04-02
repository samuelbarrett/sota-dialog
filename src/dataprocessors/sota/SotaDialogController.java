package dataprocessors.sota;

import dataprocessors.DataProcessor;
import dataprocessors.SilenceDetector.SilenceStatusData;
import dataprocessors.SilenceDetector.Status;
import datatypes.Data;
import eventsystem.EventGenerator;
import tools.ServoRangeTool;

import jp.vstone.RobotLib.*;

import java.awt.Color;

/**
 * A controller for the Sota robot that manages its output for dialog.
 * Interacts with the Sota through the vstone API directly.
 */
public class SotaDialogController extends DataProcessor {
    /**
     * SotaStateData defines the robot's states.
     * LISTENING - the robot is listening for the user's speech
     * SPEAKING - the robot is speaking
     */
    public enum SotaState { LISTENING, BACKCHANNELING }
    public static class SotaStateData extends Data {
        private static final long serialVersionUID = 1L;
        public final SotaState data;
        
        public SotaStateData(SotaState s) {
            this.data = s;
        }

        public String toString() {
            return data.name();
        }
    }
    
    // ------------- SotaDialogController -------------
    static final String TAG = "SotaDialogController";
    private CRobotPose pose;
    private CSotaMotion motion;
    private CRobotMem mem;

    private Status currentStatus = null;
    private SotaState state;
    private long backchannel_finish_time_ms;    // when the robot will finish speaking
    private boolean backchannelled;
    private static final long MIN_BACKCHANNEL_INTERVAL_MS = 1000; // minimum time between backchannels

    // robot motor poses
    private CRobotPose nodNeutral = null;
    private CRobotPose nodDown = null;
    private CRobotPose nodUp = null;

    Byte[] servoIDs = new Byte[] {
        Byte.valueOf(CSotaMotion.SV_BODY_Y),
        Byte.valueOf(CSotaMotion.SV_L_SHOULDER),
        Byte.valueOf(CSotaMotion.SV_L_ELBOW),
        Byte.valueOf(CSotaMotion.SV_R_SHOULDER),
        Byte.valueOf(CSotaMotion.SV_R_ELBOW),
        Byte.valueOf(CSotaMotion.SV_HEAD_Y),
        Byte.valueOf(CSotaMotion.SV_HEAD_P),
        Byte.valueOf(CSotaMotion.SV_HEAD_R),
    };

    public SotaDialogController() {
        this.mem = new CRobotMem();
		this.motion = new CSotaMotion(mem);
		this.pose = new CRobotPose();
        this.backchannel_finish_time_ms = 0;
        this.backchannelled = false;
        this.state = SotaState.LISTENING;

        mem.Connect();
        this.motion.InitRobot_Sota();
        this.motion.ServoOn();
        this.initNodPoses();
        this.update(Status.STARTUP);
    }
    
    @Override
    protected Data process(Data input, EventGenerator sender) {
        SilenceStatusData silenceStatus = (SilenceStatusData)input;
        return update(silenceStatus.data);
    }

    public SotaStateData update(Status status) {
        // manage internal Sota state
        if (this.state == SotaState.BACKCHANNELING) {
            long current_time_ms = System.currentTimeMillis();
            if (current_time_ms > this.backchannel_finish_time_ms) {
                this.state = SotaState.LISTENING;
                this.backchannelled = false;
            }
        }
        dialogStatusUpdate(status);
        return new SotaStateData(this.state);
    }

    // handles Sota behaviour according to the state of the dialog, as dictated by DialogServer
    private void dialogStatusUpdate(Status status) {
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
                    playVerbalBackchannel(MIN_BACKCHANNEL_INTERVAL_MS);
                    playNod(MIN_BACKCHANNEL_INTERVAL_MS);
                }
            } else if (status == Status.STOPPED) {
                pose.setLED_Sota(Color.RED, Color.RED, 0, Color.RED);
            }
            motion.play(pose, 50);
        }
    }

    // plays a backchannel
    private void playVerbalBackchannel(long min_backchannel_interval_ms) {
        long play_time = CPlayWave.getPlayTime("../resources/minecraft-villager-complete-trade.wav");
        long current_time_ms = System.currentTimeMillis();
        CPlayWave.PlayWave("../resources/minecraft-villager-complete-trade.wav");
        this.backchannel_finish_time_ms = current_time_ms + play_time + min_backchannel_interval_ms;
        this.backchannelled = true;
        this.state = SotaState.BACKCHANNELING;
    }

    // Adjust head pitch to make Sota nod using the nod poses
    private void playNod(long min_backchannel_interval_ms) {
        long play_time = 1000;
        this.backchannel_finish_time_ms = System.currentTimeMillis() + play_time + min_backchannel_interval_ms;
        this.backchannelled = true;
        this.state = SotaState.BACKCHANNELING;

        this.motion.play(nodDown, 200);
        this.motion.waitEndinterpAll();

        // this.motion.play(nodUp, 400);
        // this.motion.waitEndinterpAll();

        this.motion.play(nodNeutral, 250);
        this.motion.waitEndinterpAll();
    }

    /**
     * Initializes the motor poses for head nodding (head pitch motor positions).
     * Ensure the robot starts in a neutral position with its head facing forward.
     */
    private void initNodPoses() {
        ServoRangeTool ranges = ServoRangeTool.Load("../resources/servo/head_nod_motor_positions");
        CRobotPose minPose = ranges.getMinPose();
        CRobotPose maxPose = ranges.getMaxPose();
        CRobotPose midPose = ranges.getMidPose();

        this.nodNeutral = this.motion.getReadPose();
        this.nodDown = this.motion.getReadPose();
        this.nodUp = this.motion.getReadPose();
        
        // extract just the head pitch motor position from saved ServoRangeTool obj
        // and don't change the rest of the pose
        Short minHeadPitch = minPose.getServoAngle(Byte.valueOf(CSotaMotion.SV_HEAD_P));
        Short maxHeadPitch = maxPose.getServoAngle(Byte.valueOf(CSotaMotion.SV_HEAD_P));
        Short midHeadPitch = midPose.getServoAngle(Byte.valueOf(CSotaMotion.SV_HEAD_P));

        this.nodUp.addServoAngle(Byte.valueOf(CSotaMotion.SV_HEAD_P), minHeadPitch);
        this.nodNeutral.addServoAngle(Byte.valueOf(CSotaMotion.SV_HEAD_P), midHeadPitch);
        this.nodDown.addServoAngle(Byte.valueOf(CSotaMotion.SV_HEAD_P), maxHeadPitch);
    }
}