package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.IndustrialBuilding;
import org.citygml4j.ade.testade.module.TestADEModule;
import org.citygml4j.xml.adapter.deprecated.building.BuildingAdapter;
import org.xmlobjects.annotation.XMLElement;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.serializer.ObjectSerializeException;
import org.xmlobjects.stream.XMLReadException;
import org.xmlobjects.stream.XMLReader;
import org.xmlobjects.stream.XMLWriteException;
import org.xmlobjects.stream.XMLWriter;
import org.xmlobjects.util.composite.CompositeObjectAdapter;
import org.xmlobjects.xml.Attributes;
import org.xmlobjects.xml.Element;
import org.xmlobjects.xml.Namespaces;

import javax.xml.namespace.QName;

@XMLElement(name = "IndustrialBuilding", namespaceURI = TestADEModule.TESTADE_NAMESPACE)
public class IndustrialBuildingAdapter extends CompositeObjectAdapter<IndustrialBuilding> {

    public IndustrialBuildingAdapter() {
        super(BuildingAdapter.class);
    }

    @Override
    public IndustrialBuilding createObject(QName name, Object parent) throws ObjectBuildException {
        return new IndustrialBuilding();
    }

    @Override
    public void buildChildObject(IndustrialBuilding object, QName name, Attributes attributes, XMLReader reader) throws ObjectBuildException, XMLReadException {
        if (TestADEModule.TESTADE_NAMESPACE.equals(name.getNamespaceURI())
                && "remark".equals(name.getLocalPart())) {
            reader.getTextContent().ifPresent(object::setRemark);
        } else
            super.buildChildObject(object, name, attributes, reader);
    }

    @Override
    public Element createElement(IndustrialBuilding object, Namespaces namespaces) throws ObjectSerializeException {
        return Element.of(TestADEModule.TESTADE_NAMESPACE, "IndustrialBuilding");
    }

    @Override
    public void writeChildElements(IndustrialBuilding object, Namespaces namespaces, XMLWriter writer) throws ObjectSerializeException, XMLWriteException {
        super.writeChildElements(object, namespaces, writer);

        if (object.getRemark() != null)
            writer.writeElement(Element.of(TestADEModule.TESTADE_NAMESPACE, "remark").addTextContent(object.getRemark()));
    }
}