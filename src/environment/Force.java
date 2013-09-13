package environment;

import java.util.List;
import object.Mass;
import jboxGlue.PhysicalObject;

public abstract class Force {

    public abstract void setForce(PhysicalObject object);
    
    public void setForce (List<PhysicalObject> objects) {
        for(PhysicalObject object : objects){
            if(object instanceof Mass){
                setForce(object);
            }
        }
        
    }
}
