package com.codecentral.sid.shared.command

/**
 * A set of statuses indicating robot command execution states.
 *
 * @see RobotCommand
 */
enum class CommandState {

    /**
     * The command has been received and is queued to be executed.
     */
    RECEIVED,

    /**
     * The command is currently being executed.
     */
    IN_ACTION,

    /**
     * The command has encountered an error during execution.
     */
    ERROR,

    /**
     * The command has successfully finished executing.
     */
    DONE,
}
