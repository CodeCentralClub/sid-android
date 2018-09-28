package com.codecentral.sid.shared.ui.nearby

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val nickname = (application as SidBaseApplication).clientNickname

    private val connectionClient: RobotConnectionClient =
            (application as SidBaseApplication).connectionClient

    private val _status = MutableLiveData<ConnectionStatus>()

    val status: LiveData<ConnectionStatus>
        get() = _status

    private val _endpoints = MutableLiveData<List<String>>()

    val endpoints: LiveData<List<String>>
        get() = _endpoints

    fun startDiscovery() {
        connectionClient.findEndpoints()
    }

    fun stopDiscovery() {
        connectionClient.stopFindingEndpoints()
    }

    fun startConnection() {
        connectionClient.connect(nickname, NearbyRobotConnectionClient.SERVICE_ID)
    }

    fun startAdvertising() {
        connectionClient.beginAdvertising(nickname)
    }

    fun stopAdvertising() {
        connectionClient.stopAdvertising()
    }

}