package springies;

import java.util.ArrayList;
import java.util.List;
import jboxGlue.PhysicalObject;
import environment.Force;
import object.*;

public class Model {
    private List<PhysicalObject> objects;
    private List<Force> forces;
    private List<FixedMass> fixedMass;
	
    public Model () {
    	objects = new ArrayList<PhysicalObject>();
    	fixedMass = new ArrayList<FixedMass>();
    	forces = new ArrayList<Force>();
    }
    
    public void add(PhysicalObject object){
        if(object instanceof FixedMass){
            fixedMass.add((FixedMass) object);
        }else{
            objects.add(object);
        }
    }
    
    public void add(Force force){
    	forces.add(force);
    }

    public List<PhysicalObject> getObjects () {
        return objects;
    }

    public List<FixedMass> getFixedMasses () {
        return fixedMass;
    }

    public List<Force> getForces () {
        return forces;
    }
    
    public void clear(){
        for(PhysicalObject object:objects){
            object.remove();
        }
        
        forces.clear();
        
        //No need to remove walls and other fixedMass
        /*for(PhysicalObject object:fixedMass){
            object.remove();
        }*/
        
    }
    
    public Mass calculateNearestMass(int mouseX, int mouseY){
        Mass nearestMass = null;
        double shortestDistance = Double.MAX_VALUE;
        
        for(PhysicalObject object:objects){
            if(object instanceof Mass){
                double tempDistance = Math.sqrt(Math.pow(object.x-mouseX, 2) + Math.pow(object.y-mouseY, 2));
                
                if(tempDistance < shortestDistance){
                    shortestDistance = tempDistance;
                    nearestMass = (Mass)object;
                }
            }
        }
        
        return nearestMass;
        
    }
    
}
