package org.citydb.ade.test.exporter;

import org.citydb.ade.exporter.ADEExportManager;
import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.citygml.exporter.CityGMLExportException;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citydb.database.schema.mapping.FeatureType;
import org.citydb.query.filter.projection.ProjectionFilter;
import org.citygml.ade.test.model.IndustrialBuilding;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.gml.feature.AbstractFeature;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ExportManager implements ADEExportManager {
	private final Map<Class<? extends ADEExporter>, ADEExporter> exporters;
	private final SchemaMapper schemaMapper;
	
	private Connection connection;
	private CityGMLExportHelper helper;
	
	public ExportManager(SchemaMapper schemaMapper) {
		this.schemaMapper = schemaMapper;
		exporters = new HashMap<>();
	}
	
	@Override
	public void init(Connection connection, CityGMLExportHelper helper) throws CityGMLExportException, SQLException {
		this.connection = connection;
		this.helper = helper;
	}

	@Override
	public void exportObject(ADEModelObject object, long objectId, AbstractObjectType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
		if (object instanceof IndustrialBuilding)
			getExporter(IndustrialBuildingExporter.class).doExport((IndustrialBuilding)object, objectId, objectType, projectionFilter);
		else if (object instanceof OtherConstruction)
			getExporter(OtherConstructionExporter.class).doExport((OtherConstruction)object, objectId, objectType, projectionFilter);
	}
	
	@Override
	public void exportGenericApplicationProperties(String adeHookTable, AbstractFeature parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
		if (adeHookTable.equals(schemaMapper.getTableName(ADETable.BUILDING)) && parent instanceof AbstractBuilding)
			getExporter(BuildingPropertiesExporter.class).doExport((AbstractBuilding)parent, parentId, parentType, projectionFilter);
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		for (ADEExporter exporter : exporters.values())
			exporter.close();
	}
	
	protected SchemaMapper getSchemaMapper() {
		return schemaMapper;
	}

	protected <T extends ADEExporter> T getExporter(Class<T> type) throws CityGMLExportException, SQLException {
		ADEExporter exporter = exporters.get(type);
		
		if (exporter == null) {
			if (type == IndustrialBuildingExporter.class)
				exporter = new IndustrialBuildingExporter(connection, helper, this);
			else if (type == OtherConstructionExporter.class)
				exporter = new OtherConstructionExporter(helper);
			else if (type == BuildingPropertiesExporter.class)
				exporter = new BuildingPropertiesExporter(connection, helper, this);

			if (exporter == null)
				throw new SQLException("Failed to build ADE exporter of type " + type.getName() + ".");
			
			exporters.put(type, exporter);
		}
		
		return type.cast(exporter);
	}

}
