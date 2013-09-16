package object;

import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;

public class FixedMass extends PhysicalObjectRect {
    private static final int defaultHeight = 10;
    private static final int defaultWidth = 10;
    private static final JGColor color = JGColor.green;
    private String id;


    public FixedMass(String id, int collisionId, double x, double y) {
        this(id, collisionId, defaultWidth, defaultHeight, x, y);
    }

    public FixedMass(String id, int collisionId, double width, double height, double x, double y) {
        super(id, collisionId, color, width, height);
        setPos(x, y);
        this.id = id;
        this.x=x;
        this.y=y;

    }

    public void increaseWidth(){
        myWidth += 10;
    }
    
    public void increaseHeight(){
        myHeight += 10;
    }
    
    public String getId () {
        return id;
    }


}
