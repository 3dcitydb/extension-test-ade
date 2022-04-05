package org.citygml4j.ade.testade.model;

import org.citygml4j.core.model.building.ADEOfAbstractBuilding;
import org.xmlobjects.gml.model.measures.Area;
import org.xmlobjects.model.ChildList;

import java.util.List;

public class AbstractBuildingProperties extends ADEOfAbstractBuilding {
    private String ownerName;
    private Area floorArea;
    private EnergyPerformanceCertificationProperty energyPerformanceCertification;
    private List<AbstractBuildingUndergroundProperty> buildingUnderground;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Area getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(Area floorArea) {
        this.floorArea = asChild(floorArea);
    }

    public EnergyPerformanceCertificationProperty getEnergyPerformanceCertification() {
        return energyPerformanceCertification;
    }

    public void setEnergyPerformanceCertification(EnergyPerformanceCertificationProperty energyPerformanceCertification) {
        this.energyPerformanceCertification = energyPerformanceCertification;
    }

    public List<AbstractBuildingUndergroundProperty> getBuildingUnderground() {
        if (buildingUnderground == null)
            buildingUnderground = new ChildList<>(this);

        return buildingUnderground;
    }

    public void setBuildingUnderground(List<AbstractBuildingUndergroundProperty> buildingUnderground) {
        this.buildingUnderground = asChild(buildingUnderground);
    }
}
