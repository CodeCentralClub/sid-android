package com.codecentral.sid.shared.ui.nearby

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.codecentral.sid.shared.R
import com.codecentral.sid.shared.nearby.ConnectionStatus

/**
 * An Activity that establishes a connection between two endpoints.
 *
 * @see com.codecentral.sid.shared.ui.nearby.PairingViewModel
 */
open class PairingActivity : AppCompatActivity() {

    protected lateinit var viewModel: PairingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_connection)
        viewModel = ViewModelProviders.of(this).get(PairingViewModel::class.java)
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
