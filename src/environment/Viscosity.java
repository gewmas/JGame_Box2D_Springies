package environment;

import org.jbox2d.common.Vec2;
import jboxGlue.PhysicalObject;

public class Viscosity extends Force {

        private double magnitude;
	
	public Viscosity(double magnitude){
	    this.magnitude = magnitude;
	}
	
	public void setForce(PhysicalObject object){
	    Vec2 velocity = object.getBody().getLinearVelocity();
	    velocity.x *= magnitude;
	    velocity.y *= magnitude;
	    object.getBody().setLinearVelocity( velocity );
	    
	    System.out.println("Calling Viscosity setForce!");
	}

}
