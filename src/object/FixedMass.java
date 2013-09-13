package object;

import jgame.JGColor;
import jgame.JGObject;

public class FixedMass extends Mass {
    private static final JGColor color = JGColor.green;
    private static final double radius = 8;
//    private static final double mass = 0;
//    private static final double vx = 0;
//    private static final double vy = 0;

    public FixedMass(String id, int collisionId, double x, double y) {
        super(id, collisionId, color, radius, x, y);
    }
    
    public void hit( JGObject other ){
        
    }

}
