package org.citygml.ade.test.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEGenericApplicationProperty;

public class EnergyPerformanceCertificationPropertyElement extends ADEGenericApplicationProperty<EnergyPerformanceCertificationProperty> {

	public EnergyPerformanceCertificationPropertyElement() {
	}
	
	public EnergyPerformanceCertificationPropertyElement(EnergyPerformanceCertificationProperty value) {
		super(value);
	}
	
	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new EnergyPerformanceCertificationPropertyElement(), copyBuilder);
	}
	
}
