package parser;

import jboxGlue.PhysicalObject;
import jgame.JGColor;
import object.Muscle;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import springies.Common;

public class CreateMuscle extends CreateObject {
    private double restlength = 0;
    private double constant = Common.DEFAULT_K;
    private String id1;
    private String id2;
    private double amplitude = 0;
    
    @Override
    public PhysicalObject createObject (NamedNodeMap nodeMap) {

        
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

        return new Muscle("muscle", Common.SPRING_CID, JGColor.cyan, m1, m2, restlength, constant,
                          amplitude);
    
    }

}
