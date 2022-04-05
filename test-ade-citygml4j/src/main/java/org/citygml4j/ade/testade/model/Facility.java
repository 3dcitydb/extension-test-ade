package org.citygml4j.ade.testade.model;

import org.citygml4j.core.model.ade.ADEObject;
import org.citygml4j.core.model.core.AbstractFeature;
import org.xmlobjects.gml.model.basictypes.Measure;

public abstract class Facility extends AbstractFeature implements ADEObject {
    private Measure electricalPower;

    public Measure getElectricalPower() {
        return electricalPower;
    }

    public void setElectricalPower(Measure electricalPower) {
        this.electricalPower = asChild(electricalPower);
    }
}
