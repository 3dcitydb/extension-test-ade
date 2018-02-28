package org.citydb.ade.test.importer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.ADEPropertyCollection;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.importer.ForeignKeys;
import org.citydb.ade.test.schema.ADETables;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.citygml.importer.CityGMLImportException;
import org.citydb.citygml.importer.util.AttributeValueJoiner;
import org.citydb.database.schema.mapping.FeatureType;
import org.citygml.ade.test.model.AbstractBuildingUnit;
import org.citygml.ade.test.model.BuildingUnitPropertyElement;
import org.citygml.ade.test.model.EnergyPerformanceCertification;
import org.citygml.ade.test.model.EnergyPerformanceCertificationPropertyElement;
import org.citygml.ade.test.model.FloorAreaProperty;
import org.citygml.ade.test.model.OwnerNameProperty;
import org.citygml4j.model.citygml.building.AbstractBuilding;

public class BuildingPropertiesImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final SchemaMapper schemaMapper;

	private AttributeValueJoiner valueJoiner;
	private PreparedStatement ps;
	private int batchCounter;

	public BuildingPropertiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
		this.helper = helper;
		this.schemaMapper = manager.getSchemaMapper();

		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(schemaMapper.getTableName(ADETables.BUILDING))).append(" ")
				.append("(id, ownername, energyperforma_certification, energyperform_certificatio_1, floorarea, floorarea_uom) ")
				.append("values (?, ?, ?, ?, ?, ?)");
		ps = connection.prepareStatement(stmt.toString());

		valueJoiner = helper.getAttributeValueJoiner();
	}

	public void doImport(ADEPropertyCollection properties, AbstractBuilding parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
		ps.setLong(1, parentId);

		OwnerNameProperty ownerName = properties.getFirst(OwnerNameProperty.class);
		if (ownerName != null && ownerName.getValue() != null)
			ps.setString(2, ownerName.getValue());
		else
			ps.setNull(2, Types.VARCHAR);

		EnergyPerformanceCertificationPropertyElement energyCertificationElement = properties.getFirst(EnergyPerformanceCertificationPropertyElement.class);			
		if (energyCertificationElement != null 
				&& energyCertificationElement.getValue().isSetEnergyPerformanceCertification()) {
			EnergyPerformanceCertification energyCertification = energyCertificationElement.getValue().getEnergyPerformanceCertification();
			ps.setString(3, valueJoiner.join(energyCertification.getCertificationName()));
			ps.setString(4, energyCertification.getCertificationId());
		} else {
			ps.setNull(3, Types.VARCHAR);
			ps.setNull(4, Types.VARCHAR);
		}

		FloorAreaProperty floorArea = properties.getFirst(FloorAreaProperty.class);
		if (floorArea != null && floorArea.getValue().isSetValue()) {
			ps.setDouble(5, floorArea.getValue().getValue());
			ps.setString(6, floorArea.getValue().getUom());
		} else {
			ps.setNull(5, Types.DOUBLE);
			ps.setNull(6, Types.VARCHAR);
		}

		ps.addBatch();
		if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
			helper.executeBatch(schemaMapper.getTableName(ADETables.BUILDING));

		if (properties.contains(BuildingUnitPropertyElement.class)) {
			for (BuildingUnitPropertyElement propertyElement : properties.getAll(BuildingUnitPropertyElement.class)) {
				AbstractBuildingUnit buildingUnit = propertyElement.getValue().getBuildingUnit();
				if (buildingUnit != null) {
					helper.importObject(buildingUnit, ForeignKeys.create().with("buildingId", parentId));
					propertyElement.getValue().unsetBuildingUnit();
				} else {
					String href = propertyElement.getValue().getHref();
					if (href != null && href.length() != 0)
						helper.logOrThrowUnsupportedXLinkMessage(parent, AbstractBuildingUnit.class, href);
				}				
			}
		}
	}

	@Override
	public void executeBatch() throws CityGMLImportException, SQLException {
		if (batchCounter > 0) {
			ps.executeBatch();
			batchCounter = 0;
		}
	}

	@Override
	public void close() throws CityGMLImportException, SQLException {
		ps.close();
	}

}
