package com.codecentral.sid.robot.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.codecentral.sid.robot.SidRobotApplication
import com.codecentral.sid.shared.nearby.ConnectionStatus

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val connectionState: LiveData<ConnectionStatus> =
            (application as SidRobotApplication).connectionClient.observeConnectionStatus()

    // TODO(console): Set up camera, other peripheral displays
}