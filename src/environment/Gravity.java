package environment;

import java.util.List;
import org.jbox2d.common.Vec2;
import jboxGlue.PhysicalObject;

public class Gravity extends Force {
    private double direction;
    private double magnitude;
        
    Gravity(double direction, double magnitude){
        this.direction = direction;
        this.magnitude = magnitude;
    }
    
    public void setForce(PhysicalObject object){
        object.setForce(magnitude*object.mass*Math.cos(direction), magnitude*object.mass*Math.sin(direction));
    }

}
