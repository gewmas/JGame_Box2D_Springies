package environment;

import java.util.List;
import org.jbox2d.common.Vec2;
import jboxGlue.PhysicalObject;

public class Gravity extends Force {
    private double gravity;
    private double magnitude;
        
    Gravity(double gravity, double magnitude){
        this.gravity = gravity;
        this.magnitude = magnitude;
    }
    
    public void setForce(PhysicalObject object){
        
    }

}
