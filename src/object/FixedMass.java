package object;

import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;

public class FixedMass extends PhysicalObjectRect {
    private static final int DEFAULTHeight = 10;
    private static final int DEFAULTWidth = 10;
    
    private static final JGColor color = JGColor.green;
    private String id;


    public FixedMass(String id, int collisionId, double x, double y) {
        this(id, collisionId, DEFAULTWidth, DEFAULTHeight, x, y);
    }

    public FixedMass(String id, int collisionId, double width, double height, double x, double y) {
        super(id, collisionId, color, width, height);
        setPos(x, y);
        this.id = id;
        this.x=x;
        this.y=y;

    }

    public void changeThickness(double value){
//        System.out.println(myWidth + " " + myHeight + " " + value); 
        
        myWidth += value;
        myHeight += value;
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
