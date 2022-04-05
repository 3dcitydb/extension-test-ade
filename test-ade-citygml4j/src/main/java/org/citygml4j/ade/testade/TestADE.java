package org.citygml4j.ade.testade;

import org.citygml4j.ade.testade.module.TestADEModule;
import org.citygml4j.ade.testade.walker.TestADEWalker;
import org.citygml4j.core.visitor.ADEWalker;
import org.citygml4j.xml.ade.CityGMLADE;
import org.citygml4j.xml.module.ade.ADEModule;

import java.util.Collections;
import java.util.List;

public class TestADE implements CityGMLADE {

    @Override
    public List<ADEModule> getADEModules() {
        return Collections.singletonList(new TestADEModule());
    }

    @Override
    public ADEWalker getADEWalker() {
        return new TestADEWalker();
    }
}
