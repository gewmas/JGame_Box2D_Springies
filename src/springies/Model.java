package springies;

import java.util.ArrayList;
import java.util.List;

import jboxGlue.PhysicalObject;
import environment.Force;
import object.*;

public class Model {
    private List<PhysicalObject> objects;
    private List<Force> forces;
	
    public Model () {
    	objects = new ArrayList<PhysicalObject>();
    	forces = new ArrayList<Force>();
    }
    
    public void add(PhysicalObject object){
    	objects.add(object);
    }
    
    public void add(Force force){
    	forces.add(force);
    }

    public List<PhysicalObject> getObjects () {
        return objects;
    }

    public List<Force> getForces () {
        return forces;
    }
    
}
