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
		features.put("IndustrialBuilding", IndustrialBuilding.class);
		features.put("OtherConstruction", OtherConstruction.class);

		featureProperties = new HashSet<>();
		featureProperties.add("buildingUnit");
		featureProperties.add("boundedBy");
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
