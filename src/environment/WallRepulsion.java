package environment;

import springies.Common;
import jboxGlue.PhysicalObject;

public abstract class WallRepulsion extends Force {
    private String wallId;
    private double magnitude;
    private double exponent;
    
    public WallRepulsion(String wallId, double magnitude, double exponent){
        this.wallId = wallId;
        this.magnitude = magnitude;
        this.exponent = exponent;
    }
    
    public double calculateForce(double distance){
        return magnitude/Math.pow(distance, exponent);
    }
    
 

}
