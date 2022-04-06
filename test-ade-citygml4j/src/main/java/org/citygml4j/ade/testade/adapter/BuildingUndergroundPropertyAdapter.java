package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.BuildingUndergroundProperty;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.gml.adapter.feature.AbstractFeaturePropertyAdapter;

import javax.xml.namespace.QName;

public class BuildingUndergroundPropertyAdapter extends AbstractFeaturePropertyAdapter<BuildingUndergroundProperty> {

    @Override
    public BuildingUndergroundProperty createObject(QName name, Object parent) throws ObjectBuildException {
        return new BuildingUndergroundProperty();
    }
}
