package org.citygml.ade.test.model;

import org.citygml.ade.test.model.module.TestADEModule;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEGenericApplicationProperty;
import org.citygml4j.model.module.ade.ADEModule;

public class OwnerNameProperty extends ADEGenericApplicationProperty<String> {

	public OwnerNameProperty() {
	}
	
	public OwnerNameProperty(String value) {
		super(value);
	}
	
	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new OwnerNameProperty(), copyBuilder);
	}
	
	@Override
	public ADEModule getADEModule() {
		return TestADEModule.v1_0;
	}
	
}
