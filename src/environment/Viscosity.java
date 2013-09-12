package environment;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;

public class Viscosity extends Force {

	private final static double DAMPING_FACTOR = 0.999;
	
	public static void SetViscosity(PhysicalObject object){
		Vec2 velocity = object.getBody().getLinearVelocity();
		
		velocity.x *= DAMPING_FACTOR;
		velocity.y *= DAMPING_FACTOR;
		
		object.getBody().setLinearVelocity( velocity );
	}
}
