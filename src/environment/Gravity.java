package environment;

import jboxGlue.PhysicalObject;

public class Gravity extends Force {
    private double direction;
    private double magnitude;
        
    public Gravity(double direction, double magnitude){
        this.direction = direction;
        this.magnitude = magnitude;
    }
    
    public void setForce(PhysicalObject object){
        object.setForce(magnitude*object.getMass()*Math.cos(direction/180*Math.PI), magnitude*object.getMass()*Math.sin(direction/180*Math.PI));
    }

}
