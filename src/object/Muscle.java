package object;

import jgame.JGColor;

public class Muscle extends Spring {
        private double amplitude;
    
	public Muscle(String id, int collisionId, JGColor color, Mass mass1,
			Mass mass2, double restlength, double amplitude) {
		super(id, collisionId, color, mass1, mass2, restlength);
		this.amplitude = amplitude;
	}

}
