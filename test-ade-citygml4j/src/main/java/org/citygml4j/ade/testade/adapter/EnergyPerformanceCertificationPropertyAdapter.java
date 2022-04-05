package org.citygml4j.ade.testade.adapter;

import org.citygml4j.ade.testade.model.EnergyPerformanceCertificationProperty;
import org.xmlobjects.builder.ObjectBuildException;
import org.xmlobjects.gml.adapter.base.AbstractInlinePropertyAdapter;

import javax.xml.namespace.QName;

public class EnergyPerformanceCertificationPropertyAdapter extends AbstractInlinePropertyAdapter<EnergyPerformanceCertificationProperty> {

    @Override
    public EnergyPerformanceCertificationProperty createObject(QName name, Object parent) throws ObjectBuildException {
        return new EnergyPerformanceCertificationProperty();
    }
}
