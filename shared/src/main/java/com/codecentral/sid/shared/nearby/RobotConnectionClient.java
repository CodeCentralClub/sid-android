package com.codecentral.sid.shared.nearby;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.*;

public class RobotConnectionClient {

    private static final String SERVICE_NICKNAME = "Sid the Robot";
    private final ConnectionsClient client;
    private ConnectionLifecycleCallback callback;

    public RobotConnectionClient(Context context) {
        client = Nearby.getConnectionsClient(context);
        callback = new ConnectionLifecycleCallback() {
            @Override
            public void onConnectionInitiated(@NonNull String s, @NonNull ConnectionInfo connectionInfo) {

            }

            @Override
            public void onConnectionResult(@NonNull String s, @NonNull ConnectionResolution connectionResolution) {

            }

            @Override
            public void onDisconnected(@NonNull String s) {

            }
        };
    }

    public void startAdvertising(String nickname) {
        client.startAdvertising(nickname, SERVICE_NICKNAME, callback, new AdvertisingOptions.Builder()
                .setStrategy(Strategy.P2P_POINT_TO_POINT)
                .build());
    }
}
