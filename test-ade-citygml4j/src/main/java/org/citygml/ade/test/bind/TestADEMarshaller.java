package org.citygml.ade.test.bind;

import javax.xml.bind.JAXBElement;

import org.citygml.ade.test._1.BuildingUnitPartPropertyType;
import org.citygml.ade.test._1.BuildingUnitPartType;
import org.citygml.ade.test._1.BuildingUnitType;
import org.citygml.ade.test._1.DHWFacilitiesType;
import org.citygml.ade.test._1.EnergyPerformanceCertificationPropertyType;
import org.citygml.ade.test._1.EnergyPerformanceCertificationType;
import org.citygml.ade.test._1.FacilitiesPropertyType;
import org.citygml.ade.test._1.FacilitiesType;
import org.citygml.ade.test._1.IndustrialBuildingPartType;
import org.citygml.ade.test._1.IndustrialBuildingRoofSurfaceType;
import org.citygml.ade.test._1.IndustrialBuildingType;
import org.citygml.ade.test._1.LightingFacilitiesType;
import org.citygml.ade.test._1.ObjectFactory;
import org.citygml.ade.test._1.OtherConstructionType;
import org.citygml.ade.test._1._AbstractBuildingUnitPropertyType;
import org.citygml.ade.test._1._AbstractBuildingUnitType;
import org.citygml.ade.test.model.AbstractBuildingUnit;
import org.citygml.ade.test.model.AbstractFacilities;
import org.citygml.ade.test.model.BuildingUnit;
import org.citygml.ade.test.model.BuildingUnitPart;
import org.citygml.ade.test.model.BuildingUnitPartProperty;
import org.citygml.ade.test.model.BuildingUnitProperty;
import org.citygml.ade.test.model.BuildingUnitPropertyElement;
import org.citygml.ade.test.model.DHWFacilities;
import org.citygml.ade.test.model.EnergyPerformanceCertification;
import org.citygml.ade.test.model.EnergyPerformanceCertificationProperty;
import org.citygml.ade.test.model.EnergyPerformanceCertificationPropertyElement;
import org.citygml.ade.test.model.FacilitiesProperty;
import org.citygml.ade.test.model.FloorAreaProperty;
import org.citygml.ade.test.model.IndustrialBuilding;
import org.citygml.ade.test.model.IndustrialBuildingPart;
import org.citygml.ade.test.model.IndustrialBuildingRoofSurface;
import org.citygml.ade.test.model.LightingFacilities;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml.ade.test.model.OwnerNameProperty;
import org.citygml4j.builder.jaxb.marshal.citygml.ade.ADEMarshallerHelper;
import org.citygml4j.model.citygml.ade.binding.ADEMarshaller;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;
import org.citygml4j.model.citygml.core.AddressProperty;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.util.mapper.TypeMapper;
import org.w3._1999.xlink.ActuateType;
import org.w3._1999.xlink.ShowType;
import org.w3._1999.xlink.TypeType;

public class TestADEMarshaller implements ADEMarshaller {
	private final ObjectFactory factory = new ObjectFactory();
	private final TypeMapper<JAXBElement<?>> elementMapper;
	private final TypeMapper<Object> typeMapper;

	private ADEMarshallerHelper helper;

	public TestADEMarshaller() {
		elementMapper = TypeMapper.<JAXBElement<?>>create()
				.with(BuildingUnitPropertyElement.class, this::createBuildingUnitProperty)
				.with(EnergyPerformanceCertificationPropertyElement.class, this::createEnergyPerformanceCertificationProperty)
				.with(FloorAreaProperty.class, this::createFloorArea)
				.with(OwnerNameProperty.class, this::createOwnerName)
				.with(BuildingUnit.class, this::createBuildingUnit)
				.with(BuildingUnitPart.class, this::createBuildingUnitPart)
				.with(DHWFacilities.class, this::createDHWFacilities)
				.with(LightingFacilities.class, this::createLightingFacilities)
				.with(EnergyPerformanceCertification.class, this::createEnergyPerformanceCertification)
				.with(IndustrialBuilding.class, this::createIndustrialBuilding)
				.with(IndustrialBuildingPart.class, this::createIndustrialBuildingPart)
				.with(IndustrialBuildingRoofSurface.class, this::createIndustrialBuildingRoofSurface)
				.with(OtherConstruction.class, this::createOtherConstruction);
		
		typeMapper = TypeMapper.create()
				.with(BuildingUnit.class, this::marshalBuildingUnit)
				.with(BuildingUnitPart.class, this::marshalBuildingUnitPart)
				.with(BuildingUnitProperty.class, this::marshalBuildingUnitProperty)
				.with(BuildingUnitPartProperty.class, this::marshalBuildingUnitPartProperty)	
				.with(DHWFacilities.class, this::marshalDHWFacilities)
				.with(LightingFacilities.class, this::marshalLightingFacilities)
				.with(FacilitiesProperty.class, this::marshalFacilitiesProperty)
				.with(EnergyPerformanceCertification.class, this::marshalEnergyPerformanceCertification)
				.with(EnergyPerformanceCertificationProperty.class, this::marshalEnergyPerformanceCertificationProperty)
				.with(IndustrialBuilding.class, this::marshalIndustrialBuilding)
				.with(IndustrialBuildingPart.class, this::marshalIndustrialBuildingPart)
				.with(IndustrialBuildingRoofSurface.class, this::marshalIndustrialBuildingRoofSurface)
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

	public void marshalAbstractFacilities(AbstractFacilities src, FacilitiesType dest) {
		helper.getGMLMarshaller().marshalAbstractFeature(src, dest);

		if (src.isSetTotalValue())
			dest.setTotalValue(helper.getGMLMarshaller().marshalMeasure(src.getTotalValue()));
	}

	public DHWFacilitiesType marshalDHWFacilities(DHWFacilities src) {
		DHWFacilitiesType dest = factory.createDHWFacilitiesType();
		marshalAbstractFacilities(src, dest);

		return dest;
	}

	public LightingFacilitiesType marshalLightingFacilities(LightingFacilities src) {
		LightingFacilitiesType dest = factory.createLightingFacilitiesType();
		marshalAbstractFacilities(src, dest);

		return dest;
	}

	@SuppressWarnings("unchecked")
	public FacilitiesPropertyType marshalFacilitiesProperty(FacilitiesProperty src) {
		FacilitiesPropertyType dest = factory.createFacilitiesPropertyType();

		if (src.isSetFacilities()) {
			JAXBElement<?> elem = helper.getJAXBMarshaller().marshalJAXBElement(src.getFacilities());
			if (elem != null && elem.getValue() instanceof FacilitiesType)
				dest.setFacilities((JAXBElement<? extends FacilitiesType>)elem);
		}

		if (src.isSetRemoteSchema())
			dest.setRemoteSchema(src.getRemoteSchema());

		if (src.isSetType())
			dest.setType(TypeType.fromValue(src.getType().getValue()));

		if (src.isSetHref())
			dest.setHref(src.getHref());

		if (src.isSetRole())
			dest.setRole(src.getRole());

		if (src.isSetArcrole())
			dest.setArcrole(src.getArcrole());

		if (src.isSetTitle())
			dest.setTitle(src.getTitle());

		if (src.isSetShow())
			dest.setShow(ShowType.fromValue(src.getShow().getValue()));

		if (src.isSetActuate())
			dest.setActuate(ActuateType.fromValue(src.getActuate().getValue()));

		return dest;		
	}

	public void marshalAbstractBuildingUnit(AbstractBuildingUnit src, _AbstractBuildingUnitType dest) {
		helper.getCore200Marshaller().marshalAbstractCityObject(src, dest);

		if (src.isSetClazz())
			dest.setClazz(helper.getGMLMarshaller().marshalCode(src.getClazz()));

		if (src.isSetFunction()) {
			for (Code function : src.getFunction())
				dest.getFunction().add(helper.getGMLMarshaller().marshalCode(function));
		}

		if (src.isSetUsage()) {
			for (Code usage : src.getUsage())
				dest.getUsage().add(helper.getGMLMarshaller().marshalCode(usage));
		}

		if (src.isSetEnergyPerformanceCertification()) {
			for (EnergyPerformanceCertificationProperty property : src.getEnergyPerformanceCertification())
				dest.getEnergyPerformanceCertification().add(marshalEnergyPerformanceCertificationProperty(property));
		}

		if (src.isSetLod1Solid())
			dest.setLod1Solid(helper.getGMLMarshaller().marshalSolidProperty(src.getLod1Solid()));

		if (src.isSetLod2Solid())
			dest.setLod2Solid(helper.getGMLMarshaller().marshalSolidProperty(src.getLod2Solid()));

		if (src.isSetLod3Solid())
			dest.setLod3Solid(helper.getGMLMarshaller().marshalSolidProperty(src.getLod3Solid()));

		if (src.isSetLod4Solid())
			dest.setLod4Solid(helper.getGMLMarshaller().marshalSolidProperty(src.getLod4Solid()));

		if (src.isSetLod1MultiSurface())
			dest.setLod1MultiSurface(helper.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod1MultiSurface()));

		if (src.isSetLod2MultiSurface())
			dest.setLod2MultiSurface(helper.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod2MultiSurface()));

		if (src.isSetLod3MultiSurface())
			dest.setLod3MultiSurface(helper.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod3MultiSurface()));

		if (src.isSetLod4MultiSurface())
			dest.setLod4MultiSurface(helper.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod4MultiSurface()));

		if (src.isSetLod2MultiCurve())
			dest.setLod2MultiCurve(helper.getGMLMarshaller().marshalMultiCurveProperty(src.getLod2MultiCurve()));

		if (src.isSetLod3MultiCurve())
			dest.setLod3MultiCurve(helper.getGMLMarshaller().marshalMultiCurveProperty(src.getLod3MultiCurve()));

		if (src.isSetLod4MultiCurve())
			dest.setLod4MultiCurve(helper.getGMLMarshaller().marshalMultiCurveProperty(src.getLod4MultiCurve()));

		if (src.isSetAddress()) {
			for (AddressProperty addressProperty : src.getAddress())
				dest.getAddress().add(helper.getCore200Marshaller().marshalAddressProperty(addressProperty));
		}

		if (src.isSetEquippedWith()) {
			for (FacilitiesProperty facilitiesProperty : src.getEquippedWith())
				dest.getEquippedWith().add(marshalFacilitiesProperty(facilitiesProperty));
		}

		if (src.isSetConsistsOf()) {
			for (BuildingUnitPartProperty buildingUnitPartProperty : src.getConsistsOf())
				dest.getConsistsOf().add(marshalBuildingUnitPartProperty(buildingUnitPartProperty));
		}
	}

	public BuildingUnitType marshalBuildingUnit(BuildingUnit src) {
		BuildingUnitType dest = factory.createBuildingUnitType();
		marshalAbstractBuildingUnit(src, dest);

		return dest;
	}

	public BuildingUnitPartType marshalBuildingUnitPart(BuildingUnitPart src) {
		BuildingUnitPartType dest = factory.createBuildingUnitPartType();
		marshalAbstractBuildingUnit(src, dest);

		return dest;
	}

	@SuppressWarnings("unchecked")
	public _AbstractBuildingUnitPropertyType marshalBuildingUnitProperty(BuildingUnitProperty src) {
		_AbstractBuildingUnitPropertyType dest = factory.create_AbstractBuildingUnitPropertyType();

		if (src.isSetBuildingUnit()) {
			JAXBElement<?> elem = helper.getJAXBMarshaller().marshalJAXBElement(src.getBuildingUnit());
			if (elem != null && elem.getValue() instanceof _AbstractBuildingUnitType)
				dest.set_AbstractBuildingUnit((JAXBElement<? extends _AbstractBuildingUnitType>)elem);	
		}

		if (src.isSetRemoteSchema())
			dest.setRemoteSchema(src.getRemoteSchema());

		if (src.isSetType())
			dest.setType(TypeType.fromValue(src.getType().getValue()));

		if (src.isSetHref())
			dest.setHref(src.getHref());

		if (src.isSetRole())
			dest.setRole(src.getRole());

		if (src.isSetArcrole())
			dest.setArcrole(src.getArcrole());

		if (src.isSetTitle())
			dest.setTitle(src.getTitle());

		if (src.isSetShow())
			dest.setShow(ShowType.fromValue(src.getShow().getValue()));

		if (src.isSetActuate())
			dest.setActuate(ActuateType.fromValue(src.getActuate().getValue()));

		return dest;		
	}

	public BuildingUnitPartPropertyType marshalBuildingUnitPartProperty(BuildingUnitPartProperty src) {
		BuildingUnitPartPropertyType dest = factory.createBuildingUnitPartPropertyType();

		if (src.isSetBuildingUnitPart())
			dest.setBuildingUnitPart(marshalBuildingUnitPart(src.getBuildingUnitPart()));

		if (src.isSetRemoteSchema())
			dest.setRemoteSchema(src.getRemoteSchema());

		if (src.isSetType())
			dest.setType(TypeType.fromValue(src.getType().getValue()));

		if (src.isSetHref())
			dest.setHref(src.getHref());

		if (src.isSetRole())
			dest.setRole(src.getRole());

		if (src.isSetArcrole())
			dest.setArcrole(src.getArcrole());

		if (src.isSetTitle())
			dest.setTitle(src.getTitle());

		if (src.isSetShow())
			dest.setShow(ShowType.fromValue(src.getShow().getValue()));

		if (src.isSetActuate())
			dest.setActuate(ActuateType.fromValue(src.getActuate().getValue()));

		return dest;		
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
	
	public IndustrialBuildingPartType marshalIndustrialBuildingPart(IndustrialBuildingPart src) {
		IndustrialBuildingPartType dest = factory.createIndustrialBuildingPartType();
		helper.getBuilding200Marshaller().marshalBuildingPart(src, dest);
		
		if (src.isSetRemark()) {
			dest.setRemark(src.getRemark());
		}
		
		return dest;
	}
	
	public IndustrialBuildingRoofSurfaceType marshalIndustrialBuildingRoofSurface(IndustrialBuildingRoofSurface src) {
		IndustrialBuildingRoofSurfaceType dest = factory.createIndustrialBuildingRoofSurfaceType();
		helper.getBuilding200Marshaller().marshalRoofSurface(src, dest);
		
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

	public JAXBElement<?> createBuildingUnitProperty(BuildingUnitPropertyElement src) {
		return factory.createBuildingUnitProperty(marshalBuildingUnitProperty(src.getValue()));
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


	public JAXBElement<?> createBuildingUnit(BuildingUnit src) {
		return factory.createBuildingUnit(marshalBuildingUnit(src));
	}

	public JAXBElement<?> createBuildingUnitPart(BuildingUnitPart src) {
		return factory.createBuildingUnitPart(marshalBuildingUnitPart(src));
	}

	public JAXBElement<?> createDHWFacilities(DHWFacilities src) {
		return factory.createDHWFacilities(marshalDHWFacilities(src));
	}

	public JAXBElement<?> createLightingFacilities(LightingFacilities src) {
		return factory.createLightingFacilities(marshalLightingFacilities(src));
	}

	public JAXBElement<?> createEnergyPerformanceCertification(EnergyPerformanceCertification src) {
		return factory.createEnergyPerformanceCertification(marshalEnergyPerformanceCertification(src));
	}

	public JAXBElement<?> createIndustrialBuilding(IndustrialBuilding src) {
		return factory.createIndustrialBuilding(marshalIndustrialBuilding(src));
	}

	public JAXBElement<?> createIndustrialBuildingPart(IndustrialBuildingPart src) {
		return factory.createIndustrialBuildingPart(marshalIndustrialBuildingPart(src));
	}
	
	public JAXBElement<?> createIndustrialBuildingRoofSurface(IndustrialBuildingRoofSurface src) {
		return factory.createIndustrialBuildingRoofSurface(marshalIndustrialBuildingRoofSurface(src));
	}
	
	public JAXBElement<?> createOtherConstruction(OtherConstruction src) {
		return factory.createOtherConstruction(marshalOtherConstruction(src));
	}
}
