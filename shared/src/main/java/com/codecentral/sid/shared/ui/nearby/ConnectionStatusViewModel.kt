package com.codecentral.sid.shared.ui.nearby

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codecentral.sid.shared.BuildConfig
import com.codecentral.sid.shared.SidBaseApplication
import com.codecentral.sid.shared.nearby.ConnectionStatus
import com.codecentral.sid.shared.nearby.NearbyRobotConnectionClient
import com.codecentral.sid.shared.nearby.RobotConnectionClient

/**
 * A [ViewModel] for managing Nearby connection state.
 *
 * @see NearbyConnectionActivity
 */
class ConnectionStatusViewModel(application: Application) : AndroidViewModel(application) {

    private val connectionClient: RobotConnectionClient =
            (application as SidBaseApplication).connectionClient

    private val _status = MutableLiveData<ConnectionStatus>()

    val status: LiveData<ConnectionStatus>
        get() = _status

    fun startDiscovery() {
        connectionClient.beginAdvertising(BuildConfig.DEVICE_NICKNAME)
    }

    fun startConnection() {
        connectionClient.connect(BuildConfig.DEVICE_NICKNAME, NearbyRobotConnectionClient.SERVICE_ID)
    }
}