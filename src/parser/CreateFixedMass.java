package parser;

import java.util.Map;
import jboxGlue.PhysicalObject;
import object.FixedMass;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import springies.Common;


public class CreateFixedMass extends CreateObject {
    private String id;
    private double x = 0;
    private double y = 0;

    Map<String, PhysicalObject> myMasses;

    CreateFixedMass (Map<String, PhysicalObject> myMasses) {
        this.myMasses = myMasses;
    }

    @Override
    public PhysicalObject createObject (NamedNodeMap nodeMap) {
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

        // System.out.println(id + " " + mass + " " + (float)vx + " " + vy + " " + x + " " + y);

        FixedMass result = new FixedMass(id, Common.FIXEDMASS_CID, x, y);
        myMasses.put(id, result);

        return result;

    }

}
