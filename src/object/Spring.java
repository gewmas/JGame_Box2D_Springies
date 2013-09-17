package object;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;
/**Spring object that behaves accordingly to Hooke's law F=-kx
 * 
 * 
 *@author Yuhua Mai, Susan Zhang
 */
public class Spring extends PhysicalObjectRect{

    protected PhysicalObject mass1;
    protected PhysicalObject mass2;
    protected double restLength;
    protected double currentLength;
    protected double k;
    protected double dx;
    protected double dy;
/**
 * Constructor for Spring Object
 * 
 * @param id object ID
 * @param collisionId object collision ID
 * @param color color of spring
 * @param mass1 first Mass object that is attached to spring
 * @param mass2 second Mass object that is attached to string
 * @param length rest length 
 * @param k spring constant
 */
    public Spring(String id, int collisionId, JGColor color, PhysicalObject mass1, PhysicalObject mass2, double length, double k) {
        super(id, collisionId, color, 0, 0, 0);
        this.mass1 = mass1;
        this.mass2 = mass2;
        this.restLength = length;
        this.currentLength = calculateActualDistance();
        this.k = k;
        
        if (length==0){
            restLength=calculateActualDistance();
        }
    }

    /** Causes actual movement of Spring by calling setLength() and springMove()
     */
    public void move( ){
        setLength();
        springMove();

    }

    /** Applies force on masses based on Hooke's law and x and y distances between masses
     */
    public void springMove(){
        dx = mass2.x - mass1.x;
        dy = mass2.y - mass1.y;              

        double force = k*(currentLength-restLength);

        mass1.setForce(dx*force, dy*force);
        mass2.setForce(-dx*force, -dy*force);
   }
    
    /** Calculates the Euclidean distance between the two masses the spring is attached to
     * 
     * @return distance between mass1 and mass2
     */
    public double calculateActualDistance(){    
        return Math.sqrt(Math.pow((mass1.x-mass2.x),2)+ Math.pow((mass1.y-mass2.y),2));
    }

    /** Sets current length of spring to distance between mass1 and mass2

     */
    public void setLength(){
        currentLength = calculateActualDistance();
    }

    @Override
    public void paintShape( )
    {
        myEngine.drawLine(mass1.x, mass1.y, mass2.x, mass2.y, 1, JGColor.white);
    }


}
