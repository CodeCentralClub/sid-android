package com.codecentral.sid.shared;

import android.app.Application;
import com.codecentral.sid.shared.nearby.NearbyRobotConnectionClient;
import com.codecentral.sid.shared.nearby.RobotConnectionClient;

/**
 * A common application class that initializes a companion app connection.
 */
public abstract class SidBaseApplication extends Application {

    private RobotConnectionClient connectionClient;

    @Override
    public void onCreate() {
        super.onCreate();
        connectionClient = createConnectionClient();
    }

    /**
     * Must be overridden in subclass to provide functionality.
     *
     * @return A RobotConnectionClient instance
     * @see NearbyRobotConnectionClient
     */
    public abstract RobotConnectionClient createConnectionClient();

    /**
     * Must be overridden in subclass to provide functionality
     *
     * @return A unique client name for the type of device, like "Sid Companion
     * App"
     */
    public abstract String getClientNickname();

    /**
     * Provides a {@link NearbyRobotConnectionClient} used to connect a robot to a
     * companion app.
     * <p>
     * TODO(di): Use dependency injection to initialize this automatically
     *
     * @return This application's {@link NearbyRobotConnectionClient} instance
     */
    public RobotConnectionClient getConnectionClient() {
        return connectionClient;
    }
}
