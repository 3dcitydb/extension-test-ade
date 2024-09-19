/*
 * 3D City Database - The Open Source CityGML Database
 * https://www.3dcitydb.org/
 *
 * Copyright 2013 - 2024
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

package org.citygml.ade.test.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.citygml.core.AddressProperty;
import org.citygml4j.model.citygml.core.LodRepresentation;
import org.citygml4j.model.common.child.ChildList;
import org.citygml4j.model.common.visitor.FeatureFunctor;
import org.citygml4j.model.common.visitor.FeatureVisitor;
import org.citygml4j.model.common.visitor.GMLFunctor;
import org.citygml4j.model.common.visitor.GMLVisitor;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.feature.BoundingShape;
import org.citygml4j.model.gml.geometry.AbstractGeometry;
import org.citygml4j.model.gml.geometry.GeometryProperty;
import org.citygml4j.model.gml.geometry.aggregates.MultiCurveProperty;
import org.citygml4j.model.gml.geometry.aggregates.MultiSurfaceProperty;
import org.citygml4j.model.gml.geometry.primitives.SolidProperty;
import org.citygml4j.util.bbox.BoundingBoxOptions;

import java.util.List;

public abstract class AbstractBuildingUnit extends AbstractCityObject implements ADEModelObject {
    private Code clazz;
    private List<Code> function;
    private List<Code> usage;
    private List<EnergyPerformanceCertificationProperty> energyPerformanceCertification;
    private SolidProperty lod1Solid;
    private SolidProperty lod2Solid;
    private SolidProperty lod3Solid;
    private SolidProperty lod4Solid;
    private MultiCurveProperty lod2MultiCurve;
    private MultiCurveProperty lod3MultiCurve;
    private MultiCurveProperty lod4MultiCurve;
    private MultiSurfaceProperty lod1MultiSurface;
    private MultiSurfaceProperty lod2MultiSurface;
    private MultiSurfaceProperty lod3MultiSurface;
    private MultiSurfaceProperty lod4MultiSurface;
    private List<AddressProperty> address;
    private List<FacilitiesProperty> equippedWith;
    private List<BuildingUnitPartProperty> consistsOf;

    public Code getClazz() {
        return clazz;
    }

    public boolean isSetClazz() {
        return clazz != null;
    }

    public void setClazz(Code clazz) {
        this.clazz = clazz;
    }

    public void addFunction(Code function) {
        if (this.function == null)
            this.function = new ChildList<Code>(this);

        this.function.add(function);
    }

    public List<Code> getFunction() {
        if (function == null)
            function = new ChildList<Code>(this);

        return function;
    }

    public boolean isSetFunction() {
        return function != null && !function.isEmpty();
    }

    public void setFunction(List<Code> function) {
        this.function = new ChildList<Code>(this, function);
    }

    public void addUsage(Code function) {
        if (this.usage == null)
            this.usage = new ChildList<Code>(this);

        this.usage.add(function);
    }

    public List<Code> getUsage() {
        if (usage == null)
            usage = new ChildList<Code>(this);

        return usage;
    }

    public boolean isSetUsage() {
        return usage != null && !usage.isEmpty();
    }

    public void setUsage(List<Code> usage) {
        this.usage = new ChildList<Code>(this, usage);
    }

    public void addEnergyPerformanceCertification(EnergyPerformanceCertificationProperty energyPerformanceCertification) {
        if (this.energyPerformanceCertification == null)
            this.energyPerformanceCertification = new ChildList<EnergyPerformanceCertificationProperty>(this);

        this.energyPerformanceCertification.add(energyPerformanceCertification);
    }

    public List<EnergyPerformanceCertificationProperty> getEnergyPerformanceCertification() {
        if (energyPerformanceCertification == null)
            energyPerformanceCertification = new ChildList<EnergyPerformanceCertificationProperty>(this);

        return energyPerformanceCertification;
    }

    public boolean isSetEnergyPerformanceCertification() {
        return energyPerformanceCertification != null && !energyPerformanceCertification.isEmpty();
    }

    public void setEnergyPerformanceCertification(List<EnergyPerformanceCertificationProperty> energyPerformanceCertification) {
        this.energyPerformanceCertification = new ChildList<EnergyPerformanceCertificationProperty>(this, energyPerformanceCertification);
    }

    public void addEquippedWith(FacilitiesProperty equippedWith) {
        if (this.equippedWith == null)
            this.equippedWith = new ChildList<FacilitiesProperty>(this);

        this.equippedWith.add(equippedWith);
    }

    public List<FacilitiesProperty> getEquippedWith() {
        if (equippedWith == null)
            equippedWith = new ChildList<FacilitiesProperty>(this);

        return equippedWith;
    }

    public boolean isSetEquippedWith() {
        return equippedWith != null && !equippedWith.isEmpty();
    }

    public void setEquippedWith(List<FacilitiesProperty> equippedWith) {
        this.equippedWith = new ChildList<FacilitiesProperty>(this, equippedWith);
    }

    public void addAddress(AddressProperty address) {
        if (this.address == null)
            this.address = new ChildList<AddressProperty>(this);

        this.address.add(address);
    }

    public List<AddressProperty> getAddress() {
        if (address == null)
            address = new ChildList<AddressProperty>(this);

        return address;
    }

    public boolean isSetAddress() {
        return address != null && !address.isEmpty();
    }

    public void setAddress(List<AddressProperty> address) {
        this.address = new ChildList<AddressProperty>(this, address);
    }

    public void addConsistsOf(BuildingUnitPartProperty consistsOf) {
        if (this.consistsOf == null)
            this.consistsOf = new ChildList<BuildingUnitPartProperty>(this);

        this.consistsOf.add(consistsOf);
    }

    public List<BuildingUnitPartProperty> getConsistsOf() {
        if (consistsOf == null)
            consistsOf = new ChildList<BuildingUnitPartProperty>(this);

        return consistsOf;
    }

    public boolean isSetConsistsOf() {
        return consistsOf != null && !consistsOf.isEmpty();
    }

    public void setConsistsOf(List<BuildingUnitPartProperty> consistsOf) {
        this.consistsOf = new ChildList<BuildingUnitPartProperty>(this, consistsOf);
    }

    public MultiCurveProperty getLod2MultiCurve() {
        return lod2MultiCurve;
    }

    public boolean isSetLod2MultiCurve() {
        return lod2MultiCurve != null;
    }

    public void setLod2MultiCurve(MultiCurveProperty lod2MultiCurve) {
        if (lod2MultiCurve != null)
            lod2MultiCurve.setParent(this);

        this.lod2MultiCurve = lod2MultiCurve;
    }

    public MultiCurveProperty getLod3MultiCurve() {
        return lod3MultiCurve;
    }

    public boolean isSetLod3MultiCurve() {
        return lod3MultiCurve != null;
    }

    public void setLod3MultiCurve(MultiCurveProperty lod3MultiCurve) {
        if (lod3MultiCurve != null)
            lod3MultiCurve.setParent(this);

        this.lod3MultiCurve = lod3MultiCurve;
    }

    public MultiCurveProperty getLod4MultiCurve() {
        return lod4MultiCurve;
    }

    public boolean isSetLod4MultiCurve() {
        return lod4MultiCurve != null;
    }

    public void setLod4MultiCurve(MultiCurveProperty lod4MultiCurve) {
        if (lod4MultiCurve != null)
            lod4MultiCurve.setParent(this);

        this.lod4MultiCurve = lod4MultiCurve;
    }

    public SolidProperty getLod1Solid() {
        return lod1Solid;
    }

    public boolean isSetLod1Solid() {
        return lod1Solid != null;
    }

    public void setLod1Solid(SolidProperty lod1Solid) {
        if (lod1Solid != null)
            lod1Solid.setParent(this);

        this.lod1Solid = lod1Solid;
    }

    public SolidProperty getLod2Solid() {
        return lod2Solid;
    }

    public boolean isSetLod2Solid() {
        return lod2Solid != null;
    }

    public void setLod2Solid(SolidProperty lod2Solid) {
        if (lod2Solid != null)
            lod2Solid.setParent(this);

        this.lod2Solid = lod2Solid;
    }

    public SolidProperty getLod3Solid() {
        return lod3Solid;
    }

    public boolean isSetLod3Solid() {
        return lod3Solid != null;
    }

    public void setLod3Solid(SolidProperty lod3Solid) {
        if (lod3Solid != null)
            lod3Solid.setParent(this);

        this.lod3Solid = lod3Solid;
    }

    public SolidProperty getLod4Solid() {
        return lod4Solid;
    }

    public boolean isSetLod4Solid() {
        return lod4Solid != null;
    }

    public void setLod4Solid(SolidProperty lod4Solid) {
        if (lod4Solid != null)
            lod4Solid.setParent(this);

        this.lod4Solid = lod4Solid;
    }

    public MultiSurfaceProperty getLod1MultiSurface() {
        return lod1MultiSurface;
    }

    public boolean isSetLod1MultiSurface() {
        return lod1MultiSurface != null;
    }

    public void setLod1MultiSurface(MultiSurfaceProperty lod1MultiSurface) {
        if (lod1MultiSurface != null)
            lod1MultiSurface.setParent(this);

        this.lod1MultiSurface = lod1MultiSurface;
    }

    public MultiSurfaceProperty getLod2MultiSurface() {
        return lod2MultiSurface;
    }

    public boolean isSetLod2MultiSurface() {
        return lod2MultiSurface != null;
    }

    public void setLod2MultiSurface(MultiSurfaceProperty lod2MultiSurface) {
        if (lod2MultiSurface != null)
            lod2MultiSurface.setParent(this);

        this.lod2MultiSurface = lod2MultiSurface;
    }

    public MultiSurfaceProperty getLod3MultiSurface() {
        return lod3MultiSurface;
    }

    public boolean isSetLod3MultiSurface() {
        return lod3MultiSurface != null;
    }

    public void setLod3MultiSurface(MultiSurfaceProperty lod3MultiSurface) {
        if (lod3MultiSurface != null)
            lod3MultiSurface.setParent(this);

        this.lod3MultiSurface = lod3MultiSurface;
    }

    public MultiSurfaceProperty getLod4MultiSurface() {
        return lod4MultiSurface;
    }

    public boolean isSetLod4MultiSurface() {
        return lod4MultiSurface != null;
    }

    public void setLod4MultiSurface(MultiSurfaceProperty lod4MultiSurface) {
        if (lod4MultiSurface != null)
            lod4MultiSurface.setParent(this);

        this.lod4MultiSurface = lod4MultiSurface;
    }

    @Override
    public BoundingShape calcBoundedBy(BoundingBoxOptions options) {
        BoundingShape boundedBy = super.calcBoundedBy(options);
        if (options.isUseExistingEnvelopes() && !boundedBy.isEmpty())
            return boundedBy;

        SolidProperty solidProperty = null;
        for (int lod = 1; lod < 5; lod++) {
            switch (lod) {
                case 1:
                    solidProperty = lod1Solid;
                    break;
                case 2:
                    solidProperty = lod2Solid;
                    break;
                case 3:
                    solidProperty = lod3Solid;
                    break;
                case 4:
                    solidProperty = lod4Solid;
                    break;
            }

            if (solidProperty != null) {
                if (solidProperty.isSetSolid())
                    boundedBy.updateEnvelope(solidProperty.getSolid().calcBoundingBox());
            }
        }

        MultiSurfaceProperty multiSurfaceProperty = null;
        for (int lod = 1; lod < 5; lod++) {
            switch (lod) {
                case 1:
                    multiSurfaceProperty = lod1MultiSurface;
                    break;
                case 2:
                    multiSurfaceProperty = lod2MultiSurface;
                    break;
                case 3:
                    multiSurfaceProperty = lod3MultiSurface;
                    break;
                case 4:
                    multiSurfaceProperty = lod4MultiSurface;
                    break;
            }

            if (multiSurfaceProperty != null) {
                if (multiSurfaceProperty.isSetMultiSurface())
                    boundedBy.updateEnvelope(multiSurfaceProperty.getMultiSurface().calcBoundingBox());
            }
        }

        MultiCurveProperty multiCurveProperty = null;
        for (int lod = 2; lod < 5; lod++) {
            switch (lod) {
                case 2:
                    multiCurveProperty = lod2MultiCurve;
                    break;
                case 3:
                    multiCurveProperty = lod3MultiCurve;
                    break;
                case 4:
                    multiCurveProperty = lod4MultiCurve;
                    break;
            }

            if (multiCurveProperty != null) {
                if (multiCurveProperty.isSetMultiCurve())
                    boundedBy.updateEnvelope(multiCurveProperty.getMultiCurve().calcBoundingBox());
            }
        }

        if (isSetConsistsOf()) {
            for (BuildingUnitPartProperty buildingUnitPartProperty : getConsistsOf()) {
                if (buildingUnitPartProperty.isSetObject())
                    boundedBy.updateEnvelope(buildingUnitPartProperty.getObject().calcBoundedBy(options).getEnvelope());
            }
        }

        if (options.isAssignResultToFeatures())
            setBoundedBy(boundedBy);

        return boundedBy;
    }

    @Override
    public LodRepresentation getLodRepresentation() {
        LodRepresentation lodRepresentation = new LodRepresentation();

        GeometryProperty<? extends AbstractGeometry> property = null;
        for (int lod = 1; lod < 5; lod++) {
            switch (lod) {
                case 1:
                    property = lod1Solid;
                    break;
                case 2:
                    property = lod2Solid;
                    break;
                case 3:
                    property = lod3Solid;
                    break;
                case 4:
                    property = lod4Solid;
                    break;
            }

            if (property != null)
                lodRepresentation.getGeometry(lod).add(property);
        }

        property = null;
        for (int lod = 1; lod < 5; lod++) {
            switch (lod) {
                case 1:
                    property = lod1MultiSurface;
                    break;
                case 2:
                    property = lod2MultiSurface;
                    break;
                case 3:
                    property = lod3MultiSurface;
                    break;
                case 4:
                    property = lod4MultiSurface;
                    break;
            }

            if (property != null)
                lodRepresentation.getGeometry(lod).add(property);
        }

        property = null;
        for (int lod = 2; lod < 5; lod++) {
            switch (lod) {
                case 2:
                    property = lod2MultiCurve;
                    break;
                case 3:
                    property = lod3MultiCurve;
                    break;
                case 4:
                    property = lod3MultiCurve;
                    break;
            }

            if (property != null)
                lodRepresentation.getGeometry(lod).add(property);
        }

        return lodRepresentation;
    }

    @Override
    public Object copyTo(Object target, CopyBuilder copyBuilder) {
        if (target == null)
            throw new IllegalArgumentException("Target argument must not be null for abstract copyable classes.");

        AbstractBuildingUnit copy = (AbstractBuildingUnit) target;
        super.copyTo(copy, copyBuilder);

        if (isSetClazz())
            copy.setClazz((Code) copyBuilder.copy(clazz));

        if (isSetFunction()) {
            for (Code part : function) {
                Code copyPart = (Code) copyBuilder.copy(part);
                copy.addFunction(copyPart);

                if (part != null && copyPart == part)
                    part.setParent(this);
            }
        }

        if (isSetUsage()) {
            for (Code part : usage) {
                Code copyPart = (Code) copyBuilder.copy(part);
                copy.addUsage(copyPart);

                if (part != null && copyPart == part)
                    part.setParent(this);
            }
        }

        if (isSetEnergyPerformanceCertification()) {
            for (EnergyPerformanceCertificationProperty part : energyPerformanceCertification) {
                EnergyPerformanceCertificationProperty copyPart = (EnergyPerformanceCertificationProperty) copyBuilder.copy(part);
                copy.addEnergyPerformanceCertification(copyPart);

                if (part != null && copyPart == part)
                    part.setParent(this);
            }
        }

        if (isSetLod1Solid()) {
            copy.setLod1Solid((SolidProperty) copyBuilder.copy(lod1Solid));
            if (copy.getLod1Solid() == lod1Solid)
                lod1Solid.setParent(this);
        }

        if (isSetLod2Solid()) {
            copy.setLod2Solid((SolidProperty) copyBuilder.copy(lod2Solid));
            if (copy.getLod2Solid() == lod2Solid)
                lod2Solid.setParent(this);
        }

        if (isSetLod3Solid()) {
            copy.setLod3Solid((SolidProperty) copyBuilder.copy(lod3Solid));
            if (copy.getLod3Solid() == lod3Solid)
                lod3Solid.setParent(this);
        }

        if (isSetLod4Solid()) {
            copy.setLod4Solid((SolidProperty) copyBuilder.copy(lod4Solid));
            if (copy.getLod4Solid() == lod4Solid)
                lod4Solid.setParent(this);
        }

        if (isSetLod2MultiCurve()) {
            copy.setLod2MultiCurve((MultiCurveProperty) copyBuilder.copy(lod2MultiCurve));
            if (copy.getLod2MultiCurve() == lod2MultiCurve)
                lod2MultiCurve.setParent(this);
        }

        if (isSetLod3MultiCurve()) {
            copy.setLod3MultiCurve((MultiCurveProperty) copyBuilder.copy(lod3MultiCurve));
            if (copy.getLod3MultiCurve() == lod3MultiCurve)
                lod3MultiCurve.setParent(this);
        }

        if (isSetLod4MultiCurve()) {
            copy.setLod4MultiCurve((MultiCurveProperty) copyBuilder.copy(lod4MultiCurve));
            if (copy.getLod4MultiCurve() == lod4MultiCurve)
                lod4MultiCurve.setParent(this);
        }

        if (isSetLod1MultiSurface()) {
            copy.setLod1MultiSurface((MultiSurfaceProperty) copyBuilder.copy(lod1MultiSurface));
            if (copy.getLod1MultiSurface() == lod1MultiSurface)
                lod1MultiSurface.setParent(this);
        }

        if (isSetLod2MultiSurface()) {
            copy.setLod2MultiSurface((MultiSurfaceProperty) copyBuilder.copy(lod2MultiSurface));
            if (copy.getLod2MultiSurface() == lod2MultiSurface)
                lod2MultiSurface.setParent(this);
        }

        if (isSetLod3MultiSurface()) {
            copy.setLod3MultiSurface((MultiSurfaceProperty) copyBuilder.copy(lod3MultiSurface));
            if (copy.getLod3MultiSurface() == lod3MultiSurface)
                lod3MultiSurface.setParent(this);
        }

        if (isSetLod4MultiSurface()) {
            copy.setLod4MultiSurface((MultiSurfaceProperty) copyBuilder.copy(lod4MultiSurface));
            if (copy.getLod4MultiSurface() == lod4MultiSurface)
                lod4MultiSurface.setParent(this);
        }

        if (isSetAddress()) {
            for (AddressProperty part : address) {
                AddressProperty copyPart = (AddressProperty) copyBuilder.copy(part);
                copy.addAddress(copyPart);

                if (part != null && copyPart == part)
                    part.setParent(this);
            }
        }

        if (isSetEquippedWith()) {
            for (FacilitiesProperty part : equippedWith) {
                FacilitiesProperty copyPart = (FacilitiesProperty) copyBuilder.copy(part);
                copy.addEquippedWith(copyPart);

                if (part != null && copyPart == part)
                    part.setParent(this);
            }
        }

        if (isSetConsistsOf()) {
            for (BuildingUnitPartProperty part : consistsOf) {
                BuildingUnitPartProperty copyPart = (BuildingUnitPartProperty) copyBuilder.copy(part);
                copy.addConsistsOf(copyPart);

                if (part != null && copyPart == part)
                    part.setParent(this);
            }
        }

        return copy;
    }

    @Override
    public void accept(FeatureVisitor visitor) {
        visitor.visit((ADEModelObject) this);
    }

    @Override
    public <T> T accept(FeatureFunctor<T> visitor) {
        return visitor.apply((ADEModelObject) this);
    }

    @Override
    public void accept(GMLVisitor visitor) {
        visitor.visit((ADEModelObject) this);
    }

    @Override
    public <T> T accept(GMLFunctor<T> visitor) {
        return visitor.apply((ADEModelObject) this);
    }

}
