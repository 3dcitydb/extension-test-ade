package org.citydb.ade.test.exporter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.ade.test.schema.ADETables;
import org.citydb.citygml.exporter.CityGMLExportException;
import org.citydb.citygml.exporter.util.AttributeValueSplitter;
import org.citydb.citygml.exporter.util.AttributeValueSplitter.SplitValue;
import org.citydb.database.schema.mapping.FeatureType;
import org.citydb.query.filter.lod.LodFilter;
import org.citydb.query.filter.projection.ProjectionFilter;
import org.citygml.ade.test.model.EnergyPerformanceCertification;
import org.citygml.ade.test.model.EnergyPerformanceCertificationProperty;
import org.citygml.ade.test.model.EnergyPerformanceCertificationPropertyElement;
import org.citygml.ade.test.model.FloorAreaProperty;
import org.citygml.ade.test.model.OwnerNameProperty;
import org.citygml.ade.test.model.module.TestADEModule;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.gml.measures.Area;

public class BuildingPropertiesExporter implements ADEExporter {
	private PreparedStatement ps;
	private BuildingUnitExporter buildingUnitExporter;
	private AttributeValueSplitter valueSplitter;
	private LodFilter lodFilter;

	public BuildingPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
		StringBuilder stmt = new StringBuilder("select ownername, floorarea, floorarea_uom, energyperforma_certification, energyperform_certificatio_1 from ")
				.append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.BUILDING))).append(" ")
				.append("where id = ?");
		ps = connection.prepareStatement(stmt.toString());

		buildingUnitExporter = manager.getExporter(BuildingUnitExporter.class);
		valueSplitter = helper.getAttributeValueSplitter();
		lodFilter = helper.getLodFilter();
	}

	public void doExport(AbstractBuilding parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
		ps.setLong(1, parentId);

		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				if (projectionFilter.containsProperty("ownerName", TestADEModule.v1_0.getNamespaceURI())) {
					String ownerName = rs.getString(1);
					if (!rs.wasNull()) {
						OwnerNameProperty property = new OwnerNameProperty(ownerName);
						parent.addGenericApplicationPropertyOfAbstractBuilding(property);
					}
				}

				if (projectionFilter.containsProperty("floorArea", TestADEModule.v1_0.getNamespaceURI())) {
					double floorArea = rs.getDouble(2);
					if (!rs.wasNull()) {
						Area area = new Area(floorArea);
						area.setUom(rs.getString(3));
						FloorAreaProperty property = new FloorAreaProperty(area);						
						parent.addGenericApplicationPropertyOfAbstractBuilding(property);
					}
				}

				if (projectionFilter.containsProperty("energyPerformanceCertification", TestADEModule.v1_0.getNamespaceURI())) {
					String id = rs.getString(5);
					String name = rs.getString(4);
					
					if (id != null && name != null) {
						EnergyPerformanceCertification energyCertification = new EnergyPerformanceCertification();
						energyCertification.setCertificationId(id);
						for (SplitValue splitValue : valueSplitter.split(name))
							energyCertification.addCertificationName(splitValue.result(0));

						EnergyPerformanceCertificationProperty property = new EnergyPerformanceCertificationProperty(energyCertification);
						EnergyPerformanceCertificationPropertyElement propertyElement = new EnergyPerformanceCertificationPropertyElement(property);

						parent.addGenericApplicationPropertyOfAbstractBuilding(propertyElement);
					}
				}

				if (projectionFilter.containsProperty("buildingUnit", TestADEModule.v1_0.getNamespaceURI())
						&& lodFilter.containsLodGreaterThanOrEuqalTo(1))
					buildingUnitExporter.doExport(parent, parentId);
			}
		}
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps.close();
	}

}
