package object;

import jgame.JGColor;

public class Muscle extends Spring {
        private double amplitude;
        double timer=0;
    
	public Muscle(String id, int collisionId, JGColor color, Mass mass1,
			Mass mass2, double restlength, double amplitude) {
		super(id, collisionId, color, mass1, mass2, restlength);
		this.amplitude = amplitude;
	}
	
	@Override
	public void move(){
            setLength();
            springMove();
            timer+=0.1;
        }
	

	@Override
	public void setLength(){
	    currentLength = 5*restLength*(Math.sin(timer*2*Math.PI));
	          
//	   System.out.println("Muscle " + timer + " restLength:" + restLength + " length: " + (currentLength-restLength));
	}



}
