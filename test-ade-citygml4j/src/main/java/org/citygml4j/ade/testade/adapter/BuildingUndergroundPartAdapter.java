package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.BuildingUndergroundPart;
import org.citygml4j.ade.testade.module.TestADEModule;
import org.xmlobjects.annotation.XMLElement;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.serializer.ObjectSerializeException;
import org.xmlobjects.xml.Element;
import org.xmlobjects.xml.Namespaces;

import javax.xml.namespace.QName;

@XMLElement(name = "BuildingUndergroundPart", namespaceURI = TestADEModule.TESTADE_NAMESPACE)
public class BuildingUndergroundPartAdapter extends AbstractBuildingUndergroundAdapter<BuildingUndergroundPart> {

    @Override
    public BuildingUndergroundPart createObject(QName name, Object parent) throws ObjectBuildException {
        return new BuildingUndergroundPart();
    }

    @Override
    public Element createElement(BuildingUndergroundPart object, Namespaces namespaces) throws ObjectSerializeException {
        return Element.of(TestADEModule.TESTADE_NAMESPACE, "BuildingUndergroundPart");
    }
}
