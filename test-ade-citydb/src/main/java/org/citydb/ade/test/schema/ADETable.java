package org.citydb.ade.test.schema;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.test.importer.BuildingPropertiesImporter;
import org.citydb.ade.test.importer.IndustrialBuildingImporter;
import org.citydb.ade.test.importer.OtherConstructionImporter;
import org.citydb.ade.test.importer.OtherConstructionToThematicSurfaceImporter;

public enum ADETable {
	INDUSTRIALBUILDING(IndustrialBuildingImporter.class),
	BUILDING(BuildingPropertiesImporter.class),
	OTHERCONSTRUCTION(OtherConstructionImporter.class),
	OTHER_TO_THEMA_SURFA(OtherConstructionToThematicSurfaceImporter.class);	
	
	private final Class<? extends ADEImporter> importerClass;
	
	ADETable(Class<? extends ADEImporter> importerClass) {
		this.importerClass = importerClass;
	}
	
	public Class<? extends ADEImporter> getImporterClass() {
		return importerClass;
	}
	
}
