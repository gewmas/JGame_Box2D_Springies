package object;

import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;

/**
 * A fixed object that is not affected by applied forces. 
 * This object can be used to instantiate a fixed mass or a wall.
 * By default, this shows up as a green square (mass) or rectangle (wall).
 * The height and width of a wall can be changed to change the size of the 
 * 
 * @author Yuhua Mai, Susan Zhang
 *
 */
public class FixedMass extends PhysicalObjectRect {
    private static final int DEFAULTHeight = 10;
    private static final int DEFAULTWidth = 10;
    
    private static final JGColor color = JGColor.green;
    private String id;

    /**
     * Constructor for FixedMass class if height and width are not given. This constructor
     * is used to create a fixed mass in the environment with a default height and width.
     * 
     * @param id Object ID
     * @param collisionId Object collision ID
     * @param x x position of object
     * @param y y position of object
     */
    public FixedMass(String id, int collisionId, double x, double y) {
        this(id, collisionId, DEFAULTWidth, DEFAULTHeight, x, y);
    }

    /**
     * Constructor for fixed mass wall
     * 
     * @param width initial width of wall
     * @param height initial height of wall
     */
    public FixedMass(String id, int collisionId, double width, double height, double x, double y) {
        super(id, collisionId, color, width, height);
        setPos(x, y);
        this.id = id;
        this.x=x;
        this.y=y;
    }

    /**
     * Changes the width and height of object along with the dimensions of the BBox
     * 
     * @param value change in wall thickness
     */
    public void changeThickness(double value){
        myWidth += value;
        myHeight += value;
        this.setBBox(-(int) (myWidth/2), -(int) (myHeight/2), (int) (myWidth), (int) (myHeight));
    }
    
    public double getWidth(){
        return myWidth;
    }
    
    public double getHeight(){
        return myHeight;
    }
   
    public String getId () {
        return id;
    }


}
