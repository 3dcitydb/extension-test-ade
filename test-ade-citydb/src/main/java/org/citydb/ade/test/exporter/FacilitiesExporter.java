package org.citydb.ade.test.exporter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.ade.test.schema.ADETables;
import org.citydb.citygml.exporter.CityGMLExportException;
import org.citygml.ade.test.model.AbstractBuildingUnit;
import org.citygml.ade.test.model.AbstractFacilities;
import org.citygml.ade.test.model.FacilitiesProperty;
import org.citygml4j.model.gml.basicTypes.Measure;

public class FacilitiesExporter implements ADEExporter {
	private final CityGMLExportHelper helper;
	
	private PreparedStatement ps;

	public FacilitiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws SQLException {
		this.helper = helper;
		
		StringBuilder stmt = new StringBuilder("select id, objectclass_id, totalvalue, totalvalue_uom from ")
				.append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.FACILITIES))).append(" ")
				.append("where buildingunit_equippedwith_id = ?");
		ps = connection.prepareStatement(stmt.toString());
	}

	public void doExport(AbstractBuildingUnit parent, long parentId) throws CityGMLExportException, SQLException {
		ps.setLong(1, parentId);

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				double totalValue = rs.getDouble(3);
				if (rs.wasNull())
					continue;				
				
				long facilitiesId = rs.getLong(1);
				int objectClassId = rs.getInt(2);
				
				AbstractFacilities facilities = helper.createObjectStub(facilitiesId, objectClassId, AbstractFacilities.class);
				if (facilities == null) {
					helper.logOrThrowErrorMessage("Failed to instantiate " + helper.getObjectSignature(objectClassId, facilitiesId) + " as facilities object.");
					continue;
				}

				Measure measure = new Measure(totalValue);
				measure.setUom(rs.getString(4));
				facilities.setTotalValue(measure);
				
				parent.addEquippedWith(new FacilitiesProperty(facilities));
			}
		}
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps.close();	
	}

}
