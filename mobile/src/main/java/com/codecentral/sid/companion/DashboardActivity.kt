package com.codecentral.sid.companion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codecentral.sid.R
import com.codecentral.sid.companion.ui.dashboard.DashboardFragment

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DashboardFragment.newInstance())
                .commitNow()
        }
    }

}
