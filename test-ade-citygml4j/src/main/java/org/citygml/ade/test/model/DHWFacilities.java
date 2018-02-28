package org.citygml.ade.test.model;

import org.citygml4j.builder.copy.CopyBuilder;

public class DHWFacilities extends AbstractFacilities {

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new DHWFacilities(), copyBuilder);
	}
	
	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		DHWFacilities copy = (target == null) ? new DHWFacilities() : (DHWFacilities)target;
		return super.copyTo(copy, copyBuilder);
	}
	
}
