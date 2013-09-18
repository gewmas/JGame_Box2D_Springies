package springies;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jboxGlue.PhysicalObject;
import jgame.JGColor;
import object.FixedMass;
import object.Mass;
import object.Muscle;
import object.Spring;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import environment.CenterOfMass;
import environment.Gravity;
import environment.Viscosity;
import environment.WallRepulsion;
import environment.WallRepulsion1;
import environment.WallRepulsion2;
import environment.WallRepulsion3;
import environment.WallRepulsion4;
/**  Reads in XML Files and adds objects and forces to Model
 * 
 */

public class Parser {
    //mass IDs
    
    
    Map<String, PhysicalObject> myMasses = new HashMap<String, PhysicalObject>();

    //mass
    private String id;
    private double vx = 0;
    private double vy = 0;
    private double mass = Common.DEFAULT_MASS;
    private double x = 0;
    private double y = 0;

    //spring
    private String id1;
    private String id2;
    private double constant = Common.DEFAULT_K;
    private double restlength = 0;

    //gravity
    private double amplitude = 0;
    private double direction=0;

    //viscosity, center mass, wall
    private double magnitude = 0;

    //wall
    private double exponent = 0;
    private String wallId;

    public void loadModel (Model model, File selectedFile) {
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document doc = null;
        try {
            doc = dBuilder.parse(selectedFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (SAXException e) {
            e.printStackTrace();
        }

        // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        if (doc.hasChildNodes()) {
            traverseNote(doc.getChildNodes(), model);
        }

    }

    private void traverseNote(NodeList nodeList, Model model) {
        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                // get node name and value
                //                System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");

                if (tempNode.hasAttributes()) {
                    // get attributes names and values
                    NamedNodeMap nodeMap = tempNode.getAttributes();

                    if (Common.MASS_KEYWORD.equals(tempNode.getNodeName())) {
                        model.add(massCommand(nodeMap));
                    }else if (Common.FIXEDMASS_KEYWORD.equals(tempNode.getNodeName())){
                        model.add(fixedMassCommand(nodeMap));
                    }else if (Common.MUSCLE_KEYWORD.equals(tempNode.getNodeName())) {
                        model.add(muscleCommand(nodeMap));
                    }else if (Common.SPRING_KEYWORD.equals(tempNode.getNodeName())) {
                        model.add(springCommand(nodeMap));
                    }else if(Common.GRAVITY_KEYWORD.equals(tempNode.getNodeName())){
                        model.add(gravityCommand(nodeMap));
                    }else if(Common.VISCOSITY_KEYWORD.equals(tempNode.getNodeName())){
                        model.add(viscosityCommand(nodeMap));
                    }else if(Common.CENTERMASS_KEYWORD.equals(tempNode.getNodeName())){
                        model.add(centerMassCommand(nodeMap));
                    }else if(Common.WALL_KEYWORD.equals(tempNode.getNodeName())){
                        model.add(wallCommand(nodeMap));
                    }
                }

                //                 System.out.println("Node Value =" + tempNode.getTextContent());

                if (tempNode.hasChildNodes()) {
                    // loop again if has child nodes
                    traverseNote(tempNode.getChildNodes(), model);
                }

                //                 System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");

            }

        }
    }

    private Mass massCommand (NamedNodeMap nodeMap) {
        mass=Common.DEFAULT_MASS;
        for (int i = 0; i < nodeMap.getLength(); i++) {
            Node node = nodeMap.item(i);
            String nodeName = node.getNodeName();
            String nodeValue = node.getNodeValue();

            if (nodeName.equals(Common.MASS_ID)) {
                id = nodeValue;
            }
            else if (nodeName.equals(Common.MASS_MASS)) {
                mass = Double.parseDouble(nodeValue);
            }
            else if (nodeName.equals(Common.MASS_VX)) {
                vx = Double.parseDouble(nodeValue);
            }
            else if (nodeName.equals(Common.MASS_VY)) {
                vy = Double.parseDouble(nodeValue);
            }
            else if (nodeName.equals(Common.MASS_X)) {
                x = Double.parseDouble(nodeValue);
            }
            else if (nodeName.equals(Common.MASS_Y)) {
                y = Double.parseDouble(nodeValue);
            }

            // System.out.println(node.getNodeName() + " " + node.getNodeValue());
        }

//               System.out.println(id + " " + mass + " " + (float)vx + " " + vy + " " + x + " " + y);

        Mass result = new Mass(id, Common.MASS_CID, mass, x, y, vx, vy);
        myMasses.put(id, result);

        return result;
    }
    
    private FixedMass fixedMassCommand (NamedNodeMap nodeMap) {
        for (int i = 0; i < nodeMap.getLength(); i++) {
            Node node = nodeMap.item(i);
            String nodeName = node.getNodeName();
            String nodeValue = node.getNodeValue();

            if (nodeName.equals(Common.MASS_ID)) {
                id = nodeValue;
            }
            else if (nodeName.equals(Common.MASS_X)) {
                x = Double.parseDouble(nodeValue);
            }
            else if (nodeName.equals(Common.MASS_Y)) {
                y = Double.parseDouble(nodeValue);
            }

            // System.out.println(node.getNodeName() + " " + node.getNodeValue());
        }

        //        System.out.println(id + " " + mass + " " + (float)vx + " " + vy + " " + x + " " + y);

        FixedMass result = new FixedMass(id, Common.FIXEDMASS_CID, x, y);
        myMasses.put(id, result);

        return result;
    }

    private Spring springCommand (NamedNodeMap nodeMap) {
        constant=Common.DEFAULT_K;
        restlength=0;
        for (int i = 0; i < nodeMap.getLength(); i++) {
            Node node = nodeMap.item(i);
            String nodeName = node.getNodeName();
            String nodeValue = node.getNodeValue();

            if (nodeName.equals(Common.SPRING_A)) {
                id1 = nodeValue;
            }
            else if (nodeName.equals(Common.SPRING_B)) {
                id2 = nodeValue;
            }
            else if (nodeName.equals(Common.SPRING_CONSTANT)) {
                constant = Double.parseDouble(nodeValue);
            }
            else if (nodeName.equals(Common.SPRING_RESTLENGTH)) {
                restlength = Double.parseDouble(nodeValue);
            }

            // System.out.println(node.getNodeName() + " " + node.getNodeValue());
        }
        System.out.println(id1 + " " + id2 + " " + constant + " " + restlength);

        PhysicalObject m1 = myMasses.get(id1);
        PhysicalObject m2 = myMasses.get(id2);

        return new Spring("spring", Common.SPRING_CID, JGColor.cyan, m1, m2, restlength, constant);
    }

    private Muscle muscleCommand (NamedNodeMap nodeMap) {
        restlength=0;
        constant=Common.DEFAULT_K;
        for (int i = 0; i < nodeMap.getLength(); i++) {
 
            Node node = nodeMap.item(i);
            String nodeName = node.getNodeName();
            String nodeValue = node.getNodeValue();

            if (nodeName.equals(Common.SPRING_A)) {
                id1 = nodeValue;
            }
            else if (nodeName.equals(Common.SPRING_B)) {
                id2 = nodeValue;
            }
            else if (nodeName.equals(Common.SPRING_RESTLENGTH)) {
                restlength = Double.parseDouble(nodeValue);
            }
            else if (nodeName.equals(Common.SPRING_CONSTANT)) {
                constant = Double.parseDouble(nodeValue);
            }
            else if (nodeName.equals(Common.MUSCLE_AMPLITUDE)) {
                amplitude = Double.parseDouble(nodeValue);
            }

        }

        PhysicalObject m1 = myMasses.get(id1);
        PhysicalObject m2 = myMasses.get(id2);

        return new Muscle("muscle", Common.SPRING_CID, JGColor.cyan, m1, m2, restlength, constant, amplitude);
    }


    private Gravity gravityCommand(NamedNodeMap nodeMap){
        for (int i = 0; i < nodeMap.getLength(); i++) {
            Node node = nodeMap.item(i);
            String nodeName = node.getNodeName();
            String nodeValue = node.getNodeValue();

            if (nodeName.equals(Common.MAGNITUDE)) {
                magnitude= Double.parseDouble(nodeValue);     
            }

            if (nodeName.equals(Common.GRAVITY_DIRECTION)) {
                direction= Double.parseDouble(nodeValue);     
            }

            System.out.println(nodeName + " " + nodeValue);
        }

        return new Gravity(direction, magnitude);


    }

    private Viscosity viscosityCommand(NamedNodeMap nodeMap){
        for (int i = 0; i < nodeMap.getLength(); i++) {
            Node node = nodeMap.item(i);
            String nodeName = node.getNodeName();
            String nodeValue = node.getNodeValue();

            if (nodeName.equals(Common.MAGNITUDE)) {
                magnitude = Double.parseDouble(nodeValue);
            }

            System.out.println(nodeName + " " + nodeValue);
        }

        return new Viscosity(magnitude);
    }

    private CenterOfMass centerMassCommand(NamedNodeMap nodeMap){
        for (int i = 0; i < nodeMap.getLength(); i++) {
            Node node = nodeMap.item(i);
            String nodeName = node.getNodeName();
            String nodeValue = node.getNodeValue();

            if (nodeName.equals(Common.MAGNITUDE)){
                magnitude = Double.parseDouble(nodeValue);
            }

            if (nodeName.equals(Common.EXPONENT)){
                exponent = Double.parseDouble(nodeValue);
            }

            System.out.println(nodeName + " " + nodeValue);
        }

        return new CenterOfMass(magnitude, exponent);
    }

    private WallRepulsion wallCommand(NamedNodeMap nodeMap){
        for (int i = 0; i < nodeMap.getLength(); i++) {
            Node node = nodeMap.item(i);
            String nodeName = node.getNodeName();
            String nodeValue = node.getNodeValue();

            if (nodeName.equals(Common.MASS_ID)){
                wallId = nodeValue;
            }

            if (nodeName.equals(Common.MAGNITUDE)){
                magnitude = Double.parseDouble(nodeValue);
            }

            if (nodeName.equals(Common.EXPONENT)){
                exponent = Double.parseDouble(nodeValue);
            }       

            System.out.println(nodeName + " " + nodeValue);
        }
        
        
        if (wallId.equals("1")){
            return new WallRepulsion1(wallId, magnitude, exponent);
        }else if (wallId.equals("2")){
            return new WallRepulsion2(wallId, magnitude, exponent);
        }else if (wallId.equals("3")){
            return new WallRepulsion3(wallId, magnitude, exponent);
        }else{
            return new WallRepulsion4(wallId, magnitude, exponent);
        }

    }
}
