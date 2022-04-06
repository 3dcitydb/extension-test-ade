package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.Facility;
import org.citygml4j.ade.testade.module.TestADEModule;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.gml.adapter.base.AbstractGMLAdapter;
import org.xmlobjects.gml.adapter.basictypes.MeasureAdapter;
import org.xmlobjects.serializer.ObjectSerializeException;
import org.xmlobjects.stream.XMLReadException;
import org.xmlobjects.stream.XMLReader;
import org.xmlobjects.stream.XMLWriteException;
import org.xmlobjects.stream.XMLWriter;
import org.xmlobjects.xml.Attributes;
import org.xmlobjects.xml.Element;
import org.xmlobjects.xml.Namespaces;

import javax.xml.namespace.QName;

public abstract class FacilityAdapter<T extends Facility> extends AbstractGMLAdapter<T> {

    @Override
    public void buildChildObject(T object, QName name, Attributes attributes, XMLReader reader) throws ObjectBuildException, XMLReadException {
        if (TestADEModule.TESTADE_NAMESPACE.equals(name.getNamespaceURI())
                && "electricalPower".equals(name.getLocalPart())) {
            object.setElectricalPower(reader.getObjectUsingBuilder(MeasureAdapter.class));
        } else
            super.buildChildObject(object, name, attributes, reader);
    }

    @Override
    public void writeChildElements(T object, Namespaces namespaces, XMLWriter writer) throws ObjectSerializeException, XMLWriteException {
        super.writeChildElements(object, namespaces, writer);

        if (object.getElectricalPower() != null)
            writer.writeElementUsingSerializer(Element.of(TestADEModule.TESTADE_NAMESPACE, "electricalPower"), object.getElectricalPower(), MeasureAdapter.class, namespaces);
    }
}
