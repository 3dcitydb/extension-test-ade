package org.citydb.ade.test.exporter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.citygml.exporter.CityGMLExportException;
import org.citydb.citygml.exporter.database.content.GMLConverter;
import org.citydb.citygml.exporter.database.content.SurfaceGeometry;
import org.citydb.citygml.exporter.util.AttributeValueSplitter;
import org.citydb.citygml.exporter.util.AttributeValueSplitter.SplitValue;
import org.citydb.database.schema.mapping.FeatureProperty;
import org.citydb.database.schema.mapping.FeatureType;
import org.citydb.query.filter.lod.LodFilter;
import org.citydb.query.filter.lod.LodIterator;
import org.citydb.query.filter.projection.ProjectionFilter;
import org.citygml.ade.test.model.AbstractBuildingUnit;
import org.citygml.ade.test.model.BuildingUnit;
import org.citygml.ade.test.model.BuildingUnitPart;
import org.citygml.ade.test.model.BuildingUnitPartProperty;
import org.citygml.ade.test.model.BuildingUnitProperty;
import org.citygml.ade.test.model.BuildingUnitPropertyElement;
import org.citygml.ade.test.model.module.TestADEModule;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.citygml.core.Address;
import org.citygml4j.model.citygml.core.AddressProperty;
import org.citygml4j.model.gml.GMLClass;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.geometry.aggregates.MultiCurveProperty;
import org.citygml4j.model.gml.geometry.aggregates.MultiSurface;
import org.citygml4j.model.gml.geometry.aggregates.MultiSurfaceProperty;
import org.citygml4j.model.gml.geometry.primitives.AbstractSolid;
import org.citygml4j.model.gml.geometry.primitives.SolidProperty;

public class BuildingUnitExporter implements ADEExporter {
	private final CityGMLExportHelper helper;

	private PreparedStatement ps;
	private FacilitiesExporter facilitiesExporter;
	private EnergyPerformanceCertificationExporter energyCertificationExporter;
	private GMLConverter gmlConverter;
	private AttributeValueSplitter valueSplitter;
	private LodFilter lodFilter;

	public BuildingUnitExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
		this.helper = helper;

		StringBuilder stmt = new StringBuilder("select id, objectclass_id, buildingunit_parent_id, class, class_codespace, ")
				.append("usage, usage_codespace, function, function_codespace, ") 
				.append(helper.getGeometryColumn("lod2multicurve")).append(", ")
				.append(helper.getGeometryColumn("lod3multicurve")).append(", ")
				.append(helper.getGeometryColumn("lod4multicurve")).append(", ")
				.append("lod1multisurface_id, lod2multisurface_id, lod3multisurface_id, lod4multisurface_id, ")
				.append("lod1solid_id, lod2solid_id, lod3solid_id, lod4solid_id from ")
				.append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDINGUNIT))).append(" ")
				.append("where building_buildingunit_id = ?");
		ps = connection.prepareStatement(stmt.toString());

		facilitiesExporter = manager.getExporter(FacilitiesExporter.class);
		energyCertificationExporter = manager.getExporter(EnergyPerformanceCertificationExporter.class);
		gmlConverter = helper.getGMLConverter();
		valueSplitter = helper.getAttributeValueSplitter();
		lodFilter = helper.getLodFilter();
	}

	public void doExport(AbstractBuilding parent, long parentId) throws CityGMLExportException, SQLException {
		ps.setLong(1, parentId);

		try (ResultSet rs = ps.executeQuery()) {
			Map<Long, AbstractBuildingUnit> buildingUnits = new HashMap<>();

			while (rs.next()) {
				long buildingUnitId = rs.getLong(1);
				int objectClassId = rs.getInt(2);

				AbstractBuildingUnit buildingUnit = helper.createObjectStub(buildingUnitId, objectClassId, AbstractBuildingUnit.class);
				if (buildingUnit == null) {
					helper.logOrThrowErrorMessage("Failed to instantiate " + helper.getObjectSignature(objectClassId, buildingUnitId) + " as building unit object.");
					continue;
				}

				FeatureType featureType = helper.getFeatureType(objectClassId);
				ProjectionFilter projectionFilter = helper.getProjectionFilter(featureType);

				if (projectionFilter.containsProperty("class")) {
					String clazz = rs.getString(4);
					if (!rs.wasNull()) {
						Code code = new Code(clazz);
						code.setCodeSpace(rs.getString(5));
						buildingUnit.setClazz(code);
					}
				}

				if (projectionFilter.containsProperty("usage")) {
					for (SplitValue splitValue : valueSplitter.split(rs.getString(6), rs.getString(7))) {
						Code usage = new Code(splitValue.result(0));
						usage.setCodeSpace(splitValue.result(1));
						buildingUnit.addUsage(usage);
					}
				}

				if (projectionFilter.containsProperty("function")) {
					for (SplitValue splitValue : valueSplitter.split(rs.getString(8), rs.getString(9))) {
						Code function = new Code(splitValue.result(0));
						function.setCodeSpace(splitValue.result(1));
						buildingUnit.addFunction(function);
					}
				}

				if (projectionFilter.containsProperty("equippedWith"))
					facilitiesExporter.doExport(buildingUnit, buildingUnitId);

				if (projectionFilter.containsProperty("energyPerformanceCertification"))
					energyCertificationExporter.doExport(buildingUnit, buildingUnitId);

				if (projectionFilter.containsProperty("address")) {
					FeatureProperty property = featureType.getFeatureProperty("address", TestADEModule.v1_0.getNamespaceURI(), true);
					if (property != null) {
						for (Address address : helper.exportNestedCityGMLObjects(property, buildingUnitId, Address.class))
							buildingUnit.addAddress(new AddressProperty(address));
					}
				}

				LodIterator lodIterator = lodFilter.iterator(2, 4);
				while (lodIterator.hasNext()) {
					int lod = lodIterator.next();

					if (!projectionFilter.containsProperty(new StringBuilder("lod").append(lod).append("MultiCurve").toString()))
						continue;

					Object multiCurveObj = rs.getObject(new StringBuilder("lod").append(lod).append("multicurve").toString());
					if (rs.wasNull())
						continue;

					GeometryObject multiCurve = helper.getDatabaseAdapter().getGeometryConverter().getMultiCurve(multiCurveObj);
					if (multiCurve != null) {
						MultiCurveProperty multiCurveProperty = gmlConverter.getMultiCurveProperty(multiCurve, false);
						if (multiCurveProperty != null) {
							switch (lod) {
							case 2:
								buildingUnit.setLod2MultiCurve(multiCurveProperty);
								break;
							case 3:
								buildingUnit.setLod3MultiCurve(multiCurveProperty);
								break;
							case 4:
								buildingUnit.setLod4MultiCurve(multiCurveProperty);
								break;
							}
						}
					}
				}

				lodIterator = lodFilter.iterator(1, 4);
				while (lodIterator.hasNext()) {
					int lod = lodIterator.next();

					if (!projectionFilter.containsProperty(new StringBuilder("lod").append(lod).append("MultiSurface").toString()))
						continue;

					long surfaceGeometryId = rs.getLong(new StringBuilder("lod").append(lod).append("multisurface_id").toString());
					if (rs.wasNull())
						continue;

					SurfaceGeometry geometry = helper.exportSurfaceGeometry(surfaceGeometryId);
					if (geometry != null && geometry.getType() == GMLClass.MULTI_SURFACE) {
						MultiSurfaceProperty multiSurfaceProperty = new MultiSurfaceProperty();
						if (geometry.isSetGeometry())
							multiSurfaceProperty.setMultiSurface((MultiSurface)geometry.getGeometry());
						else
							multiSurfaceProperty.setHref(geometry.getReference());

						switch (lod) {
						case 1:
							buildingUnit.setLod1MultiSurface(multiSurfaceProperty);
							break;
						case 2:
							buildingUnit.setLod2MultiSurface(multiSurfaceProperty);
							break;
						case 3:
							buildingUnit.setLod3MultiSurface(multiSurfaceProperty);
							break;
						case 4:
							buildingUnit.setLod4MultiSurface(multiSurfaceProperty);
							break;
						}
					}
				}

				lodIterator.reset();
				while (lodIterator.hasNext()) {
					int lod = lodIterator.next();

					if (!projectionFilter.containsProperty(new StringBuilder("lod").append(lod).append("Solid").toString()))
						continue;

					long surfaceGeometryId = rs.getLong(new StringBuilder("lod").append(lod).append("solid_id").toString());
					if (rs.wasNull())
						continue;

					SurfaceGeometry geometry = helper.exportSurfaceGeometry(surfaceGeometryId);
					if (geometry != null && (geometry.getType() == GMLClass.SOLID || geometry.getType() == GMLClass.COMPOSITE_SOLID)) {
						SolidProperty solidProperty = new SolidProperty();
						if (geometry.isSetGeometry())
							solidProperty.setSolid((AbstractSolid)geometry.getGeometry());
						else
							solidProperty.setHref(geometry.getReference());

						switch (lod) {
						case 1:
							buildingUnit.setLod1Solid(solidProperty);
							break;
						case 2:
							buildingUnit.setLod2Solid(solidProperty);
							break;
						case 3:
							buildingUnit.setLod3Solid(solidProperty);
							break;
						case 4:
							buildingUnit.setLod4Solid(solidProperty);
							break;
						}
					}
				}

				buildingUnit.setLocalProperty("parent", rs.getLong(3));
				buildingUnits.put(buildingUnitId, buildingUnit);
			}

			// rebuild building unit hierarchy
			List<AbstractBuildingUnit> result = new ArrayList<>();
			for (Entry<Long, AbstractBuildingUnit> entry : buildingUnits.entrySet()) {
				long buildingUnitId = entry.getKey();
				AbstractBuildingUnit buildingUnit = entry.getValue();
				if (buildingUnit instanceof BuildingUnit)
					result.add(buildingUnit);

				else {				
					long buildingUnitParentId = (Long)buildingUnit.getLocalProperty("parent");
					AbstractBuildingUnit parentBuildingUnit = buildingUnits.get(buildingUnitParentId);
					if (parentBuildingUnit == null) {
						helper.logOrThrowErrorMessage("Failed to find parent for building unit " + helper.getObjectSignature(helper.getFeatureType(buildingUnit), buildingUnitId) + ".");
						continue;
					}

					parentBuildingUnit.addConsistsOf(new BuildingUnitPartProperty((BuildingUnitPart)buildingUnit));
				}
			}

			// check whether lod filter is satisfied
			if (!lodFilter.preservesGeometry()) {
				for (Iterator<AbstractBuildingUnit> iter = result.iterator(); iter.hasNext(); ) {
					if (!helper.satisfiesLodFilter(iter.next()))
						iter.remove();
				}
			}

			for (AbstractBuildingUnit buildingUnit : result) {			
				BuildingUnitProperty property = new BuildingUnitProperty(buildingUnit);
				BuildingUnitPropertyElement propertyElement = new BuildingUnitPropertyElement(property);
				parent.addGenericApplicationPropertyOfAbstractBuilding(propertyElement);
			}
		}
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps.close();
	}

}
