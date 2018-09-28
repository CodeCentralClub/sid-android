package com.codecentral.sid.shared.nearby;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.codecentral.sid.shared.command.CommandState;
import com.codecentral.sid.shared.command.RobotCommand;
import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * A {@link RobotConnectionClient} that uses {@link Nearby} Connections to
 * communicate between devices.
 */
public class NearbyRobotConnectionClient implements RobotConnectionClient {

    public static final String SERVICE_ID = "com.codecentral.sid";

    private final Executor executor;

    private final ConnectionsClient client;

    private ConnectionLifecycleCallback callback;

    private EndpointDiscoveryCallback discoveryCallback;

    private PayloadCallback payloadCallback;

    private MutableLiveData<ConnectionStatus> status = new MutableLiveData<>();

    private MutableLiveData<List<String>> endpoints = new MutableLiveData<>();

    private String connectedEndpoint;

    private boolean isAdvertising = false;

    private boolean isDiscovering = false;

    /**
     * @param context The application context
     * @see NearbyRobotConnectionClient(Context, Executor)
     */
    public NearbyRobotConnectionClient(Context context) {
        this(context, new HandlerExecutor(new Handler(context.getMainLooper())));
    }

    /**
     * Creates a new NearbyRobotConnectionClient that updates values with the
     * given executor
     *
     * @param context An application context
     * @param executor The executor to run updates on
     */
    public NearbyRobotConnectionClient(Context context, Executor executor) {
        this.executor = executor;
        client = Nearby.getConnectionsClient(context);
        callback = new ConnectionLifecycleCallback() {
            @Override
            public void onConnectionInitiated(@NonNull String endpointId,
                                              @NonNull ConnectionInfo connectionInfo) {
                // TODO(logging): Notify connection state
            }

            @Override
            public void onConnectionResult(@NonNull String endpointId,
                                           @NonNull ConnectionResolution result) {
                switch (result.getStatus().getStatusCode()) {
                    case ConnectionsStatusCodes.STATUS_OK:
                        status.postValue(ConnectionStatus.CONNECTED);
                        connectedEndpoint = endpointId;
                        // TODO(logging): Log success
                        break;
                    case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                        status.postValue(ConnectionStatus.NOT_CONNECTED);
                        // TODO(logging): Log warning for rejected connection
                        break;
                    case ConnectionsStatusCodes.STATUS_ERROR:
                        status.postValue(ConnectionStatus.NOT_CONNECTED);
                        // TODO(logging): Log connection error
                        break;
                }
            }

            @Override
            public void onDisconnected(@NonNull String endpointId) {
                // TODO(logging): Log disconnect
                status.postValue(ConnectionStatus.NOT_CONNECTED);
            }
        };

        discoveryCallback = new EndpointDiscoveryCallback() {
            @Override
            public void onEndpointFound(@NonNull String endpointId,
                                        @NonNull DiscoveredEndpointInfo discoveredEndpointInfo) {
                addEndpoint(endpointId);
//                acceptConnection(endpointId);
            }

            @Override
            public void onEndpointLost(@NonNull String endpointId) {
                removeEndpoint(endpointId);
            }
        };

        payloadCallback = new PayloadCallback() {
            @Override
            public void onPayloadReceived(@NonNull String endpointId, @NonNull Payload payload) {
                // TODO(logging): Log received data
            }

            @Override
            public void onPayloadTransferUpdate(@NonNull String endpointId,
                                                @NonNull PayloadTransferUpdate payloadTransferUpdate) {

            }
        };
    }

    @Override
    public LiveData<List<String>> findEndpoints() {
        isDiscovering = true;
        client.startDiscovery(SERVICE_ID, discoveryCallback, new DiscoveryOptions.Builder()
                .setStrategy(Strategy.P2P_POINT_TO_POINT)
                .build())
                .addOnSuccessListener(executor, success -> {
                    // TODO(logging): Log success
                })
                .addOnFailureListener(executor, exception -> {
                    // TODO(logging): Log failure
                });
        // Adding endpoints to LiveData is handled in callbacks
        return endpoints;
    }

    @Override
    public void stopFindingEndpoints() {
        client.stopDiscovery();
        isDiscovering = false;
    }

    @Override
    public void beginAdvertising(String nickname) {
        client.startAdvertising(nickname, SERVICE_ID, callback, new AdvertisingOptions.Builder()
                .setStrategy(Strategy.P2P_POINT_TO_POINT)
                .build())
                .addOnSuccessListener(executor, unusedResult -> {
                    // TODO(logging): Log success
                })
                .addOnFailureListener(executor, exception -> {
                    // TODO(logging): Log failures
                });
        isAdvertising = true;
    }

    @Override
    public void stopAdvertising() {
        client.stopAdvertising();
        isAdvertising = false;
    }

    @Override
    public LiveData<ConnectionStatus> connect(String nickname, String endpointId) {
        client.requestConnection(nickname, endpointId, callback)
                .addOnSuccessListener(executor, unusedResult -> {
                    // TODO(logging): Log success
                })
                .addOnFailureListener(executor, exception -> {
                    // TODO(logging): Log failures
                });
        // Status is updated in callbacks
        return status;
    }

    @Override
    public void acceptConnection(String endpointId) {
        client.acceptConnection(endpointId, payloadCallback)
                .addOnSuccessListener(executor, unusedResult -> {
                    // TODO(logging): Log success
                })
                .addOnFailureListener(executor, exception -> {
                    // TODO(logging): Log failures
                });
    }

    @Override
    public LiveData<ConnectionStatus> disconnect(String endpointId) {
        executor.execute(() -> {
            client.disconnectFromEndpoint(endpointId);
        });
        return null;
    }

    @Override
    public LiveData<Boolean> sendVideo(InputStream stream) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();
        client.sendPayload(connectedEndpoint, Payload.fromStream(stream))
                .addOnSuccessListener(executor, success -> {
                    // TODO(logging):
                    data.postValue(true);
                })
                .addOnFailureListener(executor, exception -> {
                    // TODO(logging):
                    data.postValue(false);
                });
        return data;
    }

    @Override
    public LiveData<CommandState> sendCommand(RobotCommand command) {
        MutableLiveData<CommandState> data = new MutableLiveData<>();
        client.sendPayload(connectedEndpoint, Payload.fromBytes(command.toBytes()))
                .addOnSuccessListener(executor, success -> {
                    // TODO(logging):
//                    data.postValue(true);
                })
                .addOnFailureListener(executor, exception -> {
                    // TODO(logging):
//                    data.postValue(false);
                });
        return data;
    }

    @Override
    public boolean isAdvertising() {
        return isAdvertising;
    }

    @Override
    public boolean isDiscovering() {
        return isDiscovering;
    }

    private void removeEndpoint(String endpointId) {
        if (endpoints.getValue() == null) {
            return;
        }
        endpoints.getValue().remove(endpointId);
    }

    private void addEndpoint(String endpointId) {
        if (endpoints.getValue() == null) {
            endpoints.setValue(new ArrayList<>());
        }
        endpoints.getValue().add(endpointId);
    }
}
