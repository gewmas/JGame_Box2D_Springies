package environment;

import java.util.List;
import object.Mass;
import jboxGlue.PhysicalObject;

/**
 * CenterOfMass force
 * 
 *
 */
public class CenterOfMass extends Force {
    private double magnitude;
    private double exponent;
    private double centerX;
    private double centerY;
    
    public CenterOfMass(double magnitude, double exponent){
        this.magnitude = magnitude;
        this.exponent = exponent;
    }

    public void setForce(PhysicalObject object){
        double dx = centerX - object.x;
        double dy = centerY - object.y;
        double dist = Math.sqrt(dx*dx + dy*dy);
        
        double force = magnitude/Math.pow(dist,exponent);
        object.setForce(dx*force, dy*force);
        
        System.out.println("Calling CenterOfMass setForce!");
    }
    
    public void setForce (List<PhysicalObject> objects) {
        centerX = 0;
        centerY = 0;
        int counter = 0;
        //calculate new center
        for(PhysicalObject object : objects){
            if(object instanceof Mass){
                counter++;
                
                centerX += object.getMass()*object.x;
                centerY += object.getMass()*object.y;
            }
        }
        centerX /= counter;
        centerY /= counter;
        
        //apply force
        super.setForce(objects);
//        for(PhysicalObject object : objects){
//            if(object instanceof Mass){
//                setForce(object);
//            }
//        }
        
    }

}
