package environment;

import org.jbox2d.common.Vec2;
import jboxGlue.PhysicalObject;

/**
 * Class for viscosity force, which applies a resistive force to the velocity of an object proportional
 * to the magnitude of this force
 * 
 * @author susanzhang93
 *
 */
public class Viscosity extends Force {

        private double magnitude;
	
        /**
         * Constructor for Viscosity Force
         * @param magnitude magnitude of viscosity force
         */
	public Viscosity(double magnitude){
	    this.magnitude = magnitude;
	}
	
	/**
	 * Applies force to physical object that is proportional to x and y components of object's velocity
	 * 
	 */
	public void setForce(PhysicalObject object){
	    Vec2 velocity = object.getBody().getLinearVelocity();
	    velocity.x *= magnitude;
	    velocity.y *= magnitude;
	    object.getBody().setLinearVelocity( velocity );
	    
//	    System.out.println("Calling Viscosity setForce!");
	}

}
