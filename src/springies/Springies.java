package springies;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import jboxGlue.PhysicalObject;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.JGFont;
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
    private Model environmentModel;
    private Parser parser;
    
    private double wallWidth;
    private double wallHeight;
    
    private boolean mousePressed = false;
    private boolean massCreated = false;
    
    private Mass mouseMass;
    private Spring mouseSpring;
    
    private double wallThickness;
    
    List<FixedMass> walls;
    
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
        
        walls = new ArrayList<FixedMass>();
        
        this.wallThickness = Common.WALL_THICKNESS;
        
        setFrameRate( 60, 2 );

        WorldManager.initWorld( this );
        WorldManager.getWorld().setGravity( new Vec2( 0.0f, 0.0f ) );

        loadModel();
        
        //add walls up-1 right-2 down-3 left-4
        wallWidth = displayWidth() - Common.WALL_MARGIN*2 + Common.WALL_THICKNESS;
        wallHeight = displayHeight() - Common.WALL_MARGIN*2 + Common.WALL_THICKNESS;
        walls.add(new FixedMass("1", Common.WALL_CID, wallWidth,  Common.WALL_THICKNESS, displayWidth()/2, Common.WALL_MARGIN));
        walls.add(new FixedMass("3", Common.WALL_CID, wallWidth,  Common.WALL_THICKNESS, displayWidth()/2, displayHeight() - Common.WALL_MARGIN));
        walls.add(new FixedMass("4", Common.WALL_CID, Common.WALL_THICKNESS, wallHeight, Common.WALL_MARGIN, displayHeight()/2));
        walls.add(new FixedMass("2", Common.WALL_CID, Common.WALL_THICKNESS, wallHeight, displayWidth() - Common.WALL_MARGIN, displayHeight()/2));
    }



    public void loadModel(){
        int n = JOptionPane.YES_OPTION;
        
        JOptionPane.showMessageDialog(this, "Please load a XML file.");
        
        while(n == JOptionPane.YES_OPTION){
            int loadObject = INPUT_CHOOSER.showOpenDialog(null);
            if (loadObject == JFileChooser.APPROVE_OPTION) {
//                System.out.println(INPUT_CHOOSER.getSelectedFile().getName());
                if(INPUT_CHOOSER.getSelectedFile().getName().equals("environment.xml")){
//                    System.out.println("environment!!");
                    environmentModel = new Model();
                    parser.loadModel(environmentModel, INPUT_CHOOSER.getSelectedFile());
                }else{
                    model = new Model();
                    assembly.addModel(model);
                    parser.loadModel(model, INPUT_CHOOSER.getSelectedFile());
                }
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
                System.out.println("Instance of Gravity!" + force.getClass().getSimpleName());
            }
            
            if(environmentModel != null){
                List<Force> environmentForces = environmentModel.getForces();
                for(Force environmentForce:environmentForces){
                    environmentForce.setForce(objects);
                    System.out.println("Environment Instance of Force!" + environmentForce.getClass().getSimpleName());

                }
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
            changeWallThickness(changeWallThicknessValue);
                  
            clearKey(KeyUp);
            
        }else if(getKey(KeyDown)){
            changeWallThickness(-changeWallThicknessValue);
            
            clearKey(KeyDown);
        }
        
        if(getKey('D')){
            assembly.setColor();
            clearKey('D');
        }
    }

    private void changeWallThickness (int changeWallThicknessValue) {
        if(wallThickness > Common.MAX_THICKNESS){
            changeWallThicknessValue = -5;
        }else if(wallThickness < Common.MIN_THICKNESS){
            changeWallThicknessValue = 5;
        }
        
        wallThickness+=changeWallThicknessValue;
        System.out.println(wallThickness);
        for (Model model: assembly.getModels()){
            List<PhysicalObject> ojbects = model.getObjects();
            for(PhysicalObject object:ojbects){
                if(object instanceof Mass){
                    ((Mass)object).changePos(changeWallThicknessValue);
                }
            }
            
//            List<FixedMass> fixedMasses = model.getFixedMasses();
            for(FixedMass fixedMass : walls){
                    fixedMass.changeThickness(changeWallThicknessValue);
            }
            
            List<Force> forces = model.getForces();
            for (Force force : forces){
                if (force instanceof WallRepulsion){
                    ((WallRepulsion) force).incrementWallThickness((double) changeWallThicknessValue);
                }
            }
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
            
            assembly.clear();
            
        }
        
    }
    
    private void toggleForce(String className){
        int i = 0;
        for (Model model: assembly.getModels()){
            List<Force> forces = model.getForces();
            for(Force force:forces){
                if(force.getClass().getSimpleName().equals(className)){
                  System.out.print("Toggle force in model " + i++ + " ");
                    force.toggleValid();       
                }
            }
        }
        
        if(environmentModel != null){
            List<Force> environmentForces = environmentModel.getForces();
            for(Force force:environmentForces){
                if(force.getClass().getSimpleName().equals(className)){
                    force.toggleValid();       
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
        super.paintFrame();

        int modelIndex = 0;
        int infoOffset = 10;
        for (Model model: assembly.getModels()){
            List<Force> forces = model.getForces();
            drawString("Model" + modelIndex++, 15, infoOffset+=20, -1, new JGFont("arial",0,15), JGColor.red);
            for(Force force:forces){
                if(force.isValid()){
                    drawString(force.getClass().getSimpleName() + " ON", 15, infoOffset+=20, -1, new JGFont("arial",0,15), JGColor.white);
                }else{
                    drawString(force.getClass().getSimpleName() + " OFF", 15, infoOffset+=20, -1, new JGFont("arial",0,15), JGColor.white);
                }
            }
        }
        
        if(environmentModel != null){
            List<Force> environmentForces = environmentModel.getForces();
            drawString("EnvironmentModel", 15, infoOffset+=20, -1, new JGFont("arial",0,15), JGColor.red);
            for(Force force:environmentForces){
                if(force.isValid()){
                    drawString(force.getClass().getSimpleName() + " ON", 15, infoOffset+=20, -1, new JGFont("arial",0,15), JGColor.white);
                }else{
                    drawString(force.getClass().getSimpleName() + " OFF", 15, infoOffset+=20, -1, new JGFont("arial",0,15), JGColor.white);
                }
            }
        }
        
        
    }

}
