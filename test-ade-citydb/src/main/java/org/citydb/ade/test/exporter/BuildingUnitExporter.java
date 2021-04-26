/*
 * 3D City Database - The Open Source CityGML Database
 * https://www.3dcitydb.org/
 *
 * Copyright 2013 - 2021
 * Chair of Geoinformatics
 * Technical University of Munich, Germany
 * https://www.lrg.tum.de/gis/
 *
 * The 3D City Database is jointly developed with the following
 * cooperation partners:
 *
 * Virtual City Systems, Berlin <https://vc.systems/>
 * M.O.S.S. Computer Grafik Systeme GmbH, Taufkirchen <http://www.moss.de/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.citydb.ade.test.exporter;

import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.ade.test.schema.ADETable;
import org.citydb.citygml.exporter.CityGMLExportException;
import org.citydb.citygml.exporter.database.content.GMLConverter;
import org.citydb.citygml.exporter.database.content.SurfaceGeometryExporter;
import org.citydb.citygml.exporter.util.AttributeValueSplitter;
import org.citydb.citygml.exporter.util.AttributeValueSplitter.SplitValue;
import org.citydb.config.geometry.GeometryObject;
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
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.geometry.aggregates.MultiCurveProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BuildingUnitExporter implements ADEExporter {
	private final CityGMLExportHelper helper;
	private final PreparedStatement ps;
	private final FacilitiesExporter facilitiesExporter;
	private final EnergyPerformanceCertificationExporter energyCertificationExporter;
	private final SurfaceGeometryExporter surfaceGeometryExporter;
	private final GMLConverter gmlConverter;
	private final AttributeValueSplitter valueSplitter;
	private final LodFilter lodFilter;

	public BuildingUnitExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
		this.helper = helper;

		ps = connection.prepareStatement("select id, objectclass_id, buildingunit_parent_id, class, class_codespace, " +
				"usage, usage_codespace, function, function_codespace, " +
				helper.getGeometryColumn("lod2multicurve") + ", " +
				helper.getGeometryColumn("lod3multicurve") + ", " +
				helper.getGeometryColumn("lod4multicurve") + ", " +
				"lod1multisurface_id, lod2multisurface_id, lod3multisurface_id, lod4multisurface_id, " +
				"lod1solid_id, lod2solid_id, lod3solid_id, lod4solid_id from " +
				helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDINGUNIT)) + " " +
				"where building_buildingunit_id = ?");

		facilitiesExporter = manager.getExporter(FacilitiesExporter.class);
		energyCertificationExporter = manager.getExporter(EnergyPerformanceCertificationExporter.class);
		surfaceGeometryExporter = helper.getSurfaceGeometryExporter();
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

				AbstractBuildingUnit buildingUnit = helper.createObject(buildingUnitId, objectClassId, AbstractBuildingUnit.class);
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

					if (!projectionFilter.containsProperty("lod" + lod + "MultiCurve"))
						continue;

					Object multiCurveObj = rs.getObject("lod" + lod + "multicurve");
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

					if (!projectionFilter.containsProperty("lod" + lod + "MultiSurface"))
						continue;

					long surfaceGeometryId = rs.getLong("lod" + lod + "multisurface_id");
					if (rs.wasNull())
						continue;

					switch (lod) {
						case 1:
							surfaceGeometryExporter.addBatch(surfaceGeometryId, buildingUnit::setLod1MultiSurface);
							break;
						case 2:
							surfaceGeometryExporter.addBatch(surfaceGeometryId, buildingUnit::setLod2MultiSurface);
							break;
						case 3:
							surfaceGeometryExporter.addBatch(surfaceGeometryId, buildingUnit::setLod3MultiSurface);
							break;
						case 4:
							surfaceGeometryExporter.addBatch(surfaceGeometryId, buildingUnit::setLod4MultiSurface);
							break;
					}
				}

				lodIterator.reset();
				while (lodIterator.hasNext()) {
					int lod = lodIterator.next();

					if (!projectionFilter.containsProperty("lod" + lod + "Solid"))
						continue;

					long surfaceGeometryId = rs.getLong("lod" + lod + "solid_id");
					if (rs.wasNull())
						continue;

					switch (lod) {
						case 1:
							surfaceGeometryExporter.addBatch(surfaceGeometryId, buildingUnit::setLod1Solid);
							break;
						case 2:
							surfaceGeometryExporter.addBatch(surfaceGeometryId, buildingUnit::setLod2Solid);
							break;
						case 3:
							surfaceGeometryExporter.addBatch(surfaceGeometryId, buildingUnit::setLod3Solid);
							break;
						case 4:
							surfaceGeometryExporter.addBatch(surfaceGeometryId, buildingUnit::setLod4Solid);
							break;
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
