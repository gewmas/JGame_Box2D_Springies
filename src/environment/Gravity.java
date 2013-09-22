package environment;

import jboxGlue.PhysicalObject;

/**
 * Class for Gravity force
 * 
 * @author Yuhua Mai, Susan Zhang
 *
 */
public class Gravity extends Force {
    private double direction;
    private double magnitude;
        
    public Gravity(double direction, double magnitude){
        this.direction = direction;
        this.magnitude = magnitude;
    }
    
    /**
     * Breaks down force into x and y components and applies force to a single PhysicalObject
     */
    public void setForce(PhysicalObject object){
        object.setForce(magnitude*object.getMass()*Math.cos(direction/180*Math.PI), magnitude*object.getMass()*Math.sin(direction/180*Math.PI));
    
        System.out.println("Calling Gravity setForce!");
    }

}
