package parser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import springies.Common;
import environment.Force;
import environment.Gravity;


public class CreateGravity extends CreateForce {
    private double direction = 0;
    private double magnitude = 0;

    @Override
    public Force createForce (NamedNodeMap nodeMap) {
        for (int i = 0; i < nodeMap.getLength(); i++) {
            Node node = nodeMap.item(i);
            String nodeName = node.getNodeName();
            String nodeValue = node.getNodeValue();

            if (nodeName.equals(Common.MAGNITUDE)) {
                magnitude = Double.parseDouble(nodeValue);
            }

            if (nodeName.equals(Common.GRAVITY_DIRECTION)) {
                direction = Double.parseDouble(nodeValue);
            }
        }

        return new Gravity(direction, magnitude);
    }

}
