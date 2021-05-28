package org.citygml.ade.test.bind;

import org.citygml.ade.test._1.*;
import org.citygml.ade.test.model.*;
import org.citygml4j.builder.jaxb.marshal.citygml.ade.ADEMarshallerHelper;
import org.citygml4j.model.citygml.ade.binding.ADEMarshaller;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;
import org.citygml4j.util.mapper.TypeMapper;

import javax.xml.bind.JAXBElement;

public class TestADEMarshaller implements ADEMarshaller {
	private final ObjectFactory factory = new ObjectFactory();
	private final TypeMapper<JAXBElement<?>> elementMapper;
	private final TypeMapper<Object> typeMapper;

	private ADEMarshallerHelper helper;

	public TestADEMarshaller() {
		elementMapper = TypeMapper.<JAXBElement<?>>create()
				.with(EnergyPerformanceCertificationPropertyElement.class, this::createEnergyPerformanceCertificationProperty)
				.with(FloorAreaProperty.class, this::createFloorArea)
				.with(OwnerNameProperty.class, this::createOwnerName)
				.with(EnergyPerformanceCertification.class, this::createEnergyPerformanceCertification)
				.with(IndustrialBuilding.class, this::createIndustrialBuilding)
				.with(OtherConstruction.class, this::createOtherConstruction);
		
		typeMapper = TypeMapper.create()
				.with(EnergyPerformanceCertification.class, this::marshalEnergyPerformanceCertification)
				.with(EnergyPerformanceCertificationProperty.class, this::marshalEnergyPerformanceCertificationProperty)
				.with(IndustrialBuilding.class, this::marshalIndustrialBuilding)
				.with(OtherConstruction.class, this::marshalOtherConstruction);
	}

	@Override
	public void setADEMarshallerHelper(ADEMarshallerHelper helper) {
		this.helper = helper;
	}

	@Override
	public JAXBElement<?> marshalJAXBElement(ADEModelObject src) {
		return elementMapper.apply(src);
	}

	@Override
	public Object marshal(ADEModelObject src) {
		return typeMapper.apply(src);
	}

	public EnergyPerformanceCertificationType marshalEnergyPerformanceCertification(EnergyPerformanceCertification src) {
		EnergyPerformanceCertificationType dest = factory.createEnergyPerformanceCertificationType();

		if (src.isSetCertificationName()) {
			for (String certificationName : src.getCertificationName())
				dest.getCertificationName().add(certificationName);
		}

		if (src.isSetCertificationId())
			dest.setCertificationid(src.getCertificationId());

		return dest;
	}

	public EnergyPerformanceCertificationPropertyType marshalEnergyPerformanceCertificationProperty(EnergyPerformanceCertificationProperty src) {
		EnergyPerformanceCertificationPropertyType dest = factory.createEnergyPerformanceCertificationPropertyType();

		if (src.isSetEnergyPerformanceCertification())
			dest.setEnergyPerformanceCertification(marshalEnergyPerformanceCertification(src.getEnergyPerformanceCertification()));

		return dest;
	}

	public IndustrialBuildingType marshalIndustrialBuilding(IndustrialBuilding src) {
		IndustrialBuildingType dest = factory.createIndustrialBuildingType();
		helper.getBuilding200Marshaller().marshalAbstractBuilding(src, dest);
		
		if (src.isSetRemark()) {
			dest.setRemark(src.getRemark());
		}
		
		return dest;
	}

	public OtherConstructionType marshalOtherConstruction(OtherConstruction src) {
		OtherConstructionType dest = factory.createOtherConstructionType();
		helper.getCore200Marshaller().marshalAbstractSite(src, dest);
		
		if (src.isSetBoundedBySurface()) {
			for (BoundarySurfaceProperty boundarySurfaceProperty : src.getBoundedBySurface())
				dest.getBoundedBySurface().add(helper.getBuilding200Marshaller().marshalBoundarySurfaceProperty(boundarySurfaceProperty));
		}
		
		return dest;
	}

	public JAXBElement<?> createEnergyPerformanceCertificationProperty(EnergyPerformanceCertificationPropertyElement src) {
		return factory.createEnergyPerformanceCertificationProperty(marshalEnergyPerformanceCertificationProperty(src.getValue()));
	}

	public JAXBElement<?> createFloorArea(FloorAreaProperty src) {
		return factory.createFloorArea(helper.getGMLMarshaller().marshalArea(src.getValue()));
	}

	public JAXBElement<?> createOwnerName(OwnerNameProperty src) {
		return factory.createOwnerName(src.getValue());
	}


	public JAXBElement<?> createEnergyPerformanceCertification(EnergyPerformanceCertification src) {
		return factory.createEnergyPerformanceCertification(marshalEnergyPerformanceCertification(src));
	}

	public JAXBElement<?> createIndustrialBuilding(IndustrialBuilding src) {
		return factory.createIndustrialBuilding(marshalIndustrialBuilding(src));
	}
	
	public JAXBElement<?> createOtherConstruction(OtherConstruction src) {
		return factory.createOtherConstruction(marshalOtherConstruction(src));
	}
}
