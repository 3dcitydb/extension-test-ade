package org.citygml.ade.test.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.common.visitor.FeatureFunctor;
import org.citygml4j.model.common.visitor.FeatureVisitor;
import org.citygml4j.model.common.visitor.GMLFunctor;
import org.citygml4j.model.common.visitor.GMLVisitor;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.feature.AbstractFeature;

public abstract class AbstractFacilities extends AbstractFeature implements ADEModelObject {
	private Measure totalValue;
	
	public Measure getTotalValue() {
		return totalValue;
	}

	public boolean isSetTotalValue() {
		return totalValue != null;
	}
	
	public void setTotalValue(Measure totalValue) {
		if (totalValue != null)
			totalValue.setParent(this);
		
		this.totalValue = totalValue;
	}

	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		if (target == null)
			throw new IllegalArgumentException("Target argument must not be null for abstract copyable classes.");
		
		AbstractFacilities copy = (AbstractFacilities)target;
		super.copyTo(copy, copyBuilder);
		
		if (isSetTotalValue()) {
			copy.setTotalValue((Measure)copyBuilder.copy(totalValue));
			if (copy.getTotalValue() == totalValue)
				totalValue.setParent(this);
		}
		
		return copy;
	}
	
	@Override
	public void accept(FeatureVisitor visitor) {
		visitor.visit((ADEModelObject)this);
	}

	@Override
	public <T> T accept(FeatureFunctor<T> visitor) {
		return visitor.apply((ADEModelObject)this);
	}

	@Override
	public void accept(GMLVisitor visitor) {
		visitor.visit((ADEModelObject)this);
	}

	@Override
	public <T> T accept(GMLFunctor<T> visitor) {
		return visitor.apply((ADEModelObject)this);
	}
}
