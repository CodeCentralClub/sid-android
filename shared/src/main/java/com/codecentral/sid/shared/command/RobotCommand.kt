package com.codecentral.sid.shared.command

/**
 * A set of instructions to be run on the robot.
 *
 * TODO(command): Create a system for running remote commands
 */
data class RobotCommand(val instruction: String) {
    fun toBytes(): ByteArray = instruction.split("//s+")
            .map { it.toByte() }
            .toByteArray() // Needed for Java compatibility
}