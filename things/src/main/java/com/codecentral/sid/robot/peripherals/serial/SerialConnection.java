package com.codecentral.sid.robot.peripherals.serial;

import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.UartDevice;

import java.io.IOException;
import java.util.List;

/**
 * A manager for serial connections with a UART-enabled device.
 */
public class SerialConnection {

    private static final String UART_DEVICE_NAME = "UART0";

    private UartDevice uartDevice;

    private PeripheralManager manager;

    public SerialConnection() {
        manager = PeripheralManager.getInstance();
    }

    public void getDevices() {
        List<String> devices = manager.getUartDeviceList();
        if (devices.isEmpty()) {
            // TODO(logging): List warning that no devices are detected
        } else {
            // TODO(logging): List available devices
        }
    }

    public void configureUartFrame(UartDevice uart) throws IOException {
        // Configure the UART port
        uart.setBaudrate(115200);
        uart.setDataSize(8);
        uart.setParity(UartDevice.PARITY_NONE);
        uart.setStopBits(1);
    }

    public void start() throws IOException {
        uartDevice = manager.openUartDevice(UART_DEVICE_NAME);
    }

    /**
     *
     * @param data
     * @return The size of data
     * @throws IOException
     */
    public int writeData(byte[] data) throws IOException {
        return uartDevice.write(data, data.length);
    }

    /**
     * Stops the currently connected
     */
    public void closeConnection() {
        try {
            uartDevice.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            uartDevice = null;
        }
    }
}
