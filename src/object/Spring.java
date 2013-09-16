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
        this.currentLength = length; //Should this be actual distance between masses?
        this.k = k;
        
        if (length==0){
            restLength=calculateActualDistance();
        }
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
   }

    public double calculateActualDistance(){
        
        return Math.sqrt(Math.pow((mass1.x-mass2.x),2)+ Math.pow((mass1.y-mass2.y),2));
    }

    public void setLength(){
        currentLength = calculateActualDistance();
    }

    @Override
    public void paintShape( )
    {
        myEngine.drawLine(mass1.x, mass1.y, mass2.x, mass2.y, 1, JGColor.white);
    }


}
