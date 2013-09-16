package springies;

import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import jboxGlue.PhysicalObject;
import jboxGlue.WorldManager;
import jgame.platform.JGEngine;
import object.FixedMass;
import org.jbox2d.common.Vec2;
import environment.Force;

@SuppressWarnings( "serial" )
public class Springies extends JGEngine
{
    private static final JFileChooser INPUT_CHOOSER = 
            new JFileChooser(System.getProperties().getProperty("user.dir"));
    Model model;
    
    private double wallWidth;
    private double wallHeight;

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



    @Override
    public void doFrame( )
    {
        WorldManager.getWorld().step( 1f, 1 );
        
        moveObjects();

        checkCollision();
        applyForce();
//        cheatKeys();
    }

    private void checkCollision () {
        checkCollision(Common.WALL_CID,Common.MASS_CID);
    }

    private void applyForce () {
        List<PhysicalObject> objects = model.getObjects();
        List<Force> forces = model.getForces();
    
        for(Force force : forces){
            force.setForce(objects);
        }
    }
    
    /*
    private void cheatKeys(){
        if(getKey('N')) {
            List<FixedMass> fixedMasses = model.getFixedMasses();
            for(FixedMass fixedMass : fixedMasses){
                if(fixedMass.getId().equals("1") || fixedMass.getId().equals("3")){
                    fixedMass.increaseHeight();
                }
            }
            clearKey('N');
        }else if(getKey('M')){
            List<FixedMass> fixedMasses = model.getFixedMasses();
            for(FixedMass fixedMass : fixedMasses){
                if(fixedMass.getId().equals("2") || fixedMass.getId().equals("4")){
                    fixedMass.increaseWidth();
                }
            }
            clearKey('M');
        }
    }*/

    @Override
    public void paintFrame( )
    {
        // nothing to do
        // the objects paint themselves
    }

    public void loadModel(){
        Parser parser = new Parser();
        int n = JOptionPane.YES_OPTION;

        if(model == null) model = new Model();
        
        JOptionPane.showMessageDialog(this, "Please load a XML file.");
        
        while(n == JOptionPane.YES_OPTION){
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
}
