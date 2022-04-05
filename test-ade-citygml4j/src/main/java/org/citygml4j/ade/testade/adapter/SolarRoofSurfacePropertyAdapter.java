package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.SolarRoofSurfaceProperty;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.gml.adapter.feature.AbstractFeaturePropertyAdapter;

import javax.xml.namespace.QName;

public class SolarRoofSurfacePropertyAdapter extends AbstractFeaturePropertyAdapter<SolarRoofSurfaceProperty> {

    @Override
    public SolarRoofSurfaceProperty createObject(QName name, Object parent) throws ObjectBuildException {
        return new SolarRoofSurfaceProperty();
    }
}
