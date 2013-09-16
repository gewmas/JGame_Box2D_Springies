package object;

import org.jbox2d.common.Vec2;
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
        this.x = x;
        this.y = y;
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
