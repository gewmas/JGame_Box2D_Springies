package object;

import jboxGlue.PhysicalObject;
import jgame.JGColor;

public class Muscle extends Spring {
        private double amplitude;
        private double timer=0;
    
	public Muscle(String id, int collisionId, JGColor color, PhysicalObject mass1,
			PhysicalObject mass2, double restlength, double k, double amplitude) {
		super(id, collisionId, color, mass1, mass2, restlength, k);
		this.amplitude = amplitude;
		this.k=k;
	}
	
	@Override
	public void move(){
            setLength();
            springMove();
            timer+=0.1;
        }
	

	@Override
	public void setLength(){
	    currentLength = restLength+amplitude*Math.sin(timer);
	          
	   System.out.println("Muscle " + timer + " restLength:" + restLength + " length: " + (currentLength-restLength));
	}



}
