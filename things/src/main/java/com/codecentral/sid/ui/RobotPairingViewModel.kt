package com.codecentral.sid.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codecentral.sid.shared.ui.nearby.PairingViewModel

/**
 * A view model that allows for advertising and accepting of a connection.
 */
class RobotPairingViewModel(application: Application) : PairingViewModel(application) {

    private val _endpointId = MutableLiveData<String>()

    val selectedEndpoint: LiveData<String>
        get() = _endpointId

    fun startAdvertising() {
        connectionClient.beginAdvertising(nickname)
    }

    fun finishConnection(endpointId: String) {
        connectionClient.acceptConnection(endpointId)
    }

    fun updateSelectedEndpoint(endpoint: String) {
        _endpointId.value = endpoint;
    }
}