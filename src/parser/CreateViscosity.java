package parser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import springies.Common;
import environment.Force;
import environment.Viscosity;

public class CreateViscosity extends CreateForce {
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
        }

        return new Viscosity(magnitude);
    }

}
