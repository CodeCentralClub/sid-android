package com.codecentral.sid.shared.ui.nearby

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProviders
import com.codecentral.sid.shared.R
import com.codecentral.sid.shared.nearby.ConnectionStatus
import kotlinx.android.synthetic.main.activity_nearby_connection.*

/**
 * An Activity that establishes a connection between two endpoints.
 *
 * @see com.codecentral.sid.shared.ui.nearby.ConnectionStatusViewModel
 */
open class NearbyConnectionActivity : AppCompatActivity() {

    protected lateinit var viewModel: ConnectionStatusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_connection)
        viewModel = ViewModelProviders.of(this).get(ConnectionStatusViewModel::class.java)

        viewModel.status.observe(this, Observer {
            updateConnectionStatusText(it)
        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun startDiscovery() {
        viewModel.startDiscovery()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun stopDiscovery() {
        viewModel.stopDiscovery()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun stopAdvertising() {
        viewModel.stopAdvertising()
    }

    private fun finishConnection() {
        val result = if (viewModel.status.value == ConnectionStatus.CONNECTED) {
            Activity.RESULT_OK
        } else {
            Activity.RESULT_CANCELED
        }
        setResult(result)
        finish()
    }

    private fun updateConnectionStatusText(status: ConnectionStatus) {
        val connectionStatus: String = getString(when (status) {
            ConnectionStatus.CONNECTED -> R.string.nearby_status_connected
            ConnectionStatus.CONNECTING -> R.string.nearby_status_connecting
            ConnectionStatus.SEARCHING -> R.string.nearby_status_searching
            ConnectionStatus.NOT_CONNECTED -> R.string.nearby_status_not_connected
        })
        val connectionStatusString = getString(R.string.template_connection_status, connectionStatus)
        connecting_indicator.text = connectionStatusString
    }
}
