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
        if(other.colid == Common.FIXEDMASS_CID){
            
            Vec2 velocity = myBody.getLinearVelocity();
            velocity.x *= -1.2;
            velocity.y *= -1.2;
            System.out.println("Hit FixedMass" + velocity.x + " " + velocity.y);
            myBody.setLinearVelocity( velocity );
        }
    }
    
//    public void paintShape( )
//    {
//        myEngine.setColor( myColor );
//        myEngine.drawOval(x, y, radius*2, radius*2, true, true);
//    }

}
