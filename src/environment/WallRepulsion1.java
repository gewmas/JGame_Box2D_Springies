package environment;

import jboxGlue.PhysicalObject;
import springies.Common;

public class WallRepulsion1 extends WallRepulsion {

    public WallRepulsion1 (String wallId, double magnitude, double exponent) {
        super(wallId, magnitude, exponent);
    }
    
    public void setForce(PhysicalObject object){
         object.setForce(0, calculateForce(calculateDistance(object)));
         
         System.out.println("Calling WallRepulsion1 setForce!");
    }
    
    public double calculateDistance(PhysicalObject object){    
        return (object.y-Common.WALL_MARGIN-wallThickness);
    }

}
