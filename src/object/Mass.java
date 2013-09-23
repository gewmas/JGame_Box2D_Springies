package object;

import org.jbox2d.common.Vec2;
import springies.Common;
import jboxGlue.*;
import jgame.JGColor;
import jgame.JGObject;

/**
 * Mass object that moves in response to applied forces
 * 
 * @author Yuhua Mai, Susan Zhang
 *
 */
public class Mass extends PhysicalObjectCircle{
    private static final JGColor color = JGColor.blue;
    private static final double radius = 10;
    private double wallThickness;

    /**
     * Returns a new mass object
     * 
     * @param id object ID
     * @param collisionId object collision ID
     * @param mass mass of object 
     * @param x initial x position
     * @param y initial y position
     * @param vx initial x direction velocity
     * @param vy initial y direction velocity
     */
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
        this.wallThickness = Common.WALL_THICKNESS;
    }

    /**
     * Defines collisions with other JGObjects
     * 
     * @param other the object that the mass collides with
     */
    public void hit(JGObject other){
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

    
    /**
     * Offset the masses once it go outside the wall
     * Could not work very well because the offset is set to be small
     */
    public void move( ){
        super.move();
        
        if(x < Common.WALL_MARGIN + wallThickness){
            setPos(x+Common.CHANGE_THICKNESS,y);
        }
        if(x > Common.WIDTH-Common.WALL_MARGIN-wallThickness){
            setPos(x-Common.CHANGE_THICKNESS,y);
        }
        if(y < Common.WALL_MARGIN + wallThickness){
            setPos(x, y+Common.CHANGE_THICKNESS);
        }
        if(y > Common.HEIGHT-Common.WALL_MARGIN-wallThickness){
            setPos(x, y-Common.CHANGE_THICKNESS);
        }
    }
    
    /**
     * Changes color of Mass
     * 
     * @param color JGColor of object
     */
    public void setMassColor(JGColor color){
        this.setColor(color);
    }
    
    /**
     * Changes the position of masses as the wall thickness change
     * 
     * @param the value to change the wall thickness
     */
    public void changePos(int changeWallThicknessValue){
        wallThickness+=changeWallThicknessValue;
        
//        System.out.println("Wall Thickness" + wallThickness);
        
        if(x < Common.WIDTH/2 && y < Common.HEIGHT/2){
            setPos(x+changeWallThicknessValue, y+changeWallThicknessValue);
        }else if(x > Common.WIDTH/2 && y < Common.HEIGHT/2){
            setPos(x-changeWallThicknessValue, y+changeWallThicknessValue);
        }else if(x < Common.WIDTH/2 && y > Common.HEIGHT/2){
            setPos(x+changeWallThicknessValue, y-changeWallThicknessValue);
        }else if(x > Common.WIDTH/2 && y > Common.HEIGHT/2){
            setPos(x-changeWallThicknessValue, y-changeWallThicknessValue);
        }
    }

}
