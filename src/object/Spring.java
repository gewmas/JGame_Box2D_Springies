package object;

import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;

public class Spring extends PhysicalObjectRect{

	Mass mass1;
	Mass mass2;
	double length;
	double k;
	
	public Spring(String id, int collisionId, JGColor color, Mass mass1, Mass mass2, double length, double k) {
		super(id, collisionId, color, 0, 0, 0);
		this.mass1 = mass1;
		this.mass2 = mass2;
		this.length = length;
		this.k = k;
	}
	
	public Spring(String id, int collisionId, JGColor color, Mass mass1, Mass mass2, double length) {
            super(id, collisionId, color, 0, 0, 0);
            this.mass1 = mass1;
            this.mass2 = mass2;
            this.length = length;
            this.k = 0;
        }
	
	public void move( ){
		super.move();
		
		double dx = mass2.x - mass1.x;
        double dy = mass2.y - mass1.y;
//        // apply hooke's law to each attached mass
//        Vector force = new Vector(Vector.angleBetween(dx, dy), 
//                                  myK * (myLength - Vector.distanceBetween(dx, dy)));
//        myStart.applyForce(force);
//        force.negate();
//        myEnd.applyForce(force);
//        // update sprite values based on attached masses
//        setCenter(getCenter(myStart, myEnd));
//        setSize(getSize(myStart, myEnd));
//        setVelocity(Vector.angleBetween(dx, dy), 0);
//		
		
		double currentLength = Math.sqrt(Math.pow((mass2.x-mass1.x),2) + Math.pow((mass2.y-mass1.y),2));

		double force = k*(currentLength-length);
//		if(currentLength < length) force *= -1;
		
		mass1.setForce(dx*force, dy*force);
		mass2.setForce(-dx*force, -dy*force);
		
//		System.out.println(dx + " " + dy + " length: " + currentLength + "force: " + force);
		
	}

	@Override
	public void paintShape( )
	{
		myEngine.drawLine(mass1.x, mass1.y, mass2.x, mass2.y, 1, JGColor.white);
	}
	
	
}
