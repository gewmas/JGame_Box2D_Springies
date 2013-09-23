package parser;

import org.w3c.dom.NamedNodeMap;
import environment.Force;

public abstract class CreateForce {

    public abstract Force createForce(NamedNodeMap nodeMap);
}
