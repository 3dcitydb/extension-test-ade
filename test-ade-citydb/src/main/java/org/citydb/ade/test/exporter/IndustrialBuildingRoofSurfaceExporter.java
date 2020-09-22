package org.citydb.ade.test.exporter;

import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.citygml.exporter.CityGMLExportException;
import org.citydb.database.schema.mapping.AbstractType;
import org.citydb.query.filter.projection.ProjectionFilter;
import org.citygml.ade.test.model.IndustrialBuildingRoofSurface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IndustrialBuildingRoofSurfaceExporter implements ADEExporter {
	private final PreparedStatement ps;

	public IndustrialBuildingRoofSurfaceExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws SQLException {
		ps = connection.prepareStatement("select remark from " +
				helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.INDUSTRIALBUILDINGRO)) + " " +
				"where id = ?");
	}

	public void doExport(IndustrialBuildingRoofSurface roofSurface, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
		// we only have one attribute, so only issue a query if this attribute
		// shall be exported
		if (projectionFilter.containsProperty("remark")) {
			ps.setLong(1, objectId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					roofSurface.setRemark(rs.getString(1));
				}
			}
		}
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps.close();
	}

}
