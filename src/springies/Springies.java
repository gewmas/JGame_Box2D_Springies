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

@SuppressWarnings( "serial" )
public class Springies extends JGEngine
{
    private static final int changeWallThicknessValue = 10;
    private static final JFileChooser INPUT_CHOOSER = 
            new JFileChooser(System.getProperties().getProperty("user.dir"));
    private Assembly assembly;
    private Model model;
    private Parser parser;
    
    private double wallWidth;
    private double wallHeight;
    
    private boolean mousePressed = false;
    private boolean massCreated = false;
    
    private Mass mouseMass;
    private Spring mouseSpring;
    
    public Springies( )
    {
        // set the window size
        initEngine( (int)(Common.WIDTH), (int)(Common.HEIGHT));
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
//        model = new Model();
        assembly = new Assembly();
        parser = new Parser();
        
//        this.addMouseListener(this.getMouseListeners());
        
        
        setFrameRate( 60, 2 );

        WorldManager.initWorld( this );
        WorldManager.getWorld().setGravity( new Vec2( 0.0f, 0.0f ) );

        loadModel();
        
        model = new Model();
        assembly.addModel(model);
        //add walls up-1 right-2 down-3 left-4
        wallWidth = displayWidth() - Common.WALL_MARGIN*2 + Common.WALL_THICKNESS;
        wallHeight = displayHeight() - Common.WALL_MARGIN*2 + Common.WALL_THICKNESS;
        model.add(new FixedMass("1", Common.WALL_CID, wallWidth,  Common.WALL_THICKNESS, displayWidth()/2, Common.WALL_MARGIN));
        model.add(new FixedMass("3", Common.WALL_CID, wallWidth,  Common.WALL_THICKNESS, displayWidth()/2, displayHeight() - Common.WALL_MARGIN));
        model.add(new FixedMass("4", Common.WALL_CID, Common.WALL_THICKNESS, wallHeight, Common.WALL_MARGIN, displayHeight()/2));
        model.add(new FixedMass("2", Common.WALL_CID, Common.WALL_THICKNESS, wallHeight, displayWidth() - Common.WALL_MARGIN, displayHeight()/2));
    }



    public void loadModel(){
        int n = JOptionPane.YES_OPTION;
        
        JOptionPane.showMessageDialog(this, "Please load a XML file.");
        
        while(n == JOptionPane.YES_OPTION){
            int loadObject = INPUT_CHOOSER.showOpenDialog(null);
            if (loadObject == JFileChooser.APPROVE_OPTION) {
                model = new Model();
                assembly.addModel(model);
//                parser = new Parser();
                
                parser.loadModel(model, INPUT_CHOOSER.getSelectedFile());
            }
    
            n = JOptionPane.showConfirmDialog(
                                              this,
                                              "Would you like to add more XML file?",
                                              "LoadModel?",
                                              JOptionPane.YES_NO_OPTION);
    
        }
    }

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

    private void checkCollision () {
        checkCollision(Common.WALL_CID,Common.MASS_CID);
    }

    private void applyForce () {
        for (Model model: assembly.getModels()){
            List<PhysicalObject> objects = model.getObjects();
            List<Force> forces = model.getForces();

            for(Force force : forces){
                force.setForce(objects);
                //            System.out.println("Instance of Gravity!" + force.getClass().getSimpleName());
            }
        }
    }
        
        
    
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
        
        dbgShowBoundingBox(getKey('B'));
        
        if(getKey(KeyUp)){
            List<FixedMass> fixedMasses = model.getFixedMasses();
            for(FixedMass fixedMass : fixedMasses){
//                if(fixedMass.getId().equals('1')){ //....Check Common.Max_ThickNess..Same For KeyDown
                    fixedMass.changeThickness(changeWallThicknessValue);
                    fixedMass.setWallBox();
//                }
            }
            
            List<Force> forces = model.getForces();
            
            for (Force force : forces){
                if (force instanceof WallRepulsion){
                    ((WallRepulsion) force).incrementWallThickness((double) changeWallThicknessValue);
                }
            }
                  
            clearKey(KeyUp);
            
        }else if(getKey(KeyDown)){
            List<FixedMass> fixedMasses = model.getFixedMasses();
            for(FixedMass fixedMass : fixedMasses){
                fixedMass.changeThickness(-changeWallThicknessValue);
                fixedMass.setWallBox();
            }

            List<Force> forces = model.getForces();

            for (Force force : forces){
                if (force instanceof WallRepulsion){
                    ((WallRepulsion) force).incrementWallThickness((double) -changeWallThicknessValue);
                }
            }
            
            clearKey(KeyDown);
        }
        
        if(getKey('D')){
            assembly.setColor();
            clearKey('D');
        }
    }
    
    private void clearAllObjects () {
        int n = JOptionPane.showConfirmDialog(
                                              this,
                                              "Are you sure to clear all objects?",
                                              "Clear Ojbects",
                                              JOptionPane.YES_NO_OPTION);
        if(n == JOptionPane.YES_OPTION){
            for (Model model: assembly.getModels()){
                model.clear();      
            }
        }
        
    }
    
    private void toggleForce(String className){
        int i = 0;
        for (Model model: assembly.getModels()){
            System.out.println("!!!!!!!!!!!!model + " + i++);
            List<Force> forces = model.getForces();
            for(Force force:forces){
                if(force.getClass().getSimpleName().equals(className)){
                    force.toggleValid();       
                    //                System.out.println("Calling toggleValid! " + force.getClass().getSimpleName());
                }
            }
        }
    }

    private void handleMouseEvent(){
        if(this.getMouseButton(1)){ //left click pressed
            System.out.println(" " + this.getMouseX() + " " + this.getMouseY());
            
            if(!massCreated){
                Mass nearestMass = assembly.calculateNearestMass(this.getMouseX(), this.getMouseY());
                System.out.println("NearestMass position: " + nearestMass.x + " " + nearestMass.y);

                mouseMass= new Mass("mouse", Common.MASS_CID, 1, this.getMouseX(), this.getMouseY(), 0, 0);
                System.out.println("1");
                mouseSpring=new Spring("mouseSpring", Common.SPRING_CID, JGColor.white, nearestMass, mouseMass, 0, 1);
                System.out.println("2");
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

    @Override
    public void paintFrame( )
    {
        // nothing to do
        // the objects paint themselves
    }

}
