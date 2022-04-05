package org.citygml4j.ade.testade.model;

import org.citygml4j.core.model.ade.ADEObject;
import org.citygml4j.core.model.construction.RoofSurface;

public class SolarRoofSurface extends RoofSurface implements ADEObject {
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
