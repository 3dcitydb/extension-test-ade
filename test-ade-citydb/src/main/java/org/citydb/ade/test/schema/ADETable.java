package org.citydb.ade.test.schema;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.test.importer.*;

public enum ADETable {
	INDUSTRIALBUILDING(IndustrialBuildingImporter.class),
	INDUSTRIALBUILDINGPA(IndustrialBuildingPartImporter.class),
	INDUSTRIALBUILDINGRO(IndustrialBuildingRoofSurfaceImporter.class),
	BUILDINGUNIT(BuildingUnitImporter.class),
	BUILDINGU_TO_ADDRESS(BuildingUnitToAddressImporter.class),
	FACILITIES(FacilitiesImporter.class),
	BUILDING(BuildingPropertiesImporter.class),
	ENERGYPERFORMANCECER(EnergyPerformanceCertificationImporter.class),
	OTHERCONSTRUCTION(OtherConstructionImporter.class),
	OTHER_TO_THEMA_SURFA(OtherConstructionToThematicSurfaceImporter.class);	
	
	private Class<? extends ADEImporter> importerClass;
	
	private ADETable(Class<? extends ADEImporter> importerClass) {
		this.importerClass = importerClass;
	}
	
	public Class<? extends ADEImporter> getImporterClass() {
		return importerClass;
	}
	
}
