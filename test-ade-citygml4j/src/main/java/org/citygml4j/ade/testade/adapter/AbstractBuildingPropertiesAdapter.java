package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.AbstractBuildingProperties;
import org.citygml4j.ade.testade.model.BuildingUndergroundProperty;
import org.citygml4j.ade.testade.module.TestADEModule;
import org.citygml4j.xml.adapter.ade.SingletonADEProperty;
import org.xmlobjects.annotation.XMLElement;
import org.xmlobjects.annotation.XMLElements;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.builder.ObjectBuilder;
import org.xmlobjects.gml.adapter.measures.AreaAdapter;
import org.xmlobjects.serializer.ObjectSerializeException;
import org.xmlobjects.serializer.ObjectSerializer;
import org.xmlobjects.stream.XMLReadException;
import org.xmlobjects.stream.XMLReader;
import org.xmlobjects.stream.XMLWriteException;
import org.xmlobjects.stream.XMLWriter;
import org.xmlobjects.xml.Attributes;
import org.xmlobjects.xml.Element;
import org.xmlobjects.xml.Namespaces;

import javax.xml.namespace.QName;

@XMLElements({
        @XMLElement(name = "ownerName", namespaceURI = TestADEModule.TESTADE_NAMESPACE),
        @XMLElement(name = "floorArea", namespaceURI = TestADEModule.TESTADE_NAMESPACE),
        @XMLElement(name = "energyPerformanceCertification", namespaceURI = TestADEModule.TESTADE_NAMESPACE),
        @XMLElement(name = "buildingUnderground", namespaceURI = TestADEModule.TESTADE_NAMESPACE)
})
public class AbstractBuildingPropertiesAdapter implements ObjectBuilder<AbstractBuildingProperties>, ObjectSerializer<AbstractBuildingProperties> {

    @Override
    @SingletonADEProperty
    public AbstractBuildingProperties createObject(QName name, Object parent) throws ObjectBuildException {
        return new AbstractBuildingProperties();
    }

    @Override
    public void initializeObject(AbstractBuildingProperties object, QName name, Attributes attributes, XMLReader reader) throws ObjectBuildException, XMLReadException {
        if (TestADEModule.TESTADE_NAMESPACE.equals(name.getNamespaceURI())) {
            switch (name.getLocalPart()) {
                case "ownerName":
                    reader.getTextContent().ifPresent(object::setOwnerName);
                    break;
                case "floorArea":
                    object.setFloorArea(reader.getObjectUsingBuilder(AreaAdapter.class));
                    break;
                case "energyPerformanceCertification":
                    object.setEnergyPerformanceCertification(reader.getObjectUsingBuilder(EnergyPerformanceCertificationPropertyAdapter.class));
                    break;
                case "buildingUnderground":
                    object.getBuildingUnderground().add(reader.getObjectUsingBuilder(BuildingUndergroundPropertyAdapter.class));
                    break;
            }
        }
    }

    @Override
    public void writeChildElements(AbstractBuildingProperties object, Namespaces namespaces, XMLWriter writer) throws ObjectSerializeException, XMLWriteException {
        if (object.getOwnerName() != null)
            writer.writeElement(Element.of(TestADEModule.TESTADE_NAMESPACE, "ownerName").addTextContent(object.getOwnerName()));

        if (object.getFloorArea() != null)
            writer.writeElementUsingSerializer(Element.of(TestADEModule.TESTADE_NAMESPACE, "floorArea"), object.getFloorArea(), AreaAdapter.class, namespaces);

        if (object.getEnergyPerformanceCertification() != null)
            writer.writeElementUsingSerializer(Element.of(TestADEModule.TESTADE_NAMESPACE, "energyPerformanceCertification"), object.getEnergyPerformanceCertification(), EnergyPerformanceCertificationPropertyAdapter.class, namespaces);

        for (BuildingUndergroundProperty property : object.getBuildingUnderground())
            writer.writeElementUsingSerializer(Element.of(TestADEModule.TESTADE_NAMESPACE, "buildingUnderground"), property, BuildingUndergroundPropertyAdapter.class, namespaces);
    }
}
