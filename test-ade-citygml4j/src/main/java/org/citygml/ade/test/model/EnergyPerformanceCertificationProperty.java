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
import org.citygml4j.model.gml.base.AssociationByRep;

public class EnergyPerformanceCertificationProperty extends AssociationByRep<EnergyPerformanceCertification> implements ADEModelObject {

	public EnergyPerformanceCertificationProperty() {
	}
	
	public EnergyPerformanceCertificationProperty(EnergyPerformanceCertification energyPerformanceCertification) {
		super(energyPerformanceCertification);
	}
	
	public EnergyPerformanceCertification getEnergyPerformanceCertification() {
		return super.getObject();
	}

	public boolean isSetEnergyPerformanceCertification() {
		return super.isSetObject();
	}

	public void setEnergyPerformanceCertification(EnergyPerformanceCertification energyPerformanceCertification) {
		super.setObject(energyPerformanceCertification);
	}

	public void unsetEnergyPerformanceCertification() {
		super.unsetObject();
	}
	
	@Override
	public Class<EnergyPerformanceCertification> getAssociableClass() {
		return EnergyPerformanceCertification.class;
	}

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new EnergyPerformanceCertificationProperty(), copyBuilder);
	}

	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		EnergyPerformanceCertificationProperty copy = (target == null) ? new EnergyPerformanceCertificationProperty() : (EnergyPerformanceCertificationProperty)target;
		return super.copyTo(copy, copyBuilder);
	}

}
