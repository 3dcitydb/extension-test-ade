package org.citygml.ade.test.walker;

import org.citygml.ade.test.model.IndustrialBuilding;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml4j.model.citygml.ade.binding.ADEWalker;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;
import org.citygml4j.model.citygml.core.AbstractSite;
import org.citygml4j.model.gml.feature.FeatureProperty;
import org.citygml4j.util.walker.GMLWalker;

import java.util.ArrayList;

public class TestADEGMLWalker implements ADEWalker<GMLWalker> {
	private GMLWalker walker;

	@Override
	public void setParentWalker(GMLWalker walker) {
		this.walker = walker;
	}

	public void visit(IndustrialBuilding industrialBuilding) {
		walker.visit((AbstractBuilding)industrialBuilding);
	}
	
	public void visit(OtherConstruction otherConstruction) {
		walker.visit((AbstractSite)otherConstruction);

		if (otherConstruction.isSetLod2Solid())
			walker.visit(otherConstruction.getLod2Solid());

		if (otherConstruction.isSetLod2MultiCurve())
			walker.visit(otherConstruction.getLod2MultiCurve());

		if (otherConstruction.isSetBoundedBySurface()) {
			for (BoundarySurfaceProperty property : new ArrayList<BoundarySurfaceProperty>(otherConstruction.getBoundedBySurface()))
				walker.visit((FeatureProperty<?>)property);
		}
	}

}
