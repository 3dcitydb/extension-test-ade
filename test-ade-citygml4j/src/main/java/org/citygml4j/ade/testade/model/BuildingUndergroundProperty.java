package org.citygml4j.ade.testade.model;

import org.citygml4j.core.model.ade.ADEObject;
import org.xmlobjects.gml.model.feature.FeatureProperty;

public class BuildingUndergroundProperty extends FeatureProperty<BuildingUnderground> implements ADEObject {

    public BuildingUndergroundProperty() {
    }

    public BuildingUndergroundProperty(BuildingUnderground object) {
        super(object);
    }

    public BuildingUndergroundProperty(String href) {
        super(href);
    }

    @Override
    public Class<BuildingUnderground> getTargetType() {
        return BuildingUnderground.class;
    }
}
