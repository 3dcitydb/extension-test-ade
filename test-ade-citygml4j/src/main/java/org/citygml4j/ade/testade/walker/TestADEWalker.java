package org.citygml4j.ade.testade.walker;

import org.citygml4j.ade.testade.model.*;
import org.citygml4j.core.model.building.Building;
import org.citygml4j.core.model.building.BuildingPart;
import org.citygml4j.core.model.construction.OtherConstruction;
import org.citygml4j.core.model.construction.RoofSurface;
import org.citygml4j.core.model.core.AbstractCityObject;
import org.citygml4j.core.model.core.AbstractFeature;
import org.citygml4j.core.visitor.ADEWalker;

public class TestADEWalker extends ADEWalker {

    public void visit(AbstractBuildingUnderground abstractBuildingUnderground) {
        walker.visit((AbstractCityObject) abstractBuildingUnderground);
    }

    public void visit(BuildingUnderground buildingUnderground) {
        visit((AbstractBuildingUnderground) buildingUnderground);
    }

    public void visit(BuildingUndergroundPart buildingUndergroundPart) {
        visit((AbstractBuildingUnderground) buildingUndergroundPart);
    }

    public void visit(ElectricalAppliance electricalAppliance) {
        visit((Facility) electricalAppliance);
    }

    public void visit(Facility facility) {
        walker.visit((AbstractFeature) facility);
    }

    public void visit(IndustrialBuilding building) {
        walker.visit((Building) building);
    }

    public void visit(IndustrialBuildingPart buildingPart) {
        walker.visit((BuildingPart) buildingPart);
    }

    public void visit(SolarRoofSurface roofSurface) {
        walker.visit((RoofSurface) roofSurface);
    }

    public void visit(LightingFacility lightingFacility) {
        visit((Facility) lightingFacility);
    }

    public void visit(MovingConstruction movingConstruction) {
        walker.visit((OtherConstruction) movingConstruction);
    }
}
