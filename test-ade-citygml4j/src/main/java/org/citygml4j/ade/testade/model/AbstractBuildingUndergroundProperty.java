package org.citygml4j.ade.testade.model;

import org.citygml4j.core.model.ade.ADEObject;
import org.xmlobjects.gml.model.feature.FeatureProperty;

public class AbstractBuildingUndergroundProperty extends FeatureProperty<AbstractBuildingUnderground> implements ADEObject {

    public AbstractBuildingUndergroundProperty() {
    }

    public AbstractBuildingUndergroundProperty(AbstractBuildingUnderground object) {
        super(object);
    }

    public AbstractBuildingUndergroundProperty(String href) {
        super(href);
    }

    @Override
    public Class<AbstractBuildingUnderground> getTargetType() {
        return AbstractBuildingUnderground.class;
    }
}
