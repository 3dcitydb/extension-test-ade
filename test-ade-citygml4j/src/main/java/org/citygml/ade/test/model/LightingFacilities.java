package org.citygml.ade.test.model;

import org.citygml4j.builder.copy.CopyBuilder;

public class LightingFacilities extends AbstractFacilities {

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new LightingFacilities(), copyBuilder);
	}
	
	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		LightingFacilities copy = (target == null) ? new LightingFacilities() : (LightingFacilities)target;
		return super.copyTo(copy, copyBuilder);
	}
	
}
