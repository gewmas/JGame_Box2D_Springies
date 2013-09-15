package object;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObjectCircle;
import jgame.JGColor;
import jgame.JGObject;

public class Ball extends PhysicalObjectCircle{

	
	public Ball(String name, int collisionId, JGColor color, double radius, double mass) {
		super(name, collisionId, color, radius, mass);
	}
	
	@Override
	public void hit( JGObject other )
	{
		// we hit something! bounce off it!
		Vec2 velocity = myBody.getLinearVelocity();
		
		// is it a tall wall?
		final double DAMPING_FACTOR = 1;
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
