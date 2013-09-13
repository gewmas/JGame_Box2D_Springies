package environment;

import jboxGlue.PhysicalObject;

public class CenterOfMass extends Force {
    private double magnitude;
    private double exponent;
    
    public CenterOfMass(double magnitude, double exponent){
        this.magnitude = magnitude;
        this.exponent = exponent;
    }

    public void setForce(PhysicalObject object){
        
    }

}
