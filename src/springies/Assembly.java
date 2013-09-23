package springies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import object.Mass;
import jboxGlue.PhysicalObject;
import jgame.JGColor;
/**
 * Class that holds models for each individual assembly
 * 
 * @author Yuhua Mai, Susan Zhang
 *
 */
public class Assembly {
    private List<Model> models = new ArrayList<Model>();
    
    public List<Model> getModels() {
        return models;
    }

    public void addModel(Model newModel){
        models.add(newModel);
    }
    
    public void setColor(){
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
    
    public JGColor getRandomColor(){
        Random generator = new Random();
        
        int r = generator.nextInt(255);
        int g = generator.nextInt(255);
        int b = generator.nextInt(255);
        
        return new JGColor(r, g, b);
    }
    
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
    
    public void clear(){
        models.clear();
    }
    
}
