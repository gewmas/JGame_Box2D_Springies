package environment;

import java.util.List;
import object.Mass;
import org.jbox2d.common.Vec2;
import jboxGlue.PhysicalObject;

public class Viscosity extends Force {

        private double magnitude;
	private final static double DAMPING_FACTOR = 0.999;
	
	public Viscosity(double magnitude){
	    this.magnitude = magnitude;
	}
	
	public void setForce(PhysicalObject object){
		Vec2 velocity = object.getBody().getLinearVelocity();
		
		velocity.x *= DAMPING_FACTOR;
		velocity.y *= DAMPING_FACTOR;
		
		object.getBody().setLinearVelocity( velocity );
		
//		System.out.println("Calling Viscosity setForce");
	}

      
//        public void setForce (List<PhysicalObject> objects) {
//            for(PhysicalObject object : objects){
//                if(object instanceof Mass){
//                    setForce(object);
//                }
//            }
//            
//        }
}
