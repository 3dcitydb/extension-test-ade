package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.MovingConstruction;
import org.citygml4j.ade.testade.model.SolarRoofSurfaceProperty;
import org.citygml4j.ade.testade.module.TestADEModule;
import org.citygml4j.core.model.core.AbstractSpaceBoundaryProperty;
import org.citygml4j.xml.adapter.deprecated.building.AbstractBoundarySurfacePropertyAdapter;
import org.citygml4j.xml.adapter.deprecated.core.AbstractSiteAdapter;
import org.xmlobjects.annotation.XMLElement;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.serializer.ObjectSerializeException;
import org.xmlobjects.stream.XMLReadException;
import org.xmlobjects.stream.XMLReader;
import org.xmlobjects.stream.XMLWriteException;
import org.xmlobjects.stream.XMLWriter;
import org.xmlobjects.xml.Attributes;
import org.xmlobjects.xml.Element;
import org.xmlobjects.xml.Namespaces;

import javax.xml.namespace.QName;

@XMLElement(name = "MovingConstruction", namespaceURI = TestADEModule.TESTADE_NAMESPACE)
public class MovingConstructionAdapter extends AbstractSiteAdapter<MovingConstruction> {

    @Override
    public MovingConstruction createObject(QName name, Object parent) throws ObjectBuildException {
        return new MovingConstruction();
    }

    @Override
    public void buildChildObject(MovingConstruction object, QName name, Attributes attributes, XMLReader reader) throws ObjectBuildException, XMLReadException {
        if (TestADEModule.TESTADE_NAMESPACE.equals(name.getNamespaceURI())) {
            switch (name.getLocalPart()) {
                case "boundedBy":
                    object.addBoundary(reader.getObjectUsingBuilder(AbstractBoundarySurfacePropertyAdapter.class));
                    break;
                case "coveredBy":
                    object.getCoveredBy().add(reader.getObjectUsingBuilder(SolarRoofSurfacePropertyAdapter.class));
            }
        } else
            super.buildChildObject(object, name, attributes, reader);
    }

    @Override
    public Element createElement(MovingConstruction object, Namespaces namespaces) throws ObjectSerializeException {
        return Element.of(TestADEModule.TESTADE_NAMESPACE, "MovingConstruction");
    }

    @Override
    public void writeChildElements(MovingConstruction object, Namespaces namespaces, XMLWriter writer) throws ObjectSerializeException, XMLWriteException {
        super.writeChildElements(object, namespaces, writer);

        if (object.isSetBoundaries()) {
            for (AbstractSpaceBoundaryProperty property : object.getBoundaries())
                writer.writeElementUsingSerializer(Element.of(TestADEModule.TESTADE_NAMESPACE, "boundedBy"), property, AbstractBoundarySurfacePropertyAdapter.class, namespaces);
        }

        for (SolarRoofSurfaceProperty property : object.getCoveredBy())
            writer.writeElementUsingSerializer(Element.of(TestADEModule.TESTADE_NAMESPACE, "coveredBy"), property, SolarRoofSurfacePropertyAdapter.class, namespaces);
    }
}
