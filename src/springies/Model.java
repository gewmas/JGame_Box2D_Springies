package springies;

import java.util.ArrayList;
import java.util.List;
import jboxGlue.PhysicalObject;
import object.FixedMass;
import object.Mass;
import environment.Force;


/**
 * Model class that stores an ArrayList of PhysicalObject, FixedMass and Force. A new Model class is
 * needed
 * for every XML file that is loaded.
 * 
 * @author Yuhua Mai, Susan Zhang
 * 
 */

public class Model {
    private List<PhysicalObject> objects;
    private List<Force> forces;
    private List<FixedMass> fixedMass;

    /**
     * Construct Model
     * Create ArrayList of PhysicalObject, FixedMass and Force
     */
    public Model () {
        objects = new ArrayList<PhysicalObject>();
        fixedMass = new ArrayList<FixedMass>();
        forces = new ArrayList<Force>();
    }

    /**
     * Add PhysicalOjbect to ArrayList fixedMass if is FixedMass
     * otherwise add to ArrayList objects
     * 
     * @param object the PhysicalObject created by Parser
     */
    public void add (PhysicalObject object) {
        if (object instanceof FixedMass) {
            fixedMass.add((FixedMass) object);
        }
        else {
            objects.add(object);
        }
    }

    /**
     * Add Force to ArrayList forces
     * 
     * @param force the Force created by Parser
     */
    public void add (Force force) {
        forces.add(force);
    }

    /**
     * Return ArrayList objects
     * 
     * @return List<PhysicalObject>
     */
    public List<PhysicalObject> getObjects () {
        return objects;
    }

    /**
     * Return ArrayList fixedMass
     * 
     * @return List<FixedMass>
     */
    public List<FixedMass> getFixedMasses () {
        return fixedMass;
    }

    /**
     * Return ArrayList forces
     * 
     * @return List<Force>
     */
    public List<Force> getForces () {
        return forces;
    }

    /**
     * Remove objects, forces
     * Not for fixedMass, assume the fixedMass can't be removed
     */
    public void clear () {
        for (PhysicalObject object : objects) {
            object.remove();
        }

        forces.clear();

        // No need to remove walls and other fixedMass
        /*
         * for(PhysicalObject object:fixedMass){
         * object.remove();
         * }
         */

    }

    /**
     * Return the Mass nearest to the Mouse Position
     * 
     * @param mouseX the X position of the mouse click
     * @param mouseY the Y position of the mouse click
     * @return Mass
     */
    public Mass calculateNearestMass (int mouseX, int mouseY) {
        Mass nearestMass = null;
        double shortestDistance = Double.MAX_VALUE;

        for (PhysicalObject object : objects) {
            if (object instanceof Mass) {
                double tempDistance =
                        Math.sqrt(Math.pow(object.x - mouseX, 2) + Math.pow(object.y - mouseY, 2));

                if (tempDistance < shortestDistance) {
                    shortestDistance = tempDistance;
                    nearestMass = (Mass) object;
                }
            }
        }

        return nearestMass;

    }

}
