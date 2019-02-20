package org.usfirst.frc2619.Resistance2019;

import java.util.ArrayList;

public class VisionData {
    private static final String MSG_SPLIT_CHAR = "/";
    private static final String DATA_SPLIT_CHAR = ",";

    private static final int NUM_DATA_POINTS = 3;

    private ArrayList<Target> targets;

    public VisionData() {
        this.targets = new ArrayList<Target>();
    }

    public VisionData(ArrayList<Target> targets) {
        this.targets = targets;
    }

    public ArrayList<Target> getTargets() {
        return targets;
    }

    /**
     * Check if any vision targets were detected
     */
    public boolean hasTargets() {
        return targets.size() > 0;
    }

    /**
     * Returns the yaw of the target closest to the center
     */
    public double findClosestYaw() {
        if (!hasTargets())
            return 0.0;

        double closestYaw = targets.get(0).getYaw();
        for (int i = 1; i < targets.size()-1; i++) {
            double testYaw = targets.get(i).getYaw();
            if (Math.abs(testYaw) < Math.abs(closestYaw))
                closestYaw = testYaw;
        }

        return closestYaw;
    }

    /**
     * Returns Target object with shortest distance
     */
    public Target findClosestTargetDistance() {
        if (!hasTargets())
            return new Target();

        Target closestTarget = targets.get(0);
        for (int i = 1; i < targets.size()-1; i++) {
            Target testTarget = targets.get(i);
            if (testTarget.getDistance() < closestTarget.getDistance())
                closestTarget = targets.get(i);
        }

        return closestTarget;
    }

    public double findYawOfClosestTarget() {
        if (!hasTargets())
            return 0.0;
        
        Target closestTarget = findClosestTargetDistance();
        double yaw = closestTarget.getYaw();

        return yaw;
    }

    /**
     * Parses a string from the camera to create a VisionData object
     * 
     * @return a new VisionData object
     */
    public static VisionData parseMessage(String msg) {
        msg = msg.trim();
        
        if (msg.equals(""))
            return new VisionData();
        else {
            String[] msgParts = msg.split(DATA_SPLIT_CHAR);

            if (msgParts.length % NUM_DATA_POINTS != 0) {
                // error occurred
                return new VisionData();
            }
            
            ArrayList<Target> targets = new ArrayList<Target>();

            for (int i = 0; i < msgParts.length-1; i += NUM_DATA_POINTS) {
                try {
                    double distance = Double.parseDouble(msgParts[i].trim());
                    double yaw = Double.parseDouble(msgParts[i+1].trim());
                    double rotation = Double.parseDouble(msgParts[i+2].trim());
                    
                    targets.add(new Target(distance, yaw, rotation));
                } catch(Exception e) {
                    // error parsing string to int
                    System.out.println("Error parsing string to double");
                    return new VisionData();
                }
            }

            return new VisionData(targets);
        }
        
    }

    /**
     * A target object to represent the reflective tape coordinates.
     * Instances of this super class represent single pieces of angled tape.
     */
    public static class Target {

        private double distance;    //horizontal distance in inches to center of target
        private double yaw;         //horizontal angle in degrees to center of target
        private double rotation; //angle in degrees the target is rotated

        public Target() {
            distance = 0.0;
            yaw = 0.0;
            rotation = 0.0;
        }
        
        public Target(double distance, double yaw, double targetAngle) {
            this.distance = distance;
            this.yaw = yaw;
            this.rotation = targetAngle;
        }

        public double getDistance() {
            return distance;
        }

        public double getYaw() {
            return yaw;
        }

        public double getRotation() {
            return rotation;
        }

    }
}