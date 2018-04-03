package org.citydb.ade.test.schema;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.test.importer.BuildingPropertiesImporter;
import org.citydb.ade.test.importer.BuildingUnitImporter;
import org.citydb.ade.test.importer.BuildingUnitToAddressImporter;
import org.citydb.ade.test.importer.EnergyPerformanceCertificationImporter;
import org.citydb.ade.test.importer.FacilitiesImporter;
import org.citydb.ade.test.importer.IndustrialBuildingImporter;
import org.citydb.ade.test.importer.IndustrialBuildingPartImporter;
import org.citydb.ade.test.importer.IndustrialBuildingRoofSurfaceImporter;
import org.citydb.ade.test.importer.OtherConstructionImporter;
import org.citydb.ade.test.importer.OtherConstructionToThematicSurfaceImporter;

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
