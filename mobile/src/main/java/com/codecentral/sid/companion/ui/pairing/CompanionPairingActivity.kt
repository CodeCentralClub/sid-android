package com.codecentral.sid.companion.ui.pairing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.codecentral.sid.companion.R
import com.codecentral.sid.shared.ui.nearby.adapter.EndpointRecyclerAdapter
import kotlinx.android.synthetic.main.companion_pairing_activity.*

class CompanionPairingActivity : AppCompatActivity() {

    private lateinit var viewModel: CompanionPairingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.companion_pairing_activity)

        viewModel = ViewModelProviders.of(this).get(CompanionPairingViewModel::class.java)

        viewModel.status.observe(this, Observer {

        })

        val adapter = EndpointRecyclerAdapter()
        endpoint_list.adapter = adapter
        viewModel.endpoints.observe(this, Observer { newEndpoints ->
            adapter.updateItems(newEndpoints)
        })
        viewModel.startDiscovery()
    }


}