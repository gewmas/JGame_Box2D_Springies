package environment;

import jboxGlue.PhysicalObject;
import springies.Common;


/**
 * Subclass of Force that applies a force to mass that is inversely proportional to normal distance
 * between
 * wall and mass raised to exponent
 * 
 * @author Yuhua Mai, Susan Zhang
 */
public abstract class WallRepulsion extends Force {
    // private String wallId;
    private double magnitude;
    private double exponent;
    protected double wallThickness;

    /**
     * Constructor for WallRepulsion force
     * 
     * @param wallId ID of Wall
     * @param magnitude magnitude of wall repulsion force
     * @param exponent the exponent that the force will be inversely proportional to
     */
    public WallRepulsion (String wallId, double magnitude, double exponent) {
        // this.wallId = wallId;
        this.magnitude = magnitude;
        this.exponent = exponent;
        wallThickness = Common.WALL_THICKNESS;
    }

    /**
     * Changes the thickness of wall
     * 
     * @param increment how many pixels the walls are
     */
    public void incrementWallThickness (double increment) {
        wallThickness += increment;
    }

    /**
     * Method that returns the force on an object based on the magnitude and exponent
     * 
     * @param distance distance between object and wall
     * @return the magnitude of the resultant force
     */
    public double calculateForce (double distance) {
        return magnitude / Math.pow(distance, exponent);
    }

    /**
     * Calculates normal distance between wall and PhysicalObject
     * 
     * @param object Physical Object that is to be affected by wall repulsion force
     * @return normal distance between wall and object
     */
    public abstract double calculateDistance (PhysicalObject object);

}
