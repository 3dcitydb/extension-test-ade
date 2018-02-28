package org.citygml.ade.test.walker;

import java.util.ArrayList;

import org.citygml.ade.test.model.AbstractBuildingUnit;
import org.citygml.ade.test.model.AbstractFacilities;
import org.citygml.ade.test.model.BuildingUnit;
import org.citygml.ade.test.model.BuildingUnitPart;
import org.citygml.ade.test.model.BuildingUnitPartProperty;
import org.citygml.ade.test.model.BuildingUnitPropertyElement;
import org.citygml.ade.test.model.DHWFacilities;
import org.citygml.ade.test.model.FacilitiesProperty;
import org.citygml.ade.test.model.IndustrialBuilding;
import org.citygml.ade.test.model.IndustrialBuildingPart;
import org.citygml.ade.test.model.IndustrialBuildingRoofSurface;
import org.citygml.ade.test.model.LightingFacilities;
import org.citygml.ade.test.model.OtherConstruction;
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
import org.citygml4j.util.walker.FeatureFunctionWalker;

public class TestADEFeatureFunctionWalker<T> implements ADEWalker<FeatureFunctionWalker<T>> {
	private FeatureFunctionWalker<T> walker;

	@Override
	public void setParentWalker(FeatureFunctionWalker<T> walker) {
		this.walker = walker;
	}

	public T apply(AbstractFacilities abstractFacilities) {
		return walker.apply((AbstractFeature)abstractFacilities);
	}

	public T apply(DHWFacilities dhwFacilities) {
		return apply((AbstractFacilities)dhwFacilities);
	}

	public T apply(LightingFacilities lightingFacilities) {
		return apply((AbstractFacilities)lightingFacilities);
	}

	public T apply(AbstractBuildingUnit abstractBuildingUnit) {
		T object = walker.apply((AbstractCityObject)abstractBuildingUnit);
		if (object != null)
			return object;
		
		if (abstractBuildingUnit.isSetEquippedWith()) {
			for (FacilitiesProperty property : abstractBuildingUnit.getEquippedWith()) {
				object = walker.apply((FeatureProperty<?>)property);
				if (object != null)
					return object;
			}
		}

		if (abstractBuildingUnit.isSetConsistsOf()) {
			for (BuildingUnitPartProperty property : abstractBuildingUnit.getConsistsOf()) {
				object = walker.apply((FeatureProperty<?>)property);
				if (object != null)
					return object;
			}
		}
		
		if (abstractBuildingUnit.isSetAddress()) {
			for (AddressProperty property : new ArrayList<AddressProperty>(abstractBuildingUnit.getAddress())) {
				object = walker.apply((FeatureProperty<?>)property);
				if (object != null)
					return object;
			}
		}
		
		return null;
	}

	public T apply(BuildingUnit buildingUnit) {
		return apply((AbstractBuildingUnit)buildingUnit);
	}

	public T apply(BuildingUnitPart buildingUnitPart) {
		return apply((AbstractBuildingUnit)buildingUnitPart);
	}
	
	public T apply(IndustrialBuilding industrialBuilding) {
		return walker.apply((AbstractBuilding)industrialBuilding);
	}
	
	public T apply(BuildingUnitPropertyElement buildingUnitPropertyElement) {
		return walker.apply((FeatureProperty<?>)buildingUnitPropertyElement.getValue());
	}

	public T apply(IndustrialBuildingPart industrialBuildingPart) {
		return walker.apply((BuildingPart)industrialBuildingPart);
	}
	
	public T apply(IndustrialBuildingRoofSurface industrialBuildingRoofSurface) {
		return walker.apply((RoofSurface)industrialBuildingRoofSurface);
	}
	
	public T apply(OtherConstruction otherConstruction) {
		T object =  walker.apply((AbstractSite)otherConstruction);
		if (object != null)
			return object;
		
		if (otherConstruction.isSetBoundedBySurface()) {
			for (BoundarySurfaceProperty property : new ArrayList<BoundarySurfaceProperty>(otherConstruction.getBoundedBySurface())) {
				object = walker.apply((FeatureProperty<?>)property);
				if (object != null)
					return object;
			}
		}
		
		return null;
	}
}
