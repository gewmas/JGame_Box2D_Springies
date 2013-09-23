package parser;

import java.util.Map;
import jboxGlue.PhysicalObject;
import jgame.JGColor;
import object.Spring;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import springies.Common;


public class CreateSpring extends CreateObject {
    Map<String, PhysicalObject> myMasses;

    CreateSpring (Map<String, PhysicalObject> myMasses) {
        this.myMasses = myMasses;
    }

    @Override
    public PhysicalObject createObject (NamedNodeMap nodeMap) {
        double constant = Common.DEFAULT_K;
        double restlength = 0;
        String id1 = null;
        String id2 = null;

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
        // System.out.println(id1 + " " + id2 + " " + constant + " " + restlength);

        PhysicalObject m1 = myMasses.get(id1);
        PhysicalObject m2 = myMasses.get(id2);

        return new Spring("spring", Common.SPRING_CID, JGColor.cyan, m1, m2, restlength, constant);
    }

}
