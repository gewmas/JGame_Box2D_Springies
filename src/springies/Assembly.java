package springies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import object.Mass;
import jboxGlue.PhysicalObject;
import jgame.JGColor;
/**
 * Class that holds models for each individual assembly. Essentially an ArrayList of models
 * with some extended functionality, such as changing color of the objects.
 * 
 * @author Yuhua Mai, Susan Zhang
 *
 */
public class Assembly {
    private List<Model> models = new ArrayList<Model>();
    /**
     * Returns list of models contained in assembly
     */
    public List<Model> getModels() {
        return models;
    }

    /**
     * Adds model to assembly
     * @param newModel model to add
     */
    public void addModel(Model newModel){
        models.add(newModel);
    }
    
    /**
     * Changes color of masses in each model in assembly to a random color
     */
    public void setRandomColor(){
        for(Model model : models){
            List<PhysicalObject> objects = model.getObjects();
            JGColor color = getRandomColor();
            
            for(PhysicalObject object:objects){
                if(object instanceof Mass){
                    object.setColor(color);
                }
            }
        }
    }
    
    /**
     * Generates a random JGColor by using a random generator between 0 and 255
     * 
     * @return random
     */
    public JGColor getRandomColor(){
        Random generator = new Random();
        
        int r = generator.nextInt(255);
        int g = generator.nextInt(255);
        int b = generator.nextInt(255);
        
        return new JGColor(r, g, b);
    }
    
    /**
     * Returns the mass that is closest to a mouse click
     * 
     * @param mouseX x position of mouse
     * @param mouseY y position of mouse
     * @return mass that is closest to where mouse was clicked
     */
    public Mass calculateNearestMass(int mouseX, int mouseY){
        List<Mass> masses = new ArrayList<Mass>();
        Mass nearestMass = null;
        double shortestDistance = Double.MAX_VALUE;
        
        for(Model model : models){
            Mass mass = model.calculateNearestMass(mouseX, mouseY);
            if(mass != null){
                masses.add(mass);
            }
        }
        
        nearestMass = masses.get(0);
        for(Mass mass:masses){
            double tempDistance = Math.sqrt(Math.pow(mass.x-mouseX, 2) + Math.pow(mass.y-mouseY, 2));
            
            if(tempDistance < shortestDistance){
                shortestDistance = tempDistance;
                nearestMass = mass;
            }
        }
        
        System.out.println(nearestMass);
        return nearestMass;
        
    }
    
    /**
     * Clears the List of models
     */
    public void clear(){
        models.clear();
    }
    
}
