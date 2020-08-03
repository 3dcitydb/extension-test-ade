package org.citydb.ade.test.exporter;

import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.citygml.exporter.CityGMLExportException;
import org.citydb.database.schema.mapping.AbstractType;
import org.citydb.database.schema.mapping.FeatureProperty;
import org.citydb.query.filter.projection.ProjectionFilter;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml.ade.test.model.module.TestADEModule;
import org.citygml4j.model.citygml.building.AbstractBoundarySurface;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;

import java.sql.SQLException;

public class OtherConstructionExporter implements ADEExporter {
	private final CityGMLExportHelper helper;

	public OtherConstructionExporter(CityGMLExportHelper helper) {
		this.helper = helper;
	}

	public void doExport(OtherConstruction otherConstruction, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
		if (projectionFilter.containsProperty("boundedBy")) {
			FeatureProperty property = objectType.getFeatureProperty("boundedBy", TestADEModule.v1_0.getNamespaceURI(), false);
			if (property != null) {
				for (AbstractBoundarySurface surface : helper.exportNestedCityGMLObjects(property, objectId, AbstractBoundarySurface.class))
					otherConstruction.addBoundedBySurface(new BoundarySurfaceProperty(surface));
			}
		}
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		// nothing to do
	}

}
