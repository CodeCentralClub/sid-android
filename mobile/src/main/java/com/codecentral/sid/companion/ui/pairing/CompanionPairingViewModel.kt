package com.codecentral.sid.companion.ui.pairing

import android.app.Application
import com.codecentral.sid.shared.ui.nearby.PairingViewModel

class CompanionPairingViewModel(application: Application) : PairingViewModel(application) {

    fun startDiscovery() {
        connectionClient.findEndpoints()
    }

    fun chooseEndpoint(endpointId: String) {
        connectionClient.acceptConnection(endpointId)
    }
}