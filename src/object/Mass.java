package object;

import org.jbox2d.common.Vec2;

import jboxGlue.*;
import jgame.JGColor;
import jgame.JGObject;

public class Mass extends PhysicalObjectCircle{
        
    
        
    
	public Mass(String id, int collisionId, double radius, double mass, double x, double y, double vx, double vy) {
		super(id, collisionId, JGColor.blue, radius, mass);
		
		setPos(x, y);
	        Vec2 velocity = new Vec2();
	        velocity.x = (float) vx;
	        velocity.y = (float) vy;
	        getBody().setLinearVelocity(velocity);
	        this.mass=mass;
	}
	
	public Mass(String id, int collisionId, JGColor color, double radius,
			double mass) {
		super(id, collisionId, JGColor.blue, radius, mass);
		this.mass=mass;
	}

	public void hit( JGObject other )
	{
		// we hit something! bounce off it!
		Vec2 velocity = myBody.getLinearVelocity();
		
		// is it a tall wall?
		final double DAMPING_FACTOR = 0.8;
		boolean isSide = other.getBBox().height > other.getBBox().width;
		if( isSide )
		{
			velocity.x *= -DAMPING_FACTOR;
		}
		else
		{
			velocity.y *= -DAMPING_FACTOR;
		}
		
		// apply the change
		myBody.setLinearVelocity( velocity );
	}

}
