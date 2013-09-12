package springies;

import java.util.ArrayList;
import java.util.List;

import object.*;

public class Model {
    private List<Mass> masses;
    private List<Spring> springs;
	
    public Model () {
    	masses = new ArrayList<Mass>();
    	springs = new ArrayList<Spring>();
    }
    
    public void add (Mass mass) {
    	masses.add(mass);
    }

    /**
     * Add given spring to this simulation.
     */
    public void add (Spring spring) {
    	springs.add(spring);
    }
}
