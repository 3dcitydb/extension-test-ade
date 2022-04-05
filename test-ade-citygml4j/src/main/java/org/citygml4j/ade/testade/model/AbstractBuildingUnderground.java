package org.citygml4j.ade.testade.model;

import org.citygml4j.core.model.ade.ADEObject;
import org.citygml4j.core.model.common.LevelOfDetail;
import org.citygml4j.core.model.core.*;
import org.xmlobjects.gml.model.basictypes.Code;
import org.xmlobjects.gml.model.geometry.GeometryProperty;
import org.xmlobjects.gml.model.geometry.aggregates.MultiCurveProperty;
import org.xmlobjects.gml.model.geometry.aggregates.MultiSurfaceProperty;
import org.xmlobjects.gml.model.geometry.primitives.SolidProperty;
import org.xmlobjects.model.ChildList;

import java.util.List;

public abstract class AbstractBuildingUnderground extends AbstractOccupiedSpace implements StandardObjectClassifier, ADEObject {
    private Code classifier;
    private List<Code> functions;
    private List<Code> usages;
    private List<EnergyPerformanceCertificationProperty> energyPerformanceCertifications;
    private GeometryProperty<?> genericGeometry;
    private List<AddressProperty> addresses;
    private List<FacilityProperty> equippedWith;

    @Override
    public boolean isValidBoundary(AbstractSpaceBoundary boundary) {
        return false;
    }

    @Override
    public Code getClassifier() {
        return classifier;
    }

    @Override
    public void setClassifier(Code classifier) {
        this.classifier = asChild(classifier);
    }

    @Override
    public List<Code> getFunctions() {
        if (functions == null)
            functions = new ChildList<>(this);

        return functions;
    }

    @Override
    public boolean isSetFunctions() {
        return functions != null && !functions.isEmpty();
    }

    @Override
    public void setFunctions(List<Code> functions) {
        this.functions = asChild(functions);
    }

    @Override
    public List<Code> getUsages() {
        if (usages == null)
            usages = new ChildList<>(this);

        return usages;
    }

    @Override
    public boolean isSetUsages() {
        return usages != null && !usages.isEmpty();
    }

    @Override
    public void setUsages(List<Code> usages) {
        this.usages = asChild(usages);
    }

    public List<EnergyPerformanceCertificationProperty> getEnergyPerformanceCertifications() {
        if (energyPerformanceCertifications == null)
            energyPerformanceCertifications = new ChildList<>(this);

        return energyPerformanceCertifications;
    }

    public void setEnergyPerformanceCertifications(List<EnergyPerformanceCertificationProperty> energyPerformanceCertifications) {
        this.energyPerformanceCertifications = asChild(energyPerformanceCertifications);
    }

    public GeometryProperty<?> getGenericGeometry() {
        return genericGeometry;
    }

    public void setGenericGeometry(GeometryProperty<?> genericGeometry) {
        this.genericGeometry = asChild(genericGeometry);
    }

    public List<AddressProperty> getAddresses() {
        if (addresses == null)
            addresses = new ChildList<>(this);

        return addresses;
    }

    public void setAddresses(List<AddressProperty> addresses) {
        this.addresses = asChild(addresses);
    }

    public List<FacilityProperty> getEquippedWith() {
        if (equippedWith == null)
            equippedWith = new ChildList<>(this);

        return equippedWith;
    }

    public void setEquippedWith(List<FacilityProperty> equippedWith) {
        this.equippedWith = equippedWith;
    }
}
