package org.citygml4j.ade.testade.model;

import org.citygml4j.core.model.ade.ADEObject;
import org.xmlobjects.gml.model.feature.FeatureProperty;

public class FacilityProperty extends FeatureProperty<Facility> implements ADEObject {

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
