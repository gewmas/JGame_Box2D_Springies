package environment;

import jboxGlue.PhysicalObject;

public class WallRepulsion extends Force {
    private String wallId;
    private double magnitude;
    private double exponent;
    
    public WallRepulsion(String wallId, double magnitude, double exponent){
        this.wallId = wallId;
        this.magnitude = magnitude;
        this.exponent = exponent;
    }
    public void setForce(PhysicalObject object){
        
    }

}
