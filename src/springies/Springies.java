package springies;

import java.util.List;
import javax.swing.JFileChooser;
import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectRect;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.platform.JGEngine;
import object.*;
import org.jbox2d.common.Vec2;

//import javax.swing.JFileChooser;

import environment.Force;
import environment.Viscosity;

@SuppressWarnings( "serial" )
public class Springies extends JGEngine
{
	private static final JFileChooser INPUT_CHOOSER = 
            new JFileChooser(System.getProperties().getProperty("user.dir"));
	Model model;
	
	public Springies( )
	{
		// set the window size
		int height = 600;
		double aspect = 16.0/9.0;
		initEngine( (int)(height*aspect), height );
	}
	
	@Override
	public void initCanvas( )
	{
		// I have no idea what tiles do...
		setCanvasSettings(
			1, // width of the canvas in tiles
			1, // height of the canvas in tiles
			displayWidth(), // width of one tile
			displayHeight(), // height of one tile
			null,// foreground colour -> use default colour white
			null,// background colour -> use default colour black
			null // standard font -> use default font
		);
	}
	
	@Override
	public void initGame( )
	{
		setFrameRate( 60, 2 );

		// init the world
		// One thing to keep straight: The world coordinates have y pointing down
		// the game coordinates have y pointing up
		// so gravity is along the positive y axis in world coords to point down in game coords
		// remember to set all directions (eg forces, velocities) in world coords
		WorldManager.initWorld( this );
		WorldManager.getWorld().setGravity( new Vec2( 0.0f, 0.0f ) );
		
		
		loadModel();
		
		// add a bouncy ball
//		ball = new Ball("ball", 1, JGColor.blue, 10, 5);
//		ball.setPos( displayWidth()/2, displayHeight()/2 );
//		ball.setForce( 8000, -10000 );

		
		
		// add mass
//		Mass mass1 = new Mass("mass1", 3, JGColor.green, 25, 25, 0.5);
//		mass1.setPos(displayWidth()/3, 200);
//		mass1.setForce(2000, 10000);
//		Viscosity.SetViscosity(mass1);
		
//		Mass mass2 = new Mass("mass2", 3, JGColor.blue, 25, 25, 0.5);
//		mass2.setPos(2*displayWidth()/3, 200);
//		mass2.setForce(0, 1000);
		
//		Mass mass3 = new Mass("mass2", 3, JGColor.magenta, 25, 25, 0.5);
//		mass3.setPos(2*displayWidth()/3, 400);
		
		// add a spring
//		Spring spring1 = new Spring("spring1", 4, JGColor.cyan, mass1, mass2, 100);
//		Spring spring2 = new Spring("spring2", 4, JGColor.cyan, mass1, mass3, 100);
//		Spring spring3 = new Spring("spring3", 4, JGColor.cyan, mass2, mass3, 150);
//		spring.setPos(displayWidth()/2, displayHeight()/3);
		
		// add walls to bounce off of
		// NOTE: immovable objects must have no mass
//		final double WALL_MARGIN = 10;
//		final double WALL_THICKNESS = 10;
//		final double WALL_WIDTH = displayWidth() - WALL_MARGIN*2 + WALL_THICKNESS;
//		final double WALL_HEIGHT = displayHeight() - WALL_MARGIN*2 + WALL_THICKNESS;
//		PhysicalObject wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS );
//		wall.setPos( displayWidth()/2, WALL_MARGIN );
//		wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS );
//		wall.setPos( displayWidth()/2, displayHeight() - WALL_MARGIN );
//		wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT );
//		wall.setPos( WALL_MARGIN, displayHeight()/2 );
//		wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT );
//		wall.setPos( displayWidth() - WALL_MARGIN, displayHeight()/2 );
	}
	
	
	
	@Override
	public void doFrame( )
	{
		// update game objects
		WorldManager.getWorld().step( 1f, 1 );
		moveObjects();
		
//		spring.doFrame();
//		Viscosity.SetViscosity(ball);
		
		checkCollision( 1+2, 1 );
//		checkCollision( 2+3, 3 );
		
		List<PhysicalObject> objects = model.getObjects();
		List<Force> forces = model.getForces();
		
		for(Force force : forces){
		    force.SetForce(objects);
		}
		
	}
	
	@Override
	public void paintFrame( )
	{
		// nothing to do
		// the objects paint themselves
	}
	
	public void loadModel(){
		if(model == null) model = new Model();
		
		Parser parser = new Parser();
        int response = INPUT_CHOOSER.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            parser.loadModel(model, INPUT_CHOOSER.getSelectedFile());
        }
	}
}
