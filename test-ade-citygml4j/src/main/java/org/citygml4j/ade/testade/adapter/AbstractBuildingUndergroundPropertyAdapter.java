package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.AbstractBuildingUndergroundProperty;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.gml.adapter.feature.AbstractFeaturePropertyAdapter;

import javax.xml.namespace.QName;

public class AbstractBuildingUndergroundPropertyAdapter extends AbstractFeaturePropertyAdapter<AbstractBuildingUndergroundProperty> {

    @Override
    public AbstractBuildingUndergroundProperty createObject(QName name, Object parent) throws ObjectBuildException {
        return new AbstractBuildingUndergroundProperty();
    }
}
