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
import object.FixedMass;
import object.Mass;
import object.Spring;
import org.jbox2d.common.Vec2;
import environment.Force;
import environment.WallRepulsion;


/**
 * Springies class. This class initializes the display, calls parser to generate objects, and
 * updates the forces
 * on the objects in each frame. This class also manages the toggling of environment forces and
 * mouse clicking.
 * 
 * @author Yuhua Mai, Susan Zhang
 * 
 */
@SuppressWarnings("serial")
public class Springies extends JGEngine
{
    private static final JFileChooser INPUT_CHOOSER =
            new JFileChooser(System.getProperties().getProperty("user.dir"));

    private Assembly assembly;
    private Model model;
    private Model environmentModel;
    private Parser parser;

    // handle wall
    private static final int changeWallThicknessValue = 10;
    private double wallWidth;
    private double wallHeight;
    private double wallThickness;
    private List<FixedMass> walls;

    // handle mouse event
    private boolean mousePressed = false;
    private boolean massCreated = false;
    private Mass mouseMass;
    private Spring mouseSpring;

    /**
     * Constructor for Springies class that sets window size
     */
    public Springies ()
    {
        initEngine((int) (Common.WIDTH), (int) (Common.HEIGHT));
    }

    /**
     * Initializes canvas of display
     * 
     */
    @Override
    public void initCanvas ()
    {
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
    public void initGame ()
    {
        assembly = new Assembly();
        parser = new Parser();

        walls = new ArrayList<FixedMass>();

        wallThickness = Common.WALL_THICKNESS;

        setFrameRate(60, 2);

        WorldManager.initWorld(this);
        WorldManager.getWorld().setGravity(new Vec2(0.0f, 0.0f));

        loadModel();
        makeWalls();

    }

    /**
     * Sets the four walls of the display. The wall IDs start at the top wall and go clockwise.
     */
    public void makeWalls () {
        wallWidth = displayWidth() - Common.WALL_MARGIN * 2 + Common.WALL_THICKNESS;
        wallHeight = displayHeight() - Common.WALL_MARGIN * 2 + Common.WALL_THICKNESS;
        walls.add(new FixedMass("1", Common.WALL_CID, wallWidth, Common.WALL_THICKNESS,
                                displayWidth() / 2, Common.WALL_MARGIN));
        walls.add(new FixedMass("3", Common.WALL_CID, wallWidth, Common.WALL_THICKNESS,
                                displayWidth() / 2, displayHeight() - Common.WALL_MARGIN));
        walls.add(new FixedMass("4", Common.WALL_CID, Common.WALL_THICKNESS, wallHeight,
                                Common.WALL_MARGIN, displayHeight() / 2));
        walls.add(new FixedMass("2", Common.WALL_CID, Common.WALL_THICKNESS, wallHeight,
                                displayWidth() - Common.WALL_MARGIN, displayHeight() / 2));
    }

    /**
     * Loads XML files into assembly by calling parser. Allows for loading of multiple files.
     */
    public void loadModel () {
        int n = JOptionPane.YES_OPTION;
        JOptionPane.showMessageDialog(this, "Please load a XML file.");

        while (n == JOptionPane.YES_OPTION) {
            int loadObject = INPUT_CHOOSER.showOpenDialog(null);
            if (loadObject == JFileChooser.APPROVE_OPTION) {
                if (INPUT_CHOOSER.getSelectedFile().getName().equals("environment.xml")) {
                    environmentModel = new Model();
                    parser.loadModel(environmentModel, INPUT_CHOOSER.getSelectedFile());
                }
                else {
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
    public void doFrame ()
    {
        WorldManager.getWorld().step(1f, 1);

        moveObjects();

        checkCollision();
        applyForce();

        handleKeyboardEvent();
        handleMouseEvent();
    }

    /**
     * Checks to see if two objects have collided and calls JGObject's built in checkCollision
     * method
     */
    private void checkCollision () {
        checkCollision(Common.WALL_CID, Common.MASS_CID);
    }

    /**
     * Applies force to all the models in the assembly
     */
    private void applyForce () {
        for (Model model : assembly.getModels()) {
            List<PhysicalObject> objects = model.getObjects();
            List<Force> forces = model.getForces();

            for (Force force : forces) {
                force.setForce(objects);
            }

            if (environmentModel != null) {
                List<Force> environmentForces = environmentModel.getForces();
                for (Force environmentForce : environmentForces) {
                    environmentForce.setForce(objects);

                }
            }
        }
    }

    /**
     * Detects and handles keyboard input accordingly.
     */
    private void handleKeyboardEvent () {
        if (getKey('N')) {
            loadModel();
            clearKey('N');
        }
        else if (getKey('C')) {
            clearAllObjects();
            clearKey('C');
        }

        if (getKey('G')) {
            toggleForce(Common.GRAVITY_CLASS_NAME);
            clearKey('G');
        }
        else if (getKey('V')) {
            toggleForce(Common.VISCOSITY_CLASS_NAME);
            clearKey('V');
        }
        else if (getKey('M')) {
            toggleForce(Common.CENTEROFMASS_CLASS_NAME);
            clearKey('M');
        }

        if (getKey('1')) {
            toggleForce(Common.WALLREPULSION1_CLASS_NAME);
            clearKey('1');
        }
        else if (getKey('2')) {
            toggleForce(Common.WALLREPULSION2_CLASS_NAME);
            clearKey('2');
        }
        else if (getKey('3')) {
            toggleForce(Common.WALLREPULSION3_CLASS_NAME);
            clearKey('3');
        }
        else if (getKey('4')) {
            toggleForce(Common.WALLREPULSION4_CLASS_NAME);
            clearKey('4');
        }

        dbgShowBoundingBox(getKey('B'));
        if (getKey(KeyUp)) {
            changeWallThickness(changeWallThicknessValue);
            clearKey(KeyUp);
        }
        else if (getKey(KeyDown)) {
            changeWallThickness(-changeWallThicknessValue);
            clearKey(KeyDown);
        }

        if (getKey('D')) {
            assembly.setRandomColor();
            clearKey('D');
        }
    }

    /**
     * Changes the thickness of walls on display. Also changes the wall repulsion forces
     * respectively.
     * The maximum and minimum wall thicknesses are constrained by constants.
     * 
     * @param changeWallThicknessValue how much
     */
    private void changeWallThickness (int changeWallThicknessValue) {
        if (wallThickness > Common.MAX_THICKNESS) {
            changeWallThicknessValue = -5;
        }
        else if (wallThickness < Common.MIN_THICKNESS) {
            changeWallThicknessValue = 5;
        }

        wallThickness += changeWallThicknessValue;

        for (Model model : assembly.getModels()) {
            List<PhysicalObject> objects = model.getObjects();
            for (PhysicalObject object : objects) {
                if (object instanceof Mass) {
                    ((Mass) object).changePos(changeWallThicknessValue);
                }
            }

            for (FixedMass fixedMass : walls) {
                fixedMass.changeThickness(changeWallThicknessValue);
            }

            List<Force> forces = model.getForces();
            for (Force force : forces) {
                if (force instanceof WallRepulsion) {
                    ((WallRepulsion) force).incrementWallThickness(changeWallThicknessValue);
                }
            }
        }

        if (environmentModel != null) {
            for (Force force : environmentModel.getForces()) {
                if (force instanceof WallRepulsion) {
                    ((WallRepulsion) force).incrementWallThickness(changeWallThicknessValue);
                }
            }
        }

    }

    /**
     * Clears all objects from display window. There is also a popup to check if the user
     * really wants to clear all the objects.
     */
    private void clearAllObjects () {
        int n = JOptionPane.showConfirmDialog(
                                              this,
                                              "Are you sure to clear all objects?",
                                              "Clear Objects",
                                              JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            for (Model model : assembly.getModels()) {
                model.clear();
            }

            assembly.clear();

        }

    }

    /**
     * Goes through list of all forces in object models or environment model to toggle
     * type of force given in parameter
     * 
     * @param className Type of force to be toggled
     */
    private void toggleForce (String className) {

        for (Model model : assembly.getModels()) {
            List<Force> forces = model.getForces();
            for (Force force : forces) {
                if (force.getClass().getSimpleName().equals(className)) {
                    force.toggleValid();
                }
            }
        }

        if (environmentModel != null) {
            List<Force> environmentForces = environmentModel.getForces();
            for (Force force : environmentForces) {
                if (force.getClass().getSimpleName().equals(className)) {
                    force.toggleValid();
                }
            }
        }
    }

    /**
     * Detects if mouse is clicked. When mouse is held down, a mass is created at the location that
     * the mouse clicks and a spring will extend to the nearest mass in any model. When the mouse is
     * lifted, both of these objects will disappear.
     */
    private void handleMouseEvent () {
        if (getMouseButton(1)) { // left click pressed
            System.out.println(" " + getMouseX() + " " + getMouseY());

            if (!massCreated) {
                Mass nearestMass = assembly.calculateNearestMass(getMouseX(), getMouseY());

                mouseMass = new Mass("mouse", Common.MASS_CID, 1, getMouseX(), getMouseY(), 0, 0);
                mouseSpring =
                        new Spring("mouseSpring", Common.SPRING_CID, JGColor.white, nearestMass,
                                   mouseMass, 0, 1);
                massCreated = true;
            }

            mouseMass.setPos(getMouseX(), getMouseY());

            mousePressed = true;
        }
        else if (!getMouseButton(1) && mousePressed) { // left click release
            System.out.println("Mouse released");

            mouseMass.remove();
            mouseSpring.remove();

            mousePressed = false;
            massCreated = false;
        }
    }

    @Override
    public void paintFrame ()
    {
        super.paintFrame();

        int modelIndex = 0;
        int infoOffset = 10;
        for (Model model : assembly.getModels()) {
            List<Force> forces = model.getForces();
            drawString("Model" + modelIndex++, 15, infoOffset += 20, -1,
                       new JGFont("arial", 0, 15), JGColor.red);
            for (Force force : forces) {
                if (force.isValid()) {
                    drawString(force.getClass().getSimpleName() + " ON", 15, infoOffset += 20, -1,
                               new JGFont("arial", 0, 15), JGColor.white);
                }
                else {
                    drawString(force.getClass().getSimpleName() + " OFF", 15, infoOffset += 20, -1,
                               new JGFont("arial", 0, 15), JGColor.white);
                }
            }
        }

        if (environmentModel != null) {
            List<Force> environmentForces = environmentModel.getForces();
            drawString("EnvironmentModel", 15, infoOffset += 20, -1, new JGFont("arial", 0, 15),
                       JGColor.red);
            for (Force force : environmentForces) {
                if (force.isValid()) {
                    drawString(force.getClass().getSimpleName() + " ON", 15, infoOffset += 20, -1,
                               new JGFont("arial", 0, 15), JGColor.white);
                }
                else {
                    drawString(force.getClass().getSimpleName() + " OFF", 15, infoOffset += 20, -1,
                               new JGFont("arial", 0, 15), JGColor.white);
                }
            }
        }

    }

}
