package springies;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jgame.JGColor;
import object.Mass;
import object.Spring;

import org.jbox2d.common.Vec2;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Parser {
	
    private static final String MASS_KEYWORD = "mass";
    private static final String FIXED_KEYWORD = "fixed";
    private static final String SPRING_KEYWORD = "spring";
    private static final String WALL_KEYWORD = "wall";
    private static final String GRAVITY_KEYWORD = "gravity";
    private static final String VISCOSITY_KEYWORD = "viscosity";
    private static final String CENTERMASS_KEYWORD = "centermass";
    
    private static final String MASS_ID = "id";
    private static final String MASS_MASS = "mass";
    private static final String MASS_VX = "vx";
    private static final String MASS_VY = "vy";
    private static final String MASS_X = "x";
    private static final String MASS_Y = "y";
    
    private static final String SPRING_A = "a";
    private static final String SPRING_B = "b";
    private static final String SPRING_CONSTANT = "constant";
    private static final String SPRING_RESTLENGTH = "restlength";
    private static final String MUSCLE_AMPLITUDE = "amplitude";

    // mass IDs
    Map<String, Mass> myMasses = new HashMap<String, Mass>();
    
	private String id;
	private double vx = 0;
	private double vy = 0;
	private double mass = 0;
	private double x = 0;
	private double y = 0;
	
	private String id1;
	private String id2;
	private double constant = 0;
	private double restlength = 0;
	private double amplitude = 0;
    
	public void loadModel(Model model, File selectedFile) {
		
		// TODO Auto-generated method stub
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		Document doc = null;
		try {
			doc = dBuilder.parse(selectedFile);
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		if (doc.hasChildNodes()) {
				printNote(doc.getChildNodes(), model);	
		}
	
	}
	
	private void printNote(NodeList nodeList, Model model) {
		 
	    for (int count = 0; count < nodeList.getLength(); count++) {
	 
			Node tempNode = nodeList.item(count);
		 
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				// get node name and value
//				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				if (tempNode.hasAttributes()) {
					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
		 
					if(MASS_KEYWORD.equals(tempNode.getNodeName())){
						model.add(massCommand(nodeMap));
					}else if(SPRING_KEYWORD.equals(tempNode.getNodeName())){
						model.add(springCommand(nodeMap));
					}
				}
					
//				System.out.println("Node Value =" + tempNode.getTextContent());
		 
				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					printNote(tempNode.getChildNodes(), model);
				}
		 
//				System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
		 
			}
		 
	    }
	}
	
	private Mass massCommand(NamedNodeMap nodeMap) {
		for (int i = 0; i < nodeMap.getLength(); i++) {
			Node node = nodeMap.item(i);
			String nodeName = node.getNodeName();
			String nodeValue = node.getNodeValue();
			
			if(nodeName.equals(MASS_ID)){
				id = nodeValue;
			}else if(nodeName.equals(MASS_MASS)){
				mass = Double.parseDouble(nodeValue);
			}else if(nodeName.equals(MASS_VX)){
				vx = Double.parseDouble(nodeValue);
			}else if(nodeName.equals(MASS_VY)){
				vy = Double.parseDouble(nodeValue);
			}else if(nodeName.equals(MASS_X)){
				x = Double.parseDouble(nodeValue);
			}else if(nodeName.equals(MASS_Y)){
				y = Double.parseDouble(nodeValue);
			}

//			System.out.println(node.getNodeName() + " " + node.getNodeValue());
		}
		
		System.out.println(id + " " + mass + " " + vx + " " + vy + " " + x + " " + y);
		
		Mass result = new Mass(id, Common.MASS_CID, JGColor.blue, 10, mass);
		result.setPos(x, y);
		Vec2 velocity = new Vec2();
		velocity.x = (float) vx;
		velocity.y = (float) vy;
		result.getBody().setLinearVelocity(velocity);
		myMasses.put(id, result);
		
		return result;
    }
	
	private Spring springCommand(NamedNodeMap nodeMap) {
		for (int i = 0; i < nodeMap.getLength(); i++) {
			Node node = nodeMap.item(i);
			String nodeName = node.getNodeName();
			String nodeValue = node.getNodeValue();
			
			if(nodeName.equals(SPRING_A)){
				id1 = nodeValue;
			}else if(nodeName.equals(SPRING_B)){
				id2 = nodeValue;
			}else if(nodeName.equals(SPRING_CONSTANT)){
				constant = Double.parseDouble(nodeValue);
			}else if(nodeName.equals(SPRING_RESTLENGTH)){
				restlength = Double.parseDouble(nodeValue);
			}
			
//			System.out.println(node.getNodeName() + " " + node.getNodeValue());
		}
		System.out.println(id1 + " " + id2 + " " + constant + " " + restlength);
		
		Mass m1 = myMasses.get(id1);
		Mass m2 = myMasses.get(id2);
		
		return new Spring("spring", Common.SPRING_CID, JGColor.cyan, m1, m2, restlength, constant);
    }

}
