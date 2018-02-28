package org.citygml.ade.test.model.module;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;

import org.citygml.ade.test.TestADEContext;
import org.citygml.ade.test.model.AbstractBuildingUnit;
import org.citygml.ade.test.model.AbstractFacilities;
import org.citygml.ade.test.model.BuildingUnit;
import org.citygml.ade.test.model.BuildingUnitPart;
import org.citygml.ade.test.model.DHWFacilities;
import org.citygml.ade.test.model.IndustrialBuilding;
import org.citygml.ade.test.model.IndustrialBuildingPart;
import org.citygml.ade.test.model.IndustrialBuildingRoofSurface;
import org.citygml.ade.test.model.LightingFacilities;
import org.citygml.ade.test.model.OtherConstruction;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.module.ade.ADEModule;
import org.citygml4j.model.module.citygml.CityGMLVersion;

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
		Iterator<Entry<String, Class<? extends AbstractFeature>>> iter = features.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Class<? extends AbstractFeature>> entry = iter.next();
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
