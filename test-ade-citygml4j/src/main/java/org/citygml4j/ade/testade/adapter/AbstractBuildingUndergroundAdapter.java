package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.AbstractBuildingUnderground;
import org.citygml4j.ade.testade.model.EnergyPerformanceCertificationProperty;
import org.citygml4j.ade.testade.model.FacilityProperty;
import org.citygml4j.ade.testade.module.TestADEModule;
import org.citygml4j.core.model.core.AddressProperty;
import org.citygml4j.xml.adapter.core.AbstractCityObjectAdapter;
import org.citygml4j.xml.adapter.core.AbstractOccupiedSpaceAdapter;
import org.citygml4j.xml.adapter.core.AddressPropertyAdapter;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.gml.adapter.basictypes.CodeAdapter;
import org.xmlobjects.gml.adapter.geometry.GeometryPropertyAdapter;
import org.xmlobjects.gml.model.basictypes.Code;
import org.xmlobjects.serializer.ObjectSerializeException;
import org.xmlobjects.stream.XMLReadException;
import org.xmlobjects.stream.XMLReader;
import org.xmlobjects.stream.XMLWriteException;
import org.xmlobjects.stream.XMLWriter;
import org.xmlobjects.xml.Attributes;
import org.xmlobjects.xml.Element;
import org.xmlobjects.xml.Namespaces;

import javax.xml.namespace.QName;

public abstract class AbstractBuildingUndergroundAdapter<T extends AbstractBuildingUnderground> extends AbstractOccupiedSpaceAdapter<T> {

    @Override
    public void buildChildObject(T object, QName name, Attributes attributes, XMLReader reader) throws ObjectBuildException, XMLReadException {
        if (TestADEModule.TESTADE_NAMESPACE.equals(name.getNamespaceURI())) {
            switch (name.getLocalPart()) {
                case "class":
                    object.setClassifier(reader.getObjectUsingBuilder(CodeAdapter.class));
                    break;
                case "function":
                    object.getFunctions().add(reader.getObjectUsingBuilder(CodeAdapter.class));
                    break;
                case "usage":
                    object.getUsages().add(reader.getObjectUsingBuilder(CodeAdapter.class));
                    break;
                case "energyPerformanceCertification":
                    object.getEnergyPerformanceCertifications().add(reader.getObjectUsingBuilder(EnergyPerformanceCertificationPropertyAdapter.class));
                    break;
                case "genericGeometry":
                    object.setGenericGeometry(reader.getObjectUsingBuilder(GeometryPropertyAdapter.class));
                    break;
                case "address":
                    object.getAddresses().add(reader.getObjectUsingBuilder(AddressPropertyAdapter.class));
                    break;
                case "equippedWith":
                    object.getEquippedWith().add(reader.getObjectUsingBuilder(FacilityPropertyAdapter.class));
                    break;
            }
        } else
            super.buildChildObject(object, name, attributes, reader);
    }

    @Override
    public void writeChildElements(T object, Namespaces namespaces, XMLWriter writer) throws ObjectSerializeException, XMLWriteException {
        super.writeChildElements(object, namespaces, writer);

        if (object.getClassifier() != null)
            writer.writeElementUsingSerializer(Element.of(TestADEModule.TESTADE_NAMESPACE, "class"), object.getClassifier(), CodeAdapter.class, namespaces);

        if (object.isSetFunctions()) {
            for (Code function : object.getFunctions())
                writer.writeElementUsingSerializer(Element.of(TestADEModule.TESTADE_NAMESPACE, "function"), function, CodeAdapter.class, namespaces);
        }

        if (object.isSetUsages()) {
            for (Code usage : object.getUsages())
                writer.writeElementUsingSerializer(Element.of(TestADEModule.TESTADE_NAMESPACE, "usage"), usage, CodeAdapter.class, namespaces);
        }

        for (EnergyPerformanceCertificationProperty property : object.getEnergyPerformanceCertifications())
            writer.writeElementUsingSerializer(Element.of(TestADEModule.TESTADE_NAMESPACE, "energyPerformanceCertification"), property, EnergyPerformanceCertificationPropertyAdapter.class, namespaces);

        if (object.getGenericGeometry() != null)
            writer.writeElementUsingSerializer(Element.of(TestADEModule.TESTADE_NAMESPACE, "genericGeometry"), object.getGenericGeometry(), GeometryPropertyAdapter.class, namespaces);

        for (AddressProperty property : object.getAddresses())
            writer.writeElementUsingSerializer(Element.of(TestADEModule.TESTADE_NAMESPACE, "address"), property, AddressPropertyAdapter.class, namespaces);

        for (FacilityProperty property : object.getEquippedWith())
            writer.writeElementUsingSerializer(Element.of(TestADEModule.TESTADE_NAMESPACE, "equippedWith"), property, FacilityPropertyAdapter.class, namespaces);
    }
}