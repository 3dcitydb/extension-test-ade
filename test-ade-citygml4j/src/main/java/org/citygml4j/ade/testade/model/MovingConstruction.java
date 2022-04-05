package org.citygml4j.ade.testade.model;

import org.citygml4j.core.model.ade.ADEObject;
import org.citygml4j.core.model.construction.AbstractConstruction;
import org.citygml4j.core.model.construction.AbstractConstructionSurface;
import org.citygml4j.core.model.construction.OtherConstruction;
import org.citygml4j.core.model.core.AbstractSpaceBoundary;
import org.citygml4j.core.model.core.AbstractSpaceBoundaryProperty;
import org.citygml4j.core.model.core.ClosureSurface;
import org.xmlobjects.model.ChildList;

import java.util.List;

public class MovingConstruction extends OtherConstruction implements ADEObject {
    private String remark;
    private List<SolarRoofSurfaceProperty> coveredBy;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<SolarRoofSurfaceProperty> getCoveredBy() {
        if (coveredBy == null)
            coveredBy = new ChildList<>(this);

        return coveredBy;
    }

    public void setCoveredBy(List<SolarRoofSurfaceProperty> coveredBy) {
        this.coveredBy = coveredBy;
    }

    @Override
    public boolean isValidBoundary(AbstractSpaceBoundary boundary) {
        return boundary instanceof AbstractConstructionSurface
                || boundary instanceof ClosureSurface;
    }
}
