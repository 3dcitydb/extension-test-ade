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

package org.citygml.ade.test.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.CityGMLClass;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;
import org.citygml4j.model.citygml.core.AbstractSite;
import org.citygml4j.model.common.child.ChildList;
import org.citygml4j.model.common.visitor.FeatureFunctor;
import org.citygml4j.model.common.visitor.FeatureVisitor;
import org.citygml4j.model.common.visitor.GMLFunctor;
import org.citygml4j.model.common.visitor.GMLVisitor;
import org.citygml4j.model.gml.feature.BoundingShape;
import org.citygml4j.util.bbox.BoundingBoxOptions;

import java.util.List;

public class OtherConstruction extends AbstractSite implements ADEModelObject {
    private List<BoundarySurfaceProperty> boundedBySurface;

    public void addBoundedBySurface(BoundarySurfaceProperty boundedBySurface) {
        if (this.boundedBySurface == null)
            this.boundedBySurface = new ChildList<BoundarySurfaceProperty>(this);

        this.boundedBySurface.add(boundedBySurface);
    }

    public List<BoundarySurfaceProperty> getBoundedBySurface() {
        if (boundedBySurface == null)
            boundedBySurface = new ChildList<BoundarySurfaceProperty>(this);

        return boundedBySurface;
    }

    public boolean isSetBoundedBySurface() {
        return boundedBySurface != null && !boundedBySurface.isEmpty();
    }

    public void setBoundedBySurface(List<BoundarySurfaceProperty> boundedBySurface) {
        this.boundedBySurface = new ChildList<BoundarySurfaceProperty>(this, boundedBySurface);
    }

    public void unsetBoundedBySurface() {
        if (isSetBoundedBySurface())
            boundedBySurface.clear();

        boundedBySurface = null;
    }

    public boolean unsetBoundedBySurface(BoundarySurfaceProperty boundedBySurface) {
        return isSetBoundedBySurface() ? this.boundedBySurface.remove(boundedBySurface) : false;
    }

    @Override
    public BoundingShape calcBoundedBy(BoundingBoxOptions options) {
        BoundingShape boundedBy = super.calcBoundedBy(options);
        if (options.isUseExistingEnvelopes() && !boundedBy.isEmpty())
            return boundedBy;

        if (isSetBoundedBySurface()) {
            for (BoundarySurfaceProperty boundarySurfaceProperty : getBoundedBySurface()) {
                if (boundarySurfaceProperty.isSetBoundarySurface())
                    boundedBy.updateEnvelope(boundarySurfaceProperty.getBoundarySurface().calcBoundedBy(options).getEnvelope());
            }
        }

        if (options.isAssignResultToFeatures())
            setBoundedBy(boundedBy);

        return boundedBy;
    }

    @Override
    public Object copy(CopyBuilder copyBuilder) {
        return copyTo(new OtherConstruction(), copyBuilder);
    }

    @Override
    public Object copyTo(Object target, CopyBuilder copyBuilder) {
        OtherConstruction copy = (target == null) ? new OtherConstruction() : (OtherConstruction) target;
        super.copyTo(copy, copyBuilder);

        if (isSetBoundedBySurface()) {
            for (BoundarySurfaceProperty part : boundedBySurface) {
                BoundarySurfaceProperty copyPart = (BoundarySurfaceProperty) copyBuilder.copy(part);
                copy.addBoundedBySurface(copyPart);

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

    @Override
    public CityGMLClass getCityGMLClass() {
        return CityGMLClass.ADE_COMPONENT;
    }

}
