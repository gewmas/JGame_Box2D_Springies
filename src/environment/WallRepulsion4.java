package environment;

import jboxGlue.PhysicalObject;
import springies.Common;

public class WallRepulsion4 extends WallRepulsion {

    public WallRepulsion4 (String wallId, double magnitude, double exponent) {
        super(wallId, magnitude, exponent);
    }
    
    public void setForce(PhysicalObject object){ 
        object.setForce(calculateForce(calculateDistance(object)),0);
        
        System.out.println("Calling WallRepulsion4 setForce!");
    }
    
    public double calculateDistance(PhysicalObject object){
        return (object.x-Common.WALL_MARGIN-wallThickness);
    }

}
