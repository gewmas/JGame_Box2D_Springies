package springies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import object.Mass;
import jboxGlue.PhysicalObject;
import jgame.JGColor;

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
    
   
    
}
