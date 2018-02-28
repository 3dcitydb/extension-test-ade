//
// Generated with ade-xjc - XML Schema binding compiler for CityGML ADEs, version 2.6.1
// ade-xjc is part of the citygml4j project, see https://github.com/citygml4j
// Any modifications to this file will be lost upon recompilation of the source
// Generated: Tue Feb 27 22:06:11 CET 2018
//


package org.citygml.ade.test._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für EnergyPerformanceCertificationPropertyType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="EnergyPerformanceCertificationPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.citygml.org/ade/TestADE/1.0}EnergyPerformanceCertification"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnergyPerformanceCertificationPropertyType", propOrder = {
    "energyPerformanceCertification"
})
public class EnergyPerformanceCertificationPropertyType {

    @XmlElement(name = "EnergyPerformanceCertification", required = true)
    protected EnergyPerformanceCertificationType energyPerformanceCertification;

    /**
     * Ruft den Wert der energyPerformanceCertification-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link EnergyPerformanceCertificationType }
     *     
     */
    public EnergyPerformanceCertificationType getEnergyPerformanceCertification() {
        return energyPerformanceCertification;
    }

    /**
     * Legt den Wert der energyPerformanceCertification-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link EnergyPerformanceCertificationType }
     *     
     */
    public void setEnergyPerformanceCertification(EnergyPerformanceCertificationType value) {
        this.energyPerformanceCertification = value;
    }

    public boolean isSetEnergyPerformanceCertification() {
        return (this.energyPerformanceCertification!= null);
    }

}
