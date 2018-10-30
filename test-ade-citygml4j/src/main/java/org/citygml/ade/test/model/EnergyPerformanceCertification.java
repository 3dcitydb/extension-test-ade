package org.citygml.ade.test.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.common.association.Associable;
import org.citygml4j.model.common.base.ModelObject;

import java.util.ArrayList;
import java.util.List;

public class EnergyPerformanceCertification implements Associable, ADEModelObject {
	private List<String> certificationName;
	private String certificationId;
	private ModelObject parent;
	
	public void addCertificationName(String certificationName) {
		if (this.certificationName == null)
			this.certificationName = new ArrayList<>();
		
		this.certificationName.add(certificationName);
	}

	public List<String> getCertificationName() {
		if (certificationName == null)
			certificationName = new ArrayList<>();
		
		return certificationName;
	}
	
	public boolean isSetCertificationName() {
		return certificationName != null && !certificationName.isEmpty();
	}

	public void setCertificationName(List<String> certificationName) {
		this.certificationName = certificationName;
	}

	public String getCertificationId() {
		return certificationId;
	}
	
	public boolean isSetCertificationId() {
		return certificationId != null;
	}

	public void setCertificationId(String certificationId) {
		this.certificationId = certificationId;
	}

	@Override
	public ModelObject getParent() {
		return parent;
	}

	@Override
	public void setParent(ModelObject parent) {
		this.parent = parent;
	}

	@Override
	public boolean isSetParent() {
		return parent != null;
	}

	@Override
	public void unsetParent() {
		parent = null;
	}

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new EnergyPerformanceCertification(), copyBuilder);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		EnergyPerformanceCertification copy = (target == null) ? new EnergyPerformanceCertification() : (EnergyPerformanceCertification)target;
		
		if (isSetCertificationName())
			copy.setCertificationName((List<String>)copyBuilder.copy(this.certificationName));
		
		if (isSetCertificationId())
			copy.setCertificationId(copyBuilder.copy(certificationId));
		
		copy.unsetParent();
		return copy;
	}

}
