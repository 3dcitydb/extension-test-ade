package org.citydb.ade.test.schema;

import org.citydb.ade.ADEExtensionException;
import org.citydb.ade.ADEObjectMapper;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citydb.database.schema.mapping.SchemaMapping;
import org.citygml.ade.test.model.IndustrialBuilding;
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
			case "IndustrialBuilding":
				objectClassIds.put(IndustrialBuilding.class, objectClassId);
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
