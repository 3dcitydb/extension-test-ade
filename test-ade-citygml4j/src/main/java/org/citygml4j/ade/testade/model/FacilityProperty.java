package org.citygml4j.ade.testade.model;

import org.citygml4j.core.model.ade.ADEObject;
import org.xmlobjects.gml.model.base.AbstractProperty;

public class FacilityProperty extends AbstractProperty<Facility> implements ADEObject {

    public FacilityProperty() {
    }

    public FacilityProperty(Facility object) {
        super(object);
    }

    public FacilityProperty(String href) {
        super(href);
    }

    @Override
    public Class<Facility> getTargetType() {
        return Facility.class;
    }
}
