package parser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import springies.Common;
import environment.CenterOfMass;
import environment.Force;

public class CreateCenterOfMass extends CreateForce {
    private double magnitude = 0;
    private double exponent = 0;
    
    @Override
    public Force createForce (NamedNodeMap nodeMap) {
        for (int i = 0; i < nodeMap.getLength(); i++) {
            Node node = nodeMap.item(i);
            String nodeName = node.getNodeName();
            String nodeValue = node.getNodeValue();

            if (nodeName.equals(Common.MAGNITUDE)) {
                magnitude = Double.parseDouble(nodeValue);
            }

            if (nodeName.equals(Common.EXPONENT)) {
                exponent = Double.parseDouble(nodeValue);
            }

        }

        return new CenterOfMass(magnitude, exponent);
    }

}
