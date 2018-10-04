package com.codecentral.sid.companion

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.codecentral.sid.companion.ui.dashboard.DashboardFragment
import com.codecentral.sid.companion.ui.dashboard.DashboardViewModel
import com.codecentral.sid.shared.nearby.ConnectionStatus
import kotlinx.android.synthetic.main.dashboard_fragment.*

class DashboardActivity : AppCompatActivity() {

    companion object {
        const val RC_PAIR_DEVICE = 100
    }

    private lateinit var viewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DashboardFragment.newInstance())
                    .commitNow()
            viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        }
        viewModel.connectionState.observe(this, Observer { status ->
            if (status != ConnectionStatus.CONNECTED) {
                start_pairing_button.apply {
                    visibility = View.VISIBLE
                    isEnabled = true
                    setOnClickListener {
                        Intent().apply {
                            action = "com.codecentral.sid.action.START_PAIRING"
                            startActivityForResult(this, RC_PAIR_DEVICE)
                        }
                    }
                }
            } else {
                start_pairing_button.apply {
                    visibility = View.VISIBLE
                    isEnabled = false
                    setOnClickListener(null)
                }
            }
        })
    }
}
