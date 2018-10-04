package com.codecentral.sid.companion.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.codecentral.sid.companion.SidCompanionApplication
import com.codecentral.sid.shared.nearby.ConnectionStatus

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    val connectionState: LiveData<ConnectionStatus> =
            (application as SidCompanionApplication).connectionClient.observeConnectionStatus()

}
