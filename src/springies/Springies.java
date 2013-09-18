package springies;

import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import jboxGlue.PhysicalObject;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.platform.JGEngine;
import object.*;
import environment.*;
import org.jbox2d.common.Vec2;

/**
 * Springies class that init Canvas, create World, load XML files to Model, set Force to Mass
 * and perform key and mouse operations
 * 
 * @author Yuhua Mai, Susan Zhang
 *
 */

@SuppressWarnings( "serial" )
public class Springies extends JGEngine
{
    private static final int changeWallThicknessValue = 10;
    private static final JFileChooser INPUT_CHOOSER = 
            new JFileChooser(System.getProperties().getProperty("user.dir"));
    private Model model;
    private Parser parser;
    
    private double wallWidth;
    private double wallHeight;
    
    private boolean mousePressed = false;
    private boolean massCreated = false;
    
    private Mass mouseMass;
    private Spring mouseSpring;
    
    /**
     * create JGEngine
     */
    public Springies( )
    {
        // set the window size
        initEngine( (int)(Common.WIDTH), (int)(Common.HEIGHT));
    }

    /**
     * create Canvas
     */
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

    /**
     * init Game, setFrameRate, setWorld, loadModel and create walls
     */
    @Override
    public void initGame( )
    {
        model = new Model();
        
//        this.addMouseListener(this.getMouseListeners());
        
        
        setFrameRate( 60, 2 );

        WorldManager.initWorld( this );
        WorldManager.getWorld().setGravity( new Vec2( 0.0f, 0.0f ) );

        loadModel();

        //add walls up-1 right-2 down-3 left-4
        wallWidth = displayWidth() - Common.WALL_MARGIN*2 + Common.WALL_THICKNESS;
        wallHeight = displayHeight() - Common.WALL_MARGIN*2 + Common.WALL_THICKNESS;
        model.add(new FixedMass("1", Common.WALL_CID, wallWidth,  Common.WALL_THICKNESS, displayWidth()/2, Common.WALL_MARGIN));
        model.add(new FixedMass("3", Common.WALL_CID, wallWidth,  Common.WALL_THICKNESS, displayWidth()/2, displayHeight() - Common.WALL_MARGIN));
        model.add(new FixedMass("4", Common.WALL_CID, Common.WALL_THICKNESS, wallHeight, Common.WALL_MARGIN, displayHeight()/2));
        model.add(new FixedMass("2", Common.WALL_CID, Common.WALL_THICKNESS, wallHeight, displayWidth() - Common.WALL_MARGIN, displayHeight()/2));
    }

    /**
     * Pop option pane for user to choose XML file
     */
    public void loadModel(){
        int n = JOptionPane.YES_OPTION;
   
        JOptionPane.showMessageDialog(this, "Please load a XML file.");
        
        while(n == JOptionPane.YES_OPTION){
            parser = new Parser();
            int loadObject = INPUT_CHOOSER.showOpenDialog(null);
            if (loadObject == JFileChooser.APPROVE_OPTION) {
                parser.loadModel(model, INPUT_CHOOSER.getSelectedFile());
            }
    
            n = JOptionPane.showConfirmDialog(
                                              this,
                                              "Would you like to add more XML file?",
                                              "LoadModel?",
                                              JOptionPane.YES_NO_OPTION);
    
        }
    }

    /**
     * Update methods
     */
    @Override
    public void doFrame( )
    {
        WorldManager.getWorld().step( 1f, 1 );
        
        moveObjects();

        checkCollision();
        applyForce();
        
        handleKeyboardEvent();
        handleMouseEvent();
    }

    /**
     * checkCollision for Wall and Mass
     */
    private void checkCollision () {
        checkCollision(Common.WALL_CID,Common.MASS_CID); //latter one call the hit method
    }

    /**
     * appleForce for every object in Model
     */
    private void applyForce () {
        List<PhysicalObject> objects = model.getObjects();
        List<Force> forces = model.getForces();
    
        for(Force force : forces){
            force.setForce(objects);
//            System.out.println("Instance of Gravity!" + force.getClass().getSimpleName());
        }
    }
    
    /**
     * handleKeyboardEvent to load more XML files, clear all objecs, toggleForce and change wall thickness
     */
    private void handleKeyboardEvent(){
        //read new XML file and clear all objects and forces
        if(getKey('N')){
            loadModel();
            clearKey('N');
        }else if(getKey('C')){
            clearAllObjects();
            clearKey('C');
        }
        
        //toggle forces
        if(getKey('G')){
            toggleForce(Common.GRAVITY_CLASS_NAME);
            clearKey('G');
        }else if(getKey('V')){
            toggleForce(Common.VISCOSITY_CLASS_NAME);
            clearKey('V');
        }else if(getKey('M')){
            toggleForce(Common.CENTEROFMASS_CLASS_NAME);
            clearKey('M');
        }
        
        if(getKey('1')){
            toggleForce(Common.WALLREPULSION1_CLASS_NAME);
            clearKey('1');
        }else if(getKey('2')){
            toggleForce(Common.WALLREPULSION2_CLASS_NAME);
            clearKey('2');
        }else if(getKey('3')){
            toggleForce(Common.WALLREPULSION3_CLASS_NAME);
            clearKey('3');
        }else if(getKey('4')){
            toggleForce(Common.WALLREPULSION4_CLASS_NAME);
            clearKey('4');
        }
        
        if(getKey(KeyUp)){
            List<FixedMass> fixedMasses = model.getFixedMasses();
            for(FixedMass fixedMass : fixedMasses){
//                if(fixedMass.getId().equals('1')){ //....Check Common.Max_ThickNess..Same For KeyDown
                    fixedMass.changeThickness(changeWallThicknessValue);
//                }
            }
            clearKey(KeyUp);
        }else if(getKey(KeyDown)){
            List<FixedMass> fixedMasses = model.getFixedMasses();
            for(FixedMass fixedMass : fixedMasses){
                fixedMass.changeThickness(-changeWallThicknessValue);
            }
            clearKey(KeyDown);
        }
    }
    
    /**
     * Call model's method to clear all objects
     */
    private void clearAllObjects () {
        int n = JOptionPane.showConfirmDialog(
                                              this,
                                              "Are you sure to clear all objects?",
                                              "Clear Ojbects",
                                              JOptionPane.YES_NO_OPTION);
        if(n == JOptionPane.YES_OPTION){
            model.clear();
        }
        
    }
    
    /**
     * toggle force in Force
     */
    private void toggleForce(String className){
        List<Force> forces = model.getForces();
        for(Force force:forces){
            if(force.getClass().getSimpleName().equals(className)){
                force.toggleValid();       
//                System.out.println("Calling toggleValid! " + force.getClass().getSimpleName());
            }
        }
    }

    /**
     * handleMouse Event
     * When clicked, create a Mass connected by a Spring to the nearestMass
     * When dragged, the created Mass will move
     * When release, the created Mass and Spring will removed
     */
    private void handleMouseEvent(){
        if(this.getMouseButton(1)){ //left click pressed
            System.out.println(" " + this.getMouseX() + " " + this.getMouseY());
            
            if(!massCreated){
                Mass nearestMass = model.calculateNearestMass(this.getMouseX(), this.getMouseY());
                System.out.println("NearestMass position: " + nearestMass.x + " " + nearestMass.y);
                mouseMass= new Mass("mouse", Common.MASS_CID, 1, this.getMouseX(), this.getMouseY(), 0, 0);
//                model.add(mouseMass);
                mouseSpring=new Spring("mouseSpring", Common.SPRING_CID, JGColor.white, nearestMass, mouseMass, 0, 1);
//                model.add(mouseSpring);
                
                massCreated = true;
            }
            
            mouseMass.setPos(this.getMouseX(), this.getMouseY());
            
            mousePressed = true;
        }else if(!this.getMouseButton(1) && mousePressed){ //left click release
            System.out.println("Mouse released");
            
            mouseMass.remove();
            mouseSpring.remove();
            
            mousePressed = false;
            massCreated = false;
        }
    }
    

    /**
     * PaintFrame, empty method
     */
    @Override
    public void paintFrame( )
    {
        // nothing to do
        // the objects paint themselves
    }

}
