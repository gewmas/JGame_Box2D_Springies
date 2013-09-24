package parser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jboxGlue.PhysicalObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import environment.Force;
import springies.Common;
import springies.Model;


/**
 * Reads in XML Files and adds objects and forces to Model
 * 
 */

public class Parser {
    Map<String, PhysicalObject> myMasses = new HashMap<String, PhysicalObject>();
    PhysicalObject physicalObject = null;
    Force force = null;
//    CreateForce createForce;

    /**
     * parse selectedFile and create new object to model
     * 
     * @param model created by Springies
     * @param selectedFile the opened XML file
     */
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

    /**
     * traverse Nodes in the XML file
     * 
     * @param nodeList nodeList of the XML file
     * @param model model to store new created object
     */
    private void traverseNote (NodeList nodeList, Model model) {
        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                // get node name and value
                // System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");

                if (tempNode.hasAttributes()) {
                    // get attributes names and values
                    NamedNodeMap nodeMap = tempNode.getAttributes();

                    if (Common.MASS_KEYWORD.equals(tempNode.getNodeName())) {
                        physicalObject = new CreateMass(myMasses).createObject(nodeMap);
                    }
                    else if (Common.FIXEDMASS_KEYWORD.equals(tempNode.getNodeName())) {
                        physicalObject = new CreateFixedMass(myMasses).createObject(nodeMap);
                    }
                    else if (Common.MUSCLE_KEYWORD.equals(tempNode.getNodeName())) {
                        physicalObject = new CreateMuscle(myMasses).createObject(nodeMap);
                    }
                    else if (Common.SPRING_KEYWORD.equals(tempNode.getNodeName())) {
                        physicalObject = new CreateSpring(myMasses).createObject(nodeMap);
                    }
                    else if (Common.GRAVITY_KEYWORD.equals(tempNode.getNodeName())) {
                        force = (new CreateGravity()).createForce(nodeMap);
                    }
                    else if (Common.VISCOSITY_KEYWORD.equals(tempNode.getNodeName())) {
                        force = (new CreateViscosity()).createForce(nodeMap);
                    }
                    else if (Common.CENTERMASS_KEYWORD.equals(tempNode.getNodeName())) {
                        force = (new CreateCenterOfMass()).createForce(nodeMap);
                    }
                    else if (Common.WALL_KEYWORD.equals(tempNode.getNodeName())) {
                        force = (new CreateWallRepulsion()).createForce(nodeMap);
                    }
                    
                    if(physicalObject != null){
                        model.add(physicalObject);
                        physicalObject = null;
                    }
                    
                    if(force != null){
                        model.add(force);
                        force = null;
                    }
                }
               
                if (tempNode.hasChildNodes()) {
                    // loop again if has child nodes
                    traverseNote(tempNode.getChildNodes(), model);
                }

            }

        }
    }

}
