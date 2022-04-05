package org.citygml4j.ade.testade.model;

import org.citygml4j.core.model.ade.ADEObject;
import org.xmlobjects.gml.model.base.AbstractInlineProperty;

public class EnergyPerformanceCertificationProperty extends AbstractInlineProperty<EnergyPerformanceCertification> implements ADEObject {

    public EnergyPerformanceCertificationProperty() {
    }

    public EnergyPerformanceCertificationProperty(EnergyPerformanceCertification object) {
        super(object);
    }

    @Override
    public Class<EnergyPerformanceCertification> getTargetType() {
        return EnergyPerformanceCertification.class;
    }
}
