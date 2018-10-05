package com.codecentral.sid.robot

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.codecentral.sid.robot.ui.RobotPairingActivity
import com.codecentral.sid.robot.ui.home.HomeViewModel
import com.codecentral.sid.shared.nearby.ConnectionStatus
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    companion object {
        const val RC_PAIR = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        @StringRes
        val statusText: Int = when (viewModel.connectionState.value ?: return) {
            ConnectionStatus.NOT_CONNECTED -> {
                start_connection_button.isEnabled = true
                start_connection_button.setOnClickListener { startPairing() }
                R.string.nearby_status_not_connected
            }
            ConnectionStatus.CONNECTED -> {
                start_connection_button.isEnabled = false
                start_connection_button.setOnClickListener(null)
                R.string.nearby_status_connected
            }
            ConnectionStatus.SEARCHING -> {
                R.string.nearby_status_searching
            }
            ConnectionStatus.CONNECTING -> {
                R.string.nearby_status_connecting
            }
            else -> {
                throw IllegalStateException("Unknown connection status")
            }
        }
        connection_status.text = getString(statusText)
    }

    private fun startPairing() {
        val intent = Intent(this, RobotPairingActivity::class.java)
        startActivityForResult(intent, RC_PAIR)
    }
}
