package org.citydb.ade.test.schema;

import org.citydb.ade.ADEExtensionException;
import org.citydb.ade.ADEObjectMapper;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citydb.database.schema.mapping.SchemaMapping;
import org.citygml.ade.test.model.AbstractBuildingUnit;
import org.citygml.ade.test.model.AbstractFacilities;
import org.citygml.ade.test.model.BuildingUnit;
import org.citygml.ade.test.model.BuildingUnitPart;
import org.citygml.ade.test.model.DHWFacilities;
import org.citygml.ade.test.model.IndustrialBuilding;
import org.citygml.ade.test.model.IndustrialBuildingPart;
import org.citygml.ade.test.model.IndustrialBuildingRoofSurface;
import org.citygml.ade.test.model.LightingFacilities;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml4j.model.gml.base.AbstractGML;
import org.citygml4j.model.module.citygml.CityGMLVersion;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ObjectMapper implements ADEObjectMapper {
	private final Map<Class<? extends AbstractGML>, Integer> objectClassIds = new HashMap<>();

	public void populateObjectClassIds(SchemaMapping schemaMapping) throws ADEExtensionException {
		for (AbstractObjectType<?> type : schemaMapping.getAbstractObjectTypes()) {
			int objectClassId = type.getObjectClassId();
			
			switch (type.getPath()) {
			case "_AbstractBuildingUnit":
				objectClassIds.put(AbstractBuildingUnit.class, objectClassId);
				break;
			case "BuildingUnit":
				objectClassIds.put(BuildingUnit.class, objectClassId);
				break;
			case "BuildingUnitPart":
				objectClassIds.put(BuildingUnitPart.class, objectClassId);
				break;
			case "Facilities":
				objectClassIds.put(AbstractFacilities.class, objectClassId);
				break;
			case "DHWFacilities":
				objectClassIds.put(DHWFacilities.class, objectClassId);
				break;
			case "LightingFacilities":
				objectClassIds.put(LightingFacilities.class, objectClassId);
				break;
			case "IndustrialBuilding":
				objectClassIds.put(IndustrialBuilding.class, objectClassId);
				break;
			case "IndustrialBuildingPart":
				objectClassIds.put(IndustrialBuildingPart.class, objectClassId);
				break;
			case "IndustrialBuildingRoofSurface":
				objectClassIds.put(IndustrialBuildingRoofSurface.class, objectClassId);
				break;
			case "OtherConstruction":
				objectClassIds.put(OtherConstruction.class, objectClassId);
				break;
			}
		}
	}

	@Override
	public AbstractGML createObject(int objectClassId, CityGMLVersion version) {
		if (version == CityGMLVersion.v2_0_0) {
			for (Entry<Class<? extends AbstractGML>, Integer> entry : objectClassIds.entrySet()) {
				if (entry.getValue() == objectClassId) {
					try {
						return entry.getKey().getDeclaredConstructor().newInstance();
					} catch (Exception e) {
						// 
					}
				}
			}
		}

		return null;
	}

	@Override
	public int getObjectClassId(Class<? extends AbstractGML> adeObjectClass) {
		Integer objectClassId = objectClassIds.get(adeObjectClass);
		return objectClassId != null ? objectClassId : 0;
	}

}
