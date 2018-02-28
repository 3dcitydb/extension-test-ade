//
// Generated with ade-xjc - XML Schema binding compiler for CityGML ADEs, version 2.6.1
// ade-xjc is part of the citygml4j project, see https://github.com/citygml4j
// Any modifications to this file will be lost upon recompilation of the source
// Generated: Tue Feb 27 22:06:11 CET 2018
//


package org.citygml.ade.test._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.opengis.citygml._2.AbstractSiteType;
import net.opengis.citygml.building._2.BoundarySurfacePropertyType;


/**
 * <p>Java-Klasse für OtherConstructionType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="OtherConstructionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/citygml/2.0}AbstractSiteType">
 *       &lt;sequence>
 *         &lt;element name="boundedBy" type="{http://www.opengis.net/citygml/building/2.0}BoundarySurfacePropertyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OtherConstructionType", propOrder = {
    "boundedBySurface"
})
public class OtherConstructionType
    extends AbstractSiteType
{

    @XmlElement(name = "boundedBy")
    protected List<BoundarySurfacePropertyType> boundedBySurface;

    /**
     * Gets the value of the boundedBySurface property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the boundedBySurface property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBoundedBySurface().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BoundarySurfacePropertyType }
     * 
     * 
     */
    public List<BoundarySurfacePropertyType> getBoundedBySurface() {
        if (boundedBySurface == null) {
            boundedBySurface = new ArrayList<BoundarySurfacePropertyType>();
        }
        return this.boundedBySurface;
    }

    public boolean isSetBoundedBySurface() {
        return ((this.boundedBySurface!= null)&&(!this.boundedBySurface.isEmpty()));
    }

    public void unsetBoundedBySurface() {
        this.boundedBySurface = null;
    }

    public void setBoundedBySurface(List<BoundarySurfacePropertyType> value) {
        this.boundedBySurface = value;
    }

}
