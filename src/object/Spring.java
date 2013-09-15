package object;

import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;

public class Spring extends PhysicalObjectRect{

	Mass mass1;
	Mass mass2;
	double restLength;
	double currentLength;
	double k;
	double dx;
	double dy;
	
	public Spring(String id, int collisionId, JGColor color, Mass mass1, Mass mass2, double length, double k) {
		super(id, collisionId, color, 0, 0, 0);
		this.mass1 = mass1;
		this.mass2 = mass2;
		this.restLength = length;
		this.currentLength = length;
		this.k = k;
	}
	
	public Spring(String id, int collisionId, JGColor color, Mass mass1, Mass mass2, double length) {
            super(id, collisionId, color, 0, 0, 0);
            this.mass1 = mass1;
            this.mass2 = mass2;
            this.restLength = length;
            if (length==0){
                restLength=Math.sqrt(Math.pow((this.mass1.x-this.mass2.x),2)+ Math.pow((this.mass1.y-this.mass2.y),2));
                System.out.println(restLength);
            }
            this.currentLength = length;
            this.k = 0.05;
        }
	
	public void move( ){
	        setLength();
		springMove();
	}
	
	public void springMove(){
	    dx = mass2.x - mass1.x;
	    dy = mass2.y - mass1.y;              
	       
	        
	    double force = k*(currentLength-restLength);
	                
	    mass1.setForce(dx*force, dy*force);
	    mass2.setForce(-dx*force, -dy*force);
	    
	    
//	    System.out.println("calling springMove force: " + force + " dx: " + dx + " dy: " + dy);
	}
	
	public void setLength(){
            currentLength = Math.sqrt(Math.pow((mass2.x-mass1.x),2) + Math.pow((mass2.y-mass1.y),2));
            
//            System.out.println(timer + " length: " + currentLength);
        }

	@Override
	public void paintShape( )
	{
		myEngine.drawLine(mass1.x, mass1.y, mass2.x, mass2.y, 1, JGColor.white);
	}
	
	
}
