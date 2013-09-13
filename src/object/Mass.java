package object;

import org.jbox2d.common.Vec2;
import springies.Common;
import jboxGlue.*;
import jgame.JGColor;
import jgame.JGObject;

public class Mass extends PhysicalObjectCircle{
        private static final JGColor color = JGColor.blue;
        private static final double radius = 10;
        
    
	public Mass(String id, int collisionId, double mass, double x, double y, double vx, double vy) {
		super(id, collisionId, color, radius, mass);
		
		setPos(x, y);
	        Vec2 velocity = new Vec2();
	        velocity.x = (float) vx;
	        velocity.y = (float) vy;
	        getBody().setLinearVelocity(velocity);
	        this.setMass(mass);
	}


    public Mass (String id, int collisionId, JGColor color2, double radius2, double x, double y) {
        super(id, collisionId, color2, radius2);
        
        setPos(x, y);
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
//        if(other.colid == Common.FIXEDMASS_CID){
            
//            Vec2 velocity = myBody.getLinearVelocity();
//            velocity.x *= -0.8;
//            velocity.y *= -0.8;
        System.out.println("Mass Hit" + velocity.x + " " + velocity.y);
//            myBody.setLinearVelocity( velocity );
//        }
    }
    
//    public void paintShape( )
//    {
//        myEngine.setColor( myColor );
//        myEngine.drawOval(x, y, radius*2, radius*2, true, true);
//    }

}
