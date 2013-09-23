package parser;

import jboxGlue.PhysicalObject;
import org.w3c.dom.NamedNodeMap;

public abstract class CreateObject {
    public abstract PhysicalObject createObject(NamedNodeMap nodeMap);
}
