package environment;

import java.util.List;
import object.Mass;
import jboxGlue.PhysicalObject;

/**
 * Class for CenterOfMass force. The center of mass force attracts all masses in a single model towards
 * the weighted average position of the masses. 
 * 
 * @author Yuhua Mai, Susan Zhang
 * 
 */
public class CenterOfMass extends Force {
    private double magnitude;
    private double exponent;
    private double centerX;
    private double centerY;
    
    /**
     * Constructor for CenterOfMass class
     * 
     * @param magnitude magnitude of center of mass force
     * @param exponent the exponent that the force will be inversely proportional to
     */
    public CenterOfMass(double magnitude, double exponent){
        this.magnitude = magnitude;
        this.exponent = exponent;
    }

    /**
     * Applies center of mass force on a single Physical object
     */
    public void setForce(PhysicalObject object){
        double dx = centerX - object.x;
        double dy = centerY - object.y;
        double dist = Math.sqrt(dx*dx + dy*dy);
        
        double force = magnitude/Math.pow(dist,exponent);
        object.setForce(dx*force, dy*force);
        
//        System.out.println("Calling CenterOfMass setForce!");
    }
    
    /**
     * Calculates center of mass of a single assembly's list of PhysicalObjects and
     * then calls setForce individually for each mass. This overrides the setForce method
     * in the superclass because the method needs to be aware of all the masses in each model
     * in order to calculate the center of mass.
     * 
     */
    @Override
    public void setForce (List<PhysicalObject> objects) {
        centerX = 0;
        centerY = 0;
        int counter = 0;

        for(PhysicalObject object : objects){
            if(object instanceof Mass){
                counter++;
                
                centerX += object.getMass()*object.x;
                centerY += object.getMass()*object.y;
            }
        }
        centerX /= counter;
        centerY /= counter;
      
        super.setForce(objects);
        
    }

}
