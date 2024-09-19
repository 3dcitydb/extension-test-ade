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
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.common.association.Associable;
import org.citygml4j.model.common.base.ModelObject;

import java.util.ArrayList;
import java.util.List;

public class EnergyPerformanceCertification implements Associable, ADEModelObject {
    private List<String> certificationName;
    private String certificationId;
    private ModelObject parent;

    public void addCertificationName(String certificationName) {
        if (this.certificationName == null)
            this.certificationName = new ArrayList<>();

        this.certificationName.add(certificationName);
    }

    public List<String> getCertificationName() {
        if (certificationName == null)
            certificationName = new ArrayList<>();

        return certificationName;
    }

    public boolean isSetCertificationName() {
        return certificationName != null && !certificationName.isEmpty();
    }

    public void setCertificationName(List<String> certificationName) {
        this.certificationName = certificationName;
    }

    public String getCertificationId() {
        return certificationId;
    }

    public boolean isSetCertificationId() {
        return certificationId != null;
    }

    public void setCertificationId(String certificationId) {
        this.certificationId = certificationId;
    }

    @Override
    public ModelObject getParent() {
        return parent;
    }

    @Override
    public void setParent(ModelObject parent) {
        this.parent = parent;
    }

    @Override
    public boolean isSetParent() {
        return parent != null;
    }

    @Override
    public void unsetParent() {
        parent = null;
    }

    @Override
    public Object copy(CopyBuilder copyBuilder) {
        return copyTo(new EnergyPerformanceCertification(), copyBuilder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object copyTo(Object target, CopyBuilder copyBuilder) {
        EnergyPerformanceCertification copy = (target == null) ? new EnergyPerformanceCertification() : (EnergyPerformanceCertification) target;

        if (isSetCertificationName())
            copy.setCertificationName((List<String>) copyBuilder.copy(this.certificationName));

        if (isSetCertificationId())
            copy.setCertificationId(copyBuilder.copy(certificationId));

        copy.unsetParent();
        return copy;
    }

}
