package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.BuildingUndergroundPartProperty;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.gml.adapter.feature.AbstractFeaturePropertyAdapter;

import javax.xml.namespace.QName;

public class BuildingUndergroundPartPropertyAdapter extends AbstractFeaturePropertyAdapter<BuildingUndergroundPartProperty> {

    @Override
    public BuildingUndergroundPartProperty createObject(QName name, Object parent) throws ObjectBuildException {
        return new BuildingUndergroundPartProperty();
    }
}
