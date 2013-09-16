package environment;

import java.util.List;
import object.Mass;
import jboxGlue.PhysicalObject;

public abstract class Force {

    public abstract void setForce(PhysicalObject object);
    public boolean valid = true;

    public void setForce (List<PhysicalObject> objects) {
        for(PhysicalObject object : objects){
            if(object instanceof Mass && valid){
                setForce(object);
            }
        }
    }
    
    public void toggleValid () {
        System.out.println("Toggle Valid!");
        this.valid = !this.valid;
    }
}
