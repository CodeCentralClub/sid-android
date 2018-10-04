package com.codecentral.sid.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.codecentral.sid.R
import com.codecentral.sid.shared.ui.nearby.adapter.EndpointRecyclerAdapter
import com.codecentral.sid.shared.ui.nearby.adapter.EndpointSelectionListener
import kotlinx.android.synthetic.main.activity_robot_pairing.*

/**
 * An activity used to pair this robot to a discovering companion app.
 */
class RobotPairingActivity : AppCompatActivity() {

    private lateinit var viewModel: RobotPairingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_pairing)
        viewModel = ViewModelProviders.of(this).get(RobotPairingViewModel::class.java)

        viewModel.startAdvertising()
        val adapter = EndpointRecyclerAdapter(object : EndpointSelectionListener {
            override fun onEndpointSelected(endpointId: String) {
                viewModel.updateSelectedEndpoint(endpointId)
            }
        })
        viewModel.endpoints.observe(this, Observer { newEndpoints ->
            adapter.updateItems(newEndpoints)
        })
        viewModel.selectedEndpoint.observe(this, Observer {
            button_accept_connection.isEnabled = it.isNotEmpty()
        })
        button_accept_connection.setOnClickListener {
            viewModel.finishConnection(viewModel.selectedEndpoint.value ?: return@setOnClickListener)
            onConnected(viewModel.selectedEndpoint.value ?: return@setOnClickListener)
        }
        endpoint_list.adapter = adapter
    }

    private fun onConnected(endpointId: String) {
        setResult(RESULT_OK, Intent().putExtra("endpoint_id", endpointId))
        finish()
    }
}