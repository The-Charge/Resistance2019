package org.usfirst.frc2619.Resistance2019;

import edu.wpi.first.wpilibj.SerialPort;
import org.usfirst.frc2619.Resistance2019.VisionData;
import org.usfirst.frc2619.Resistance2019.VisionData.Target;

public class VisionUtil {
    
    // SerialPort constants
    public static final int DEFAULT_BAUD_RATE = 115200;
    public static final SerialPort.Port DEFAULT_SERIAL_PORT = SerialPort.Port.kUSB1;

    // Camera constants
    public static final int CAM_WIDTH = 320;
    public static final int CAM_HEIGHT = 240;
    public static final int CAM_FPS = 60;

    // Calculation constats
    public static final int CENTER_X = (int)(CAM_WIDTH / 2);
    public static final int CENTER_Y = (int)(CAM_HEIGHT / 2);

    public static final double FOV_H = Math.toRadians(65);
    public static final double FOV_V = Math.toRadians(49.75);

    public static final double FOCAL_LENGTH_H = CAM_WIDTH / (2 * Math.tan(FOV_H / 2));  // ~251.1496923 (px)
    public static final double FOCAL_LENGTH_V = CAM_HEIGHT / (2 * Math.tan(FOV_V / 2)); // ~258.8135151 (px)

    public static final double PORT_TARGET_HEIGHT_IN = 37.0;
    public static final double HATCH_TARGET_HEIGHT_IN = 28.5;

    /**
     * Creates a SerialPort object with the default Baud Rate and Port
     * 
     * @return a SerialPort object
     */
    public static SerialPort createSerialPort(int baudRate, SerialPort.Port port) {
        SerialPort serialPort;

        try {
            System.out.println("Creating serial port...");
            serialPort = new SerialPort(DEFAULT_BAUD_RATE, DEFAULT_SERIAL_PORT);
            System.out.println("Successfully created serial port.");
        } catch (Exception e) {
            System.out.println("Failed to create serial port.");
            serialPort = new SerialPort(DEFAULT_BAUD_RATE, DEFAULT_SERIAL_PORT);
        }

        return serialPort;
    }

    /**
     * Creates a SerialPort object with the given Baud Rate and Port parameters
     * 
     * @return a SerialPort object
     */
    public static SerialPort createSerialPort() {
        return createSerialPort(DEFAULT_BAUD_RATE, DEFAULT_SERIAL_PORT);
    }

    /**
     * Writes a value to a SerialPort and checks if it wrote successfully
     */
    public static void pingSerialPort(SerialPort serialPort) {
        String msg = "ping";
        int bytes = 0;

        System.out.println("Pinging Serial Port...");

        if (serialPort == null) {
            System.out.println("Serial Port not valid");
        }
        else {
            bytes = serialPort.writeString(msg);
            System.out.println("Wrote " +  bytes + "/" + msg.length() + " bytes, cmd: " + msg);
        }
    }
    
    /**
     * Reads a string from a Serial Port
     * 
     * @return a String sent throught the Serial Port
     */
    public static String readSerialPort(SerialPort serialPort) {
        try {
            return serialPort.readString();
        }
        catch (Exception e) {
            System.out.println("Unable to read from Serial Port - port is not valid");
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Reads in a string from a serial port, parses it, and returns the processed data
     */
    public static VisionData getSerialInfo(SerialPort serialPort) {
        return parseMessage(readSerialPort(serialPort));
    }

    /**
     * Reads a string and creates a packet of data
     * 
     * @return a VisionData object
     */
    public static VisionData parseMessage(String msg) {
        return VisionData.parseMessage(msg);
    }

    
}