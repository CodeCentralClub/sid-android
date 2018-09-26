package com.codecentral.sid.shared;

import android.app.Application;
import com.codecentral.sid.shared.nearby.NearbyRobotConnectionClient;

/**
 * A common application class that initializes Nearby for the robot and
 * companion app.
 */
public class SidBaseApplication extends Application {

    private NearbyRobotConnectionClient connectionClient;

    @Override
    public void onCreate() {
        super.onCreate();
        connectionClient = new NearbyRobotConnectionClient(this);
    }

    /**
     * Provides a {@link NearbyRobotConnectionClient} used to connect a robot to a
     * companion app.
     *
     * TODO(di): Use dependency injection to initialize this automatically
     *
     * @return This application's {@link NearbyRobotConnectionClient} instance
     */
    public NearbyRobotConnectionClient getConnectionClient() {
        return connectionClient;
    }
}
