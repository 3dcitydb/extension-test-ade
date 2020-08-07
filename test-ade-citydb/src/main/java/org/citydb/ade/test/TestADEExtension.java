package org.citydb.ade.test;

import org.citydb.ImpExp;
import org.citydb.ade.ADEExtension;
import org.citydb.ade.ADEExtensionException;
import org.citydb.ade.ADEObjectMapper;
import org.citydb.ade.exporter.ADEExportManager;
import org.citydb.ade.importer.ADEImportManager;
import org.citydb.ade.test.balloon.BalloonManager;
import org.citydb.ade.test.exporter.ExportManager;
import org.citydb.ade.test.importer.ImportManager;
import org.citydb.ade.test.kmlExporter.KMLExportManager;
import org.citydb.ade.test.schema.ADETableMapper;
import org.citydb.ade.test.schema.ObjectMapper;
import org.citydb.ade.test.schema.SchemaMapper;
import org.citydb.database.schema.mapping.SchemaMapping;
import org.citydb.modules.kml.ade.ADEBalloonManager;
import org.citydb.modules.kml.ade.ADEKmlExportExtension;
import org.citydb.modules.kml.ade.ADEKmlExportManager;
import org.citygml.ade.test.TestADEContext;
import org.citygml4j.model.citygml.ade.binding.ADEContext;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class TestADEExtension extends ADEExtension implements ADEKmlExportExtension {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final SchemaMapper schemaMapper = new SchemaMapper();
	private final ADETableMapper tableMapper = new ADETableMapper();
	private final TestADEContext context = new TestADEContext();

	public static void main(String[] args) throws Exception {
		TestADEExtension adeExtension = new TestADEExtension();
		adeExtension.setBasePath(Paths.get("resources").toAbsolutePath());
		new ImpExp().doMain(args, adeExtension);
	}
	
	@Override
	public void init(SchemaMapping schemaMapping) throws ADEExtensionException {
		objectMapper.populateObjectClassIds(schemaMapping);
		schemaMapper.populateSchemaNames(schemaMapping.getMetadata().getDBPrefix().toLowerCase());
		tableMapper.populateTableColumns(schemaMapper);
	}

	@Override
	public List<ADEContext> getADEContexts() {
		return Collections.singletonList(context);
	}

	@Override
	public ADEObjectMapper getADEObjectMapper() {
		return objectMapper;
	}
	
	@Override
	public ADEImportManager createADEImportManager() {
		return new ImportManager(this, schemaMapper);
	}

	@Override
	public ADEExportManager createADEExportManager() {
		return new ExportManager(schemaMapper);
	}
	
	public SchemaMapper getSchemaMapper() {
		return schemaMapper;
	}

	@Override
	public ADEKmlExportManager createADEKmlExportManager() {
		return new KMLExportManager(schemaMapper);
	}

	@Override
	public ADEBalloonManager createBalloonManager() {
		return new BalloonManager(tableMapper, schemaMapper);
	}

}
