package org.citygml.ade.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.citygml.ade.test.bind.TestADEMarshaller;
import org.citygml.ade.test.bind.TestADEUnmarshaller;
import org.citygml.ade.test.model.module.TestADEModule;
import org.citygml.ade.test.walker.TestADEFeatureFunctionWalker;
import org.citygml.ade.test.walker.TestADEFeatureWalker;
import org.citygml.ade.test.walker.TestADEGMLFunctionWalker;
import org.citygml.ade.test.walker.TestADEGMLWalker;
import org.citygml4j.model.citygml.ade.binding.ADEContext;
import org.citygml4j.model.citygml.ade.binding.ADEMarshaller;
import org.citygml4j.model.citygml.ade.binding.ADEUnmarshaller;
import org.citygml4j.model.citygml.ade.binding.ADEWalker;
import org.citygml4j.model.module.ade.ADEModule;
import org.citygml4j.util.walker.FeatureFunctionWalker;
import org.citygml4j.util.walker.FeatureWalker;
import org.citygml4j.util.walker.GMLFunctionWalker;
import org.citygml4j.util.walker.GMLWalker;

public class TestADEContext implements ADEContext {
	private final TestADEMarshaller marshaller = new TestADEMarshaller();
	private final TestADEUnmarshaller unmarshaller = new TestADEUnmarshaller();
	
	@Override
	public List<ADEModule> getADEModules() {
		return Arrays.asList(new ADEModule[]{TestADEModule.v1_0});
	}

	@Override
	public List<String> getModelPackageNames() {
		return Collections.singletonList("org.citygml.ade.test.model");
	}

	@Override
	public List<String> getJAXBPackageNames() {
		return Collections.singletonList("org.citygml.ade.test._1");
	}

	@Override
	public ADEMarshaller getADEMarshaller() {
		return marshaller;
	}

	@Override
	public ADEUnmarshaller getADEUnmarshaller() {
		return unmarshaller;
	}

	@Override
	public ADEWalker<FeatureWalker> createDefaultFeatureWalker() {
		return new TestADEFeatureWalker();
	}

	@Override
	public ADEWalker<GMLWalker> createDefaultGMLWalker() {
		return new TestADEGMLWalker();
	}

	@Override
	public <T> ADEWalker<FeatureFunctionWalker<T>> createDefaultFeatureFunctionWalker() {
		return new TestADEFeatureFunctionWalker<>();
	}

	@Override
	public <T> ADEWalker<GMLFunctionWalker<T>> createDefaultGMLFunctionWalker() {
		return new TestADEGMLFunctionWalker<>();
	}

}
