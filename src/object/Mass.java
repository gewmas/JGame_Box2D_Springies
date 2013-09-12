package object;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;
import jgame.JGObject;

public class Mass extends PhysicalObjectRect{

	public Mass(String id, int collisionId, JGColor color, double width,
			double height, double mass) {
		super(id, collisionId, color, width, height, mass);
		// TODO Auto-generated constructor stub
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
