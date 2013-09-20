package environment;

import springies.Common;

public abstract class WallRepulsion extends Force {
//    private String wallId;
    private double magnitude;
    private double exponent;
    protected double wallThickness;
    
    public WallRepulsion(String wallId, double magnitude, double exponent){
//        this.wallId = wallId;
        this.magnitude = magnitude;
        this.exponent = exponent;
        this.wallThickness = Common.WALL_THICKNESS;
    }
    
    public void incrementWallThickness(double increment){
        wallThickness+=increment;
    }
    
    public double calculateForce(double distance){
        return magnitude/Math.pow(distance, exponent);
    }
    
 

}
