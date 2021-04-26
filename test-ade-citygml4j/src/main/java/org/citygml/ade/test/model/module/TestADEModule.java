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

package org.citygml.ade.test.model.module;

import org.citygml.ade.test.TestADEContext;
import org.citygml.ade.test.model.*;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.module.ade.ADEModule;
import org.citygml4j.model.module.citygml.CityGMLVersion;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

public class TestADEModule extends ADEModule {
	public static final TestADEModule v1_0 = new TestADEModule();
	
	private HashMap<String, Class<? extends AbstractFeature>> features;
	private HashSet<String> featureProperties;

	private TestADEModule() {
		super("http://www.citygml.org/ade/TestADE/1.0", 
				"test", 
				CityGMLVersion.v2_0_0);

		features = new HashMap<>();
		features.put("_AbstractBuildingUnit", AbstractBuildingUnit.class);
		features.put("BuildingUnit", BuildingUnit.class);
		features.put("BuildingUnitPart", BuildingUnitPart.class);
		features.put("Facilities", AbstractFacilities.class);
		features.put("DHWFacilities", DHWFacilities.class);
		features.put("LightingFacilities", LightingFacilities.class);
		features.put("IndustrialBuilding", IndustrialBuilding.class);		
		features.put("IndustrialBuildingPart", IndustrialBuildingPart.class);
		features.put("IndustrialBuildingRoofSurface", IndustrialBuildingRoofSurface.class);
		features.put("OtherConstruction", OtherConstruction.class);

		featureProperties = new HashSet<>();
		featureProperties.add("buildingUnit");
		featureProperties.add("boundedBy");
		featureProperties.add("equippedWith");
		featureProperties.add("consistsOf");
	}

	@Override
	public URL getSchemaResource() {
		return TestADEContext.class.getResource("/org/citygml/ade/test/schema/CityGML-TestADE.xsd");
	}

	@Override
	public List<String> getJAXBPackageNames() {
		return Collections.singletonList("org.citygml.ade.test._1");
	}

	@Override
	public boolean hasFeatureProperty(String name) {
		return featureProperties.contains(name);
	}

	@Override
	public boolean hasFeature(String name) {
		return features.containsKey(name);
	}

	@Override
	public Class<? extends AbstractFeature> getFeatureClass(String name) {
		return features.get(name);
	}

	@Override
	public QName getFeatureName(Class<? extends AbstractFeature> featureClass) {
		for (Entry<String, Class<? extends AbstractFeature>> entry : features.entrySet()) {
			if (entry.getValue() == featureClass)
				return new QName(getNamespaceURI(), entry.getKey());
		}

		return null;
	}

	@Override
	public Map<String, Class<? extends AbstractFeature>> getFeatures() {
		return new HashMap<>(features);
	}

	@Override
	public boolean isTopLevelFeature(String name) {
		return "IndustrialBuilding".equals(name)
				|| "OtherConstruction".equals(name);
	}

}
