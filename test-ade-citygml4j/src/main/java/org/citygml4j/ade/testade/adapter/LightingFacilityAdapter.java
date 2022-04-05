package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.LightingFacility;
import org.citygml4j.ade.testade.module.TestADEModule;
import org.xmlobjects.annotation.XMLElement;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.serializer.ObjectSerializeException;
import org.xmlobjects.xml.Element;
import org.xmlobjects.xml.Namespaces;

import javax.xml.namespace.QName;

@XMLElement(name = "LightingFacility", namespaceURI = TestADEModule.TESTADE_NAMESPACE)
public class LightingFacilityAdapter extends FacilityAdapter<LightingFacility> {

    @Override
    public LightingFacility createObject(QName name, Object parent) throws ObjectBuildException {
        return new LightingFacility();
    }

    @Override
    public Element createElement(LightingFacility object, Namespaces namespaces) throws ObjectSerializeException {
        return Element.of(TestADEModule.TESTADE_NAMESPACE, "LightingFacility");
    }
}
