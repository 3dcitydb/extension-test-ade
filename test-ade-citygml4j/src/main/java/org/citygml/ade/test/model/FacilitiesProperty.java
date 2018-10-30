package org.citygml.ade.test.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.gml.feature.FeatureProperty;

public class FacilitiesProperty extends FeatureProperty<AbstractFacilities> implements ADEModelObject {

	public FacilitiesProperty() {
	}
	
	public FacilitiesProperty(AbstractFacilities abstractFacilities) {
		super(abstractFacilities);
	}
	
	public FacilitiesProperty(String href) {
		super(href);
	}
	
	public AbstractFacilities getFacilities() {
		return super.getObject();
	}

	public boolean isSetFacilities() {
		return super.isSetObject();
	}

	public void setFacilities(AbstractFacilities facilities) {
		super.setObject(facilities);
	}

	public void unsetFacilities() {
		super.unsetObject();
	}

	@Override
	public Class<AbstractFacilities> getAssociableClass() {
		return AbstractFacilities.class;
	}

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new FacilitiesProperty(), copyBuilder);
	}

	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		FacilitiesProperty copy = (target == null) ? new FacilitiesProperty() : (FacilitiesProperty)target;
		return super.copyTo(copy, copyBuilder);
	}

}
