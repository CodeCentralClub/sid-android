package com.codecentral.sid.robot;

import com.codecentral.sid.shared.SidBaseApplication;
import com.codecentral.sid.shared.nearby.NearbyRobotConnectionClient;
import com.codecentral.sid.shared.nearby.RobotConnectionClient;

/**
 * The application containing resources only the robot uses.
 */
public class SidRobotApplication extends SidBaseApplication {

    @Override
    public RobotConnectionClient createConnectionClient() {
        return new NearbyRobotConnectionClient(this);
    }

    @Override
    public String getClientNickname() {
        return BuildConfig.DEVICE_NICKNAME;
    }

    // TODO(peripherals): Initialize PWMs, cameras, ultrasonic sensor, and controller accecss
}
