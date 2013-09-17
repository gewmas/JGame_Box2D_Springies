package object;

import jboxGlue.PhysicalObject;
import jgame.JGColor;
/**
 * 
 * This class defines the Muscle object, a spring that is able to generate its own force on the two 
 * masses attached its endpoints
 * 
 * @author Yuhua Mai, Susan Zhang
 * 
 */
public class Muscle extends Spring {
        private double amplitude;
        private double timer=0;
   
        /**
         * Constructor for for a Muscle object
         * 
         * @param id object ID
         * @param collisionId object collision ID
         * @param color object color
         * @param mass1 first Mass object that is attached to Muscle
         * @param mass2 second Mass object that is attached to Muscle
         * @param restlength rest length
         * @param k spring constant 
         * @param amplitude amplitude 
         */
	public Muscle(String id, int collisionId, JGColor color, PhysicalObject mass1,
			PhysicalObject mass2, double restlength, double k, double amplitude) {
		super(id, collisionId, color, mass1, mass2, restlength, k);
		this.amplitude = amplitude;
		this.k=k;
	}
	
	/**
	 * Moves muscle object by calling setLength() and springMove() and increments timer for sinusoidal motion
	 */
	@Override
	public void move(){
            setLength();
            springMove();
            timer+=0.1;
        }
	
	/**
	 * Changes length of muscle based on a sinusoidal function of time
	 */
	@Override
	public void setLength(){
	    currentLength = restLength+amplitude*Math.sin(timer);
	          
	   System.out.println("Muscle " + timer + " restLength:" + restLength + " length: " + (currentLength-restLength));
	}



}
