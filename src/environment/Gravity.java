package environment;

import jboxGlue.PhysicalObject;


/**
 * Class for Gravity force, which exerts a force on the object proportional to
 * the mass of the object. The gravity force is a vector that is defined by a magnitude
 * and direction (given by an angle). The x and y components of the vector are calculated
 * to apply forces in the x and y direction to masses.
 * 
 * @author Yuhua Mai, Susan Zhang
 * 
 */
public class Gravity extends Force {
    private double direction;
    private double magnitude;

    /**
     * Constructor method for Gravity class
     * 
     * @param direction direction of gravity force (degrees clockwise from positive x axis)
     * @param magnitude magnitude of gravity force (pixels/s)
     */
    public Gravity (double direction, double magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
    }

    /**
     * Breaks down force into x and y components and applies force to a single PhysicalObject
     */
    @Override
    public void setForce (PhysicalObject object) {
        object.setForce(magnitude * object.getMass() * Math.cos(direction / 180 * Math.PI),
                        magnitude * object.getMass() * Math.sin(direction / 180 * Math.PI));

        // System.out.println("Calling Gravity setForce!");
    }

}
