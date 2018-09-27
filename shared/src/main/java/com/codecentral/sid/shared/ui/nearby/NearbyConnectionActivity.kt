package com.codecentral.sid.shared.ui.nearby

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.codecentral.sid.shared.R
import com.codecentral.sid.shared.nearby.ConnectionStatus
import kotlinx.android.synthetic.main.activity_nearby_connection.*

/**
 * An Activity that establishes a connection between two endpoints.
 *
 * @see com.codecentral.sid.shared.ui.nearby.ConnectionStatusViewModel
 */
class NearbyConnectionActivity : AppCompatActivity() {

    private lateinit var viewModel: ConnectionStatusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_connection)
        viewModel = ViewModelProviders.of(this).get(ConnectionStatusViewModel::class.java)

        viewModel.status.observe(this, Observer {
            val connectionStatus: String = getString(if (it != null) {
                when (it) {
                    ConnectionStatus.CONNECTED -> R.string.nearby_status_connected
                    ConnectionStatus.CONNECTING -> R.string.nearby_status_connecting
                    ConnectionStatus.SEARCHING -> R.string.nearby_status_searching
                    ConnectionStatus.NOT_CONNECTED -> R.string.nearby_status_not_connected
                }
            } else {
                R.string.nearby_status_not_connected
            })
            val connectionStatusString = getString(R.string.template_connection_status, connectionStatus)
            connecting_indicator.text = connectionStatusString
        })

        startDiscovery()
    }

    private fun startDiscovery() {
        viewModel.startDiscovery()
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
}
