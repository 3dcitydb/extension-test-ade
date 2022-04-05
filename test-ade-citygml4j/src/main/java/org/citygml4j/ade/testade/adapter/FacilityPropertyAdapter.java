package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.FacilityProperty;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.gml.adapter.feature.AbstractFeaturePropertyAdapter;

import javax.xml.namespace.QName;

public class FacilityPropertyAdapter extends AbstractFeaturePropertyAdapter<FacilityProperty> {

    @Override
    public FacilityProperty createObject(QName name, Object parent) throws ObjectBuildException {
        return new FacilityProperty();
    }
}
