package environment;

import jboxGlue.PhysicalObject;
import springies.Common;

public class WallRepulsion3 extends WallRepulsion {

    public WallRepulsion3 (String wallId, double magnitude, double exponent) {
        super(wallId, magnitude, exponent);
    }
    
    public void setForce(PhysicalObject object){
        object.setForce(0, -calculateForce(calculateDistance(object)));
        
        System.out.println("Calling WallRepulsion3 setForce!");
    }
    
    public double calculateDistance(PhysicalObject object){
        return (Common.HEIGHT-object.y-Common.WALL_MARGIN-wallThickness);             
    }

}
