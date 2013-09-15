package object;

import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;
import jgame.JGObject;

public class FixedMass extends PhysicalObjectRect {
    private static final JGColor color = JGColor.green;
    //    private static final double radius = 8;
    private String id;


    public FixedMass(String id, int collisionId, double x, double y) {
        super(id, collisionId, color, 10.0, 10.0);
        setPos(x, y);
        this.id = id;

    }

    public FixedMass(String id, int collisionId, double width, double height, double x, double y) {
        super(id, collisionId, color, width, height);
        setPos(x, y);
        System.out.println("Mywidth: " + width + " height: " + height);
        this.id = id;

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
