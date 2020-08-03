package org.citygml.ade.test.walker;

import org.citygml.ade.test.model.*;
import org.citygml4j.model.citygml.ade.binding.ADEWalker;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;
import org.citygml4j.model.citygml.building.BuildingPart;
import org.citygml4j.model.citygml.building.RoofSurface;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.citygml.core.AbstractSite;
import org.citygml4j.model.citygml.core.AddressProperty;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.gml.feature.FeatureProperty;
import org.citygml4j.util.walker.GMLWalker;

import java.util.ArrayList;

public class TestADEGMLWalker implements ADEWalker<GMLWalker> {
	private GMLWalker walker;

	@Override
	public void setParentWalker(GMLWalker walker) {
		this.walker = walker;
	}

	public void visit(AbstractFacilities abstractFacilities) {
		walker.visit((AbstractFeature)abstractFacilities);
	}

	public void visit(DHWFacilities dhwFacilities) {
		visit((AbstractFacilities)dhwFacilities);
	}

	public void visit(LightingFacilities lightingFacilities) {
		visit((AbstractFacilities)lightingFacilities);
	}

	public void visit(AbstractBuildingUnit abstractBuildingUnit) {
		walker.visit((AbstractCityObject)abstractBuildingUnit);

		if (abstractBuildingUnit.isSetEquippedWith()) {
			for (FacilitiesProperty property : abstractBuildingUnit.getEquippedWith())
				walker.visit((FeatureProperty<?>)property);
		}

		if (abstractBuildingUnit.isSetConsistsOf()) {
			for (BuildingUnitPartProperty property : abstractBuildingUnit.getConsistsOf())
				walker.visit((FeatureProperty<?>)property);
		}
		
		if (abstractBuildingUnit.isSetAddress()) {
			for (AddressProperty property : new ArrayList<AddressProperty>(abstractBuildingUnit.getAddress()))
				walker.visit((FeatureProperty<?>)property);
		}
		
		if (abstractBuildingUnit.isSetLod1Solid())
			walker.visit(abstractBuildingUnit.getLod1Solid());

		if (abstractBuildingUnit.isSetLod2Solid())
			walker.visit(abstractBuildingUnit.getLod2Solid());

		if (abstractBuildingUnit.isSetLod3Solid())
			walker.visit(abstractBuildingUnit.getLod3Solid());

		if (abstractBuildingUnit.isSetLod4Solid())
			walker.visit(abstractBuildingUnit.getLod4Solid());
		
		if (abstractBuildingUnit.isSetLod2MultiCurve())
			walker.visit(abstractBuildingUnit.getLod2MultiCurve());

		if (abstractBuildingUnit.isSetLod3MultiCurve())
			walker.visit(abstractBuildingUnit.getLod3MultiCurve());

		if (abstractBuildingUnit.isSetLod4MultiCurve())
			walker.visit(abstractBuildingUnit.getLod4MultiCurve());
		
		if (abstractBuildingUnit.isSetLod1MultiSurface())
			walker.visit(abstractBuildingUnit.getLod1MultiSurface());

		if (abstractBuildingUnit.isSetLod2MultiSurface())
			walker.visit(abstractBuildingUnit.getLod2MultiSurface());

		if (abstractBuildingUnit.isSetLod3MultiSurface())
			walker.visit(abstractBuildingUnit.getLod3MultiSurface());

		if (abstractBuildingUnit.isSetLod4MultiSurface())
			walker.visit(abstractBuildingUnit.getLod4MultiSurface());
	}

	public void visit(BuildingUnit buildingUnit) {
		visit((AbstractBuildingUnit)buildingUnit);
	}

	public void visit(BuildingUnitPart buildingUnitPart) {
		visit((AbstractBuildingUnit)buildingUnitPart);
	}
	
	public void visit(IndustrialBuilding industrialBuilding) {
		walker.visit((AbstractBuilding)industrialBuilding);
	}
	
	public void visit(BuildingUnitPropertyElement buildingUnitPropertyElement) {
		walker.visit((FeatureProperty<?>)buildingUnitPropertyElement.getValue());
	}
	
	public void visit(IndustrialBuildingPart industrialBuildingPart) {
		walker.visit((BuildingPart)industrialBuildingPart);
	}

	public void visit(IndustrialBuildingRoofSurface industrialBuildingRoofSurface) {
		walker.visit((RoofSurface)industrialBuildingRoofSurface);
	}
	
	public void visit(OtherConstruction otherConstruction) {
		walker.visit((AbstractSite)otherConstruction);
		
		if (otherConstruction.isSetBoundedBySurface()) {
			for (BoundarySurfaceProperty property : new ArrayList<BoundarySurfaceProperty>(otherConstruction.getBoundedBySurface()))
				walker.visit((FeatureProperty<?>)property);
		}
	}

}
