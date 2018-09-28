package com.codecentral.sid.shared.nearby;

import androidx.lifecycle.LiveData;
import com.codecentral.sid.shared.command.CommandState;
import com.codecentral.sid.shared.command.RobotCommand;

import java.io.InputStream;
import java.util.List;

/**
 * A client that can advertise and receive connections to and from a robot.
 */
public interface RobotConnectionClient {

    /**
     * Looks for endpoints to connect.
     *
     * @return An updating list of currently available endpoints
     */
    LiveData<List<String>> findEndpoints();

    /**
     * Stops looking for available endpoints.
     * <p>
     * Should be called when a successful connection have been established.
     */
    void stopFindingEndpoints();

    /**
     * Begins advertising availability to other clients.
     */
    void beginAdvertising(String nickname);

    /**
     * Ends advertising availability to other clients.
     */
    void stopAdvertising();

    /**
     * Begins connecting to the given device endpoint.
     *
     * @param endpointId The device to attempt a connection
     * @return A {@link LiveData} object representing the current connection
     * state
     */
    LiveData<ConnectionStatus> connect(String nickname, String endpointId);

    /**
     * Allows another device to establish a connection to this device.
     *
     * @param endpointId The device attempting to start a connection
     */
    void acceptConnection(String endpointId);

    /**
     * Disconnects from the given endpoint.
     *
     * @param endpointId The device to disconnect
     * @return The current status of the connection
     */
    LiveData<ConnectionStatus> disconnect(String endpointId);

    /**
     * Sends a video stream to the currently connected device.
     *
     * @param stream A stream to be viewed on a connected device.
     * @return True if the stream could be sent to the connected device.
     */
    LiveData<Boolean> sendVideo(InputStream stream);

    /**
     * Queues the given command to be executed
     */
    LiveData<CommandState> sendCommand(RobotCommand command);

    /**
     * Returns true if this client is advertising to endpoint devices, false
     * otherwise.
     */
    boolean isAdvertising();

    /**
     * Returns true if this client is looking for endpoint devices, false
     * otherwise.
     */
    boolean isDiscovering();
}
