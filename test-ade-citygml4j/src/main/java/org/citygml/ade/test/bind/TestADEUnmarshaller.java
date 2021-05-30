package org.citygml.ade.test.bind;

import net.opengis.citygml.building._2.BoundarySurfacePropertyType;
import net.opengis.gml.AreaType;
import org.citygml.ade.test._1.EnergyPerformanceCertificationPropertyType;
import org.citygml.ade.test._1.EnergyPerformanceCertificationType;
import org.citygml.ade.test._1.IndustrialBuildingType;
import org.citygml.ade.test._1.OtherConstructionType;
import org.citygml.ade.test.model.*;
import org.citygml4j.builder.jaxb.unmarshal.citygml.ade.ADEUnmarshallerHelper;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.ade.binding.ADEUnmarshaller;
import org.citygml4j.util.mapper.CheckedTypeMapper;
import org.citygml4j.xml.io.reader.MissingADESchemaException;

import javax.xml.bind.JAXBElement;

public class TestADEUnmarshaller implements ADEUnmarshaller {
	private final CheckedTypeMapper<ADEModelObject> typeMapper;
	private ADEUnmarshallerHelper helper;

	public TestADEUnmarshaller() {
		typeMapper = CheckedTypeMapper.<ADEModelObject>create()
				.with(EnergyPerformanceCertificationType.class, this::unmarshalEnergyPerformanceCertification)
				.with(EnergyPerformanceCertificationPropertyType.class, this::unmarshalEnergyPerformanceCertificationProperty)
				.with(IndustrialBuildingType.class, this::unmarshalIndustrialBuilding)
				.with(OtherConstructionType.class, this::unmarshalOtherConstruction)
				.with(JAXBElement.class, this::unmarshal);
	}

	@Override
	public void setADEUnmarshallerHelper(ADEUnmarshallerHelper helper) {
		this.helper = helper;
	}

	@Override
	public ADEModelObject unmarshal(JAXBElement<?> src) throws MissingADESchemaException {
		final Object value = src.getValue();

		// generic application properties
		switch (src.getName().getLocalPart()) {
		case "ownerName":
			return new OwnerNameProperty((String)value);
		case "floorArea":
			return new FloorAreaProperty(helper.getGMLUnmarshaller().unmarshalArea((AreaType)value));
		case "energyPerformanceCertification":
			return new EnergyPerformanceCertificationPropertyElement(unmarshalEnergyPerformanceCertificationProperty((EnergyPerformanceCertificationPropertyType)value));
		}
		
		// all other types
		return unmarshal(value);
	}

	@Override
	public ADEModelObject unmarshal(Object src) throws MissingADESchemaException {
		return typeMapper.apply(src);
	}
	
	public EnergyPerformanceCertification unmarshalEnergyPerformanceCertification(EnergyPerformanceCertificationType src) throws MissingADESchemaException {
		EnergyPerformanceCertification dest = new EnergyPerformanceCertification();
		
		if (src.isSetCertificationName()) {
			for (String certificationName : src.getCertificationName())
				dest.addCertificationName(certificationName);
		}
		
		if (src.isSetCertificationid())
			dest.setCertificationId(src.getCertificationid());
		
		return dest;
	}
	
	public EnergyPerformanceCertificationProperty unmarshalEnergyPerformanceCertificationProperty(EnergyPerformanceCertificationPropertyType src) throws MissingADESchemaException {
		EnergyPerformanceCertificationProperty dest = new EnergyPerformanceCertificationProperty();
		
		if (src.isSetEnergyPerformanceCertification())
			dest.setEnergyPerformanceCertification(unmarshalEnergyPerformanceCertification(src.getEnergyPerformanceCertification()));
		
		return dest;
	}
	
	public IndustrialBuilding unmarshalIndustrialBuilding(IndustrialBuildingType src) throws MissingADESchemaException {
		IndustrialBuilding dest = new IndustrialBuilding();
		helper.getBuilding200Unmarshaller().unmarshalAbstractBuilding(src, dest);
		
		if (src.isSetRemark())
			dest.setRemark(src.getRemark());
		
		return dest;
	}
	
	public OtherConstruction unmarshalOtherConstruction(OtherConstructionType src) throws MissingADESchemaException {
		OtherConstruction dest = new OtherConstruction();
		helper.getCore200Unmarshaller().unmarshalAbstractSite(src, dest);

		if (src.isSetLod2Solid())
			dest.setLod2Solid(helper.getGMLUnmarshaller().unmarshalSolidProperty(src.getLod2Solid()));

		if (src.isSetLod2MultiCurve())
			dest.setLod2MultiCurve(helper.getGMLUnmarshaller().unmarshalMultiCurveProperty(src.getLod2MultiCurve()));

		if (src.isSetBoundedBySurface()) {
			for (BoundarySurfacePropertyType boundarySurfaceProperty : src.getBoundedBySurface())
				dest.addBoundedBySurface(helper.getBuilding200Unmarshaller().unmarshalBoundarySurfaceProperty(boundarySurfaceProperty));
		}
		
		return dest;
	}
}
