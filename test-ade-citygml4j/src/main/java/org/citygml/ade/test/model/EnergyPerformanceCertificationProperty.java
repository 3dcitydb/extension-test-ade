package org.citygml.ade.test.model;

import org.citygml.ade.test.model.module.TestADEModule;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.gml.base.AssociationByRep;
import org.citygml4j.model.module.ade.ADEModule;

public class EnergyPerformanceCertificationProperty extends AssociationByRep<EnergyPerformanceCertification> implements ADEModelObject {

	public EnergyPerformanceCertificationProperty() {
	}
	
	public EnergyPerformanceCertificationProperty(EnergyPerformanceCertification energyPerformanceCertification) {
		super(energyPerformanceCertification);
	}
	
	public EnergyPerformanceCertification getEnergyPerformanceCertification() {
		return super.getObject();
	}

	public boolean isSetEnergyPerformanceCertification() {
		return super.isSetObject();
	}

	public void setEnergyPerformanceCertification(EnergyPerformanceCertification energyPerformanceCertification) {
		super.setObject(energyPerformanceCertification);
	}

	public void unsetEnergyPerformanceCertification() {
		super.unsetObject();
	}
	
	@Override
	public Class<EnergyPerformanceCertification> getAssociableClass() {
		return EnergyPerformanceCertification.class;
	}

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new EnergyPerformanceCertificationProperty(), copyBuilder);
	}

	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		EnergyPerformanceCertificationProperty copy = (target == null) ? new EnergyPerformanceCertificationProperty() : (EnergyPerformanceCertificationProperty)target;
		return super.copyTo(copy, copyBuilder);
	}
	
	@Override
	public ADEModule getADEModule() {
		return TestADEModule.v1_0;
	}

}
