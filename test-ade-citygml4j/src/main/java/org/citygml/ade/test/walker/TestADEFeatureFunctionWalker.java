package org.citygml.ade.test.walker;

import org.citygml.ade.test.model.*;
import org.citygml4j.model.citygml.ade.binding.ADEWalker;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;
import org.citygml4j.model.citygml.core.AbstractSite;
import org.citygml4j.model.gml.feature.FeatureProperty;
import org.citygml4j.util.walker.FeatureFunctionWalker;

import java.util.ArrayList;

public class TestADEFeatureFunctionWalker<T> implements ADEWalker<FeatureFunctionWalker<T>> {
	private FeatureFunctionWalker<T> walker;

	@Override
	public void setParentWalker(FeatureFunctionWalker<T> walker) {
		this.walker = walker;
	}

	public T apply(IndustrialBuilding industrialBuilding) {
		return walker.apply((AbstractBuilding)industrialBuilding);
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
