package parser;

import java.util.Map;
import jboxGlue.PhysicalObject;
import object.Mass;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import springies.Common;


public class CreateMass extends CreateObject {
    private double vx = 0;
    private double vy = 0;
    private double mass = Common.DEFAULT_MASS;
    private String id;
    private double y = 0;
    private double x = 0;
    Map<String, PhysicalObject> myMasses;

    CreateMass (Map<String, PhysicalObject> myMasses) {
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

        // System.out.println(id + " " + mass + " " + (float)vx + " " + vy + " " + x + " " + y);

        Mass result = new Mass(id, Common.MASS_CID, mass, x, y, vx, vy);
        myMasses.put(id, result);

        return result;

    }

    public Map<String, PhysicalObject> getMyMasses () {
        return myMasses;
    }

}
