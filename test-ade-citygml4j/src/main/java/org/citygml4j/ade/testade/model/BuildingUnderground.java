package org.citygml4j.ade.testade.model;

import org.xmlobjects.model.ChildList;

import java.util.List;

public class BuildingUnderground extends AbstractBuildingUnderground {
    private List<BuildingUndergroundPartProperty> consistsOf;
    public List<BuildingUndergroundPartProperty> getConsistsOf() {
        if (consistsOf == null)
            consistsOf = new ChildList<>(this);

        return consistsOf;
    }

    public void setConsistsOf(List<BuildingUndergroundPartProperty> consistsOf) {
        this.consistsOf = consistsOf;
    }
}
