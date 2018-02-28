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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import net.opengis.gml.AbstractFeatureType;
import net.opengis.gml.MeasureType;


/**
 * <p>Java-Klasse für FacilitiesType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="FacilitiesType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractFeatureType">
 *       &lt;sequence>
 *         &lt;element name="totalValue" type="{http://www.opengis.net/gml}MeasureType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacilitiesType", propOrder = {
    "totalValue"
})
@XmlSeeAlso({
    LightingFacilitiesType.class,
    DHWFacilitiesType.class
})
public abstract class FacilitiesType
    extends AbstractFeatureType
{

    @XmlElement(required = true)
    protected MeasureType totalValue;

    /**
     * Ruft den Wert der totalValue-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getTotalValue() {
        return totalValue;
    }

    /**
     * Legt den Wert der totalValue-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setTotalValue(MeasureType value) {
        this.totalValue = value;
    }

    public boolean isSetTotalValue() {
        return (this.totalValue!= null);
    }

}
