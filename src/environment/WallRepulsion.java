package environment;

import springies.Common;
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
        if (wallId.equals("1")){
            object.setForce(0, calculateForce(calculateDistance(object)));
        }else if(wallId.equals("2")){
            object.setForce(-calculateForce(calculateDistance(object)),0);
        }else if(wallId.equals("3")){
            object.setForce(0, -calculateForce(calculateDistance(object)));
        }else if(wallId.equals("4")){
            object.setForce(calculateForce(calculateDistance(object)),0);
        }
        
    }
    
    public double calculateForce(double distance){
        return magnitude/Math.pow(distance, exponent);
    }
    
    public double calculateDistance(PhysicalObject object){
        if (wallId.equals("1")){
            return (object.y-Common.WALL_MARGIN-Common.WALL_THICKNESS);
        }else if(wallId.equals("2")){
            return (Common.WIDTH-Common.WALL_MARGIN-Common.WALL_THICKNESS-object.x);           
        }else if(wallId.equals("3")){
            return (Common.HEIGHT-object.y-Common.WALL_MARGIN-Common.WALL_THICKNESS);          
        }else{
            return (object.x-Common.WALL_MARGIN-Common.WALL_THICKNESS);
        }
    }

}
