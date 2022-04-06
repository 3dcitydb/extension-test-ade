package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.FacilityProperty;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.gml.adapter.base.AbstractPropertyAdapter;

import javax.xml.namespace.QName;

public class FacilityPropertyAdapter extends AbstractPropertyAdapter<FacilityProperty> {

    @Override
    public FacilityProperty createObject(QName name, Object parent) throws ObjectBuildException {
        return new FacilityProperty();
    }
}
