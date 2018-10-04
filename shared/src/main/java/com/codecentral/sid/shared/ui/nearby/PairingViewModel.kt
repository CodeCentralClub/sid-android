package com.codecentral.sid.shared.ui.nearby

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codecentral.sid.shared.SidBaseApplication
import com.codecentral.sid.shared.nearby.ConnectionStatus
import com.codecentral.sid.shared.nearby.RobotConnectionClient

/**
 * A [ViewModel] for managing Nearby connection state.
 * @see RobotConnectionClient
 */
open class PairingViewModel(application: Application) : AndroidViewModel(application) {

    protected val nickname = (application as SidBaseApplication).clientNickname

    protected val connectionClient: RobotConnectionClient =
            (application as SidBaseApplication).connectionClient

    protected val _status = MutableLiveData<ConnectionStatus>()

    val status: LiveData<ConnectionStatus>
        get() = _status

    protected val _endpoints = MutableLiveData<List<String>>()

    val endpoints: LiveData<List<String>>
        get() = _endpoints
}