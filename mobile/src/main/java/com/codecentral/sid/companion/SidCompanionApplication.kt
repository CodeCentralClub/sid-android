package com.codecentral.sid.companion

import com.codecentral.sid.shared.SidBaseApplication
import com.codecentral.sid.shared.nearby.NearbyRobotConnectionClient

/**
 * The application containing resources the companion app uses.
 */
class SidCompanionApplication: SidBaseApplication() {

    override fun createConnectionClient() = NearbyRobotConnectionClient(this)

    override fun getClientNickname(): String = BuildConfig.DEVICE_NICKNAME
}