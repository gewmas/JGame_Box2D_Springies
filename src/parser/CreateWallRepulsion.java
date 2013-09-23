package parser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import springies.Common;
import environment.Force;
import environment.WallRepulsion1;
import environment.WallRepulsion2;
import environment.WallRepulsion3;
import environment.WallRepulsion4;

public class CreateWallRepulsion extends CreateForce {
    private double exponent = 0;
    private double magnitude = 0;
    private String wallId;
    
    @Override
    public Force createForce (NamedNodeMap nodeMap) {
        for (int i = 0; i < nodeMap.getLength(); i++) {
            Node node = nodeMap.item(i);
            String nodeName = node.getNodeName();
            String nodeValue = node.getNodeValue();

            if (nodeName.equals(Common.MASS_ID)) {
                wallId = nodeValue;
            }

            if (nodeName.equals(Common.MAGNITUDE)) {
                magnitude = Double.parseDouble(nodeValue);
            }

            if (nodeName.equals(Common.EXPONENT)) {
                exponent = Double.parseDouble(nodeValue);
            }

        }

        if (wallId.equals("1"))
            return new WallRepulsion1(wallId, magnitude, exponent);
        else if (wallId.equals("2"))
            return new WallRepulsion2(wallId, magnitude, exponent);
        else if (wallId.equals("3"))
            return new WallRepulsion3(wallId, magnitude, exponent);
        else return new WallRepulsion4(wallId, magnitude, exponent);
    }

}
