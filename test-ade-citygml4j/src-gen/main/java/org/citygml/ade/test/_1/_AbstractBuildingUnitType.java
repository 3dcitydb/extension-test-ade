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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import net.opengis.citygml._2.AbstractCityObjectType;
import net.opengis.citygml._2.AddressPropertyType;
import net.opengis.gml.CodeType;
import net.opengis.gml.MultiCurvePropertyType;
import net.opengis.gml.MultiSurfacePropertyType;
import net.opengis.gml.SolidPropertyType;


/**
 * <p>Java-Klasse für _AbstractBuildingUnitType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="_AbstractBuildingUnitType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/citygml/2.0}AbstractCityObjectType">
 *       &lt;sequence>
 *         &lt;element name="class" type="{http://www.opengis.net/gml}CodeType" minOccurs="0"/>
 *         &lt;element name="usage" type="{http://www.opengis.net/gml}CodeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="function" type="{http://www.opengis.net/gml}CodeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="energyPerformanceCertification" type="{http://www.citygml.org/ade/TestADE/1.0}EnergyPerformanceCertificationPropertyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="lod2MultiCurve" type="{http://www.opengis.net/gml}MultiCurvePropertyType" minOccurs="0"/>
 *         &lt;element name="lod3MultiCurve" type="{http://www.opengis.net/gml}MultiCurvePropertyType" minOccurs="0"/>
 *         &lt;element name="lod4MultiCurve" type="{http://www.opengis.net/gml}MultiCurvePropertyType" minOccurs="0"/>
 *         &lt;element name="lod1MultiSurface" type="{http://www.opengis.net/gml}MultiSurfacePropertyType" minOccurs="0"/>
 *         &lt;element name="lod2MultiSurface" type="{http://www.opengis.net/gml}MultiSurfacePropertyType" minOccurs="0"/>
 *         &lt;element name="lod3MultiSurface" type="{http://www.opengis.net/gml}MultiSurfacePropertyType" minOccurs="0"/>
 *         &lt;element name="lod4MultiSurface" type="{http://www.opengis.net/gml}MultiSurfacePropertyType" minOccurs="0"/>
 *         &lt;element name="lod1Solid" type="{http://www.opengis.net/gml}SolidPropertyType" minOccurs="0"/>
 *         &lt;element name="lod2Solid" type="{http://www.opengis.net/gml}SolidPropertyType" minOccurs="0"/>
 *         &lt;element name="lod3Solid" type="{http://www.opengis.net/gml}SolidPropertyType" minOccurs="0"/>
 *         &lt;element name="lod4Solid" type="{http://www.opengis.net/gml}SolidPropertyType" minOccurs="0"/>
 *         &lt;element name="address" type="{http://www.opengis.net/citygml/2.0}AddressPropertyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="equippedWith" type="{http://www.citygml.org/ade/TestADE/1.0}FacilitiesPropertyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="consistsOf" type="{http://www.citygml.org/ade/TestADE/1.0}BuildingUnitPartPropertyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "_AbstractBuildingUnitType", propOrder = {
    "clazz",
    "usage",
    "function",
    "energyPerformanceCertification",
    "lod2MultiCurve",
    "lod3MultiCurve",
    "lod4MultiCurve",
    "lod1MultiSurface",
    "lod2MultiSurface",
    "lod3MultiSurface",
    "lod4MultiSurface",
    "lod1Solid",
    "lod2Solid",
    "lod3Solid",
    "lod4Solid",
    "address",
    "equippedWith",
    "consistsOf"
})
@XmlSeeAlso({
    BuildingUnitType.class,
    BuildingUnitPartType.class
})
public abstract class _AbstractBuildingUnitType
    extends AbstractCityObjectType
{

    @XmlElement(name = "class")
    protected CodeType clazz;
    protected List<CodeType> usage;
    protected List<CodeType> function;
    protected List<EnergyPerformanceCertificationPropertyType> energyPerformanceCertification;
    protected MultiCurvePropertyType lod2MultiCurve;
    protected MultiCurvePropertyType lod3MultiCurve;
    protected MultiCurvePropertyType lod4MultiCurve;
    protected MultiSurfacePropertyType lod1MultiSurface;
    protected MultiSurfacePropertyType lod2MultiSurface;
    protected MultiSurfacePropertyType lod3MultiSurface;
    protected MultiSurfacePropertyType lod4MultiSurface;
    protected SolidPropertyType lod1Solid;
    protected SolidPropertyType lod2Solid;
    protected SolidPropertyType lod3Solid;
    protected SolidPropertyType lod4Solid;
    protected List<AddressPropertyType> address;
    protected List<FacilitiesPropertyType> equippedWith;
    protected List<BuildingUnitPartPropertyType> consistsOf;

    /**
     * Ruft den Wert der clazz-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CodeType }
     *     
     */
    public CodeType getClazz() {
        return clazz;
    }

    /**
     * Legt den Wert der clazz-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeType }
     *     
     */
    public void setClazz(CodeType value) {
        this.clazz = value;
    }

    public boolean isSetClazz() {
        return (this.clazz!= null);
    }

    /**
     * Gets the value of the usage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the usage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUsage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CodeType }
     * 
     * 
     */
    public List<CodeType> getUsage() {
        if (usage == null) {
            usage = new ArrayList<CodeType>();
        }
        return this.usage;
    }

    public boolean isSetUsage() {
        return ((this.usage!= null)&&(!this.usage.isEmpty()));
    }

    public void unsetUsage() {
        this.usage = null;
    }

    /**
     * Gets the value of the function property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the function property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFunction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CodeType }
     * 
     * 
     */
    public List<CodeType> getFunction() {
        if (function == null) {
            function = new ArrayList<CodeType>();
        }
        return this.function;
    }

    public boolean isSetFunction() {
        return ((this.function!= null)&&(!this.function.isEmpty()));
    }

    public void unsetFunction() {
        this.function = null;
    }

    /**
     * Gets the value of the energyPerformanceCertification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the energyPerformanceCertification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEnergyPerformanceCertification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EnergyPerformanceCertificationPropertyType }
     * 
     * 
     */
    public List<EnergyPerformanceCertificationPropertyType> getEnergyPerformanceCertification() {
        if (energyPerformanceCertification == null) {
            energyPerformanceCertification = new ArrayList<EnergyPerformanceCertificationPropertyType>();
        }
        return this.energyPerformanceCertification;
    }

    public boolean isSetEnergyPerformanceCertification() {
        return ((this.energyPerformanceCertification!= null)&&(!this.energyPerformanceCertification.isEmpty()));
    }

    public void unsetEnergyPerformanceCertification() {
        this.energyPerformanceCertification = null;
    }

    /**
     * Ruft den Wert der lod2MultiCurve-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MultiCurvePropertyType }
     *     
     */
    public MultiCurvePropertyType getLod2MultiCurve() {
        return lod2MultiCurve;
    }

    /**
     * Legt den Wert der lod2MultiCurve-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiCurvePropertyType }
     *     
     */
    public void setLod2MultiCurve(MultiCurvePropertyType value) {
        this.lod2MultiCurve = value;
    }

    public boolean isSetLod2MultiCurve() {
        return (this.lod2MultiCurve!= null);
    }

    /**
     * Ruft den Wert der lod3MultiCurve-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MultiCurvePropertyType }
     *     
     */
    public MultiCurvePropertyType getLod3MultiCurve() {
        return lod3MultiCurve;
    }

    /**
     * Legt den Wert der lod3MultiCurve-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiCurvePropertyType }
     *     
     */
    public void setLod3MultiCurve(MultiCurvePropertyType value) {
        this.lod3MultiCurve = value;
    }

    public boolean isSetLod3MultiCurve() {
        return (this.lod3MultiCurve!= null);
    }

    /**
     * Ruft den Wert der lod4MultiCurve-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MultiCurvePropertyType }
     *     
     */
    public MultiCurvePropertyType getLod4MultiCurve() {
        return lod4MultiCurve;
    }

    /**
     * Legt den Wert der lod4MultiCurve-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiCurvePropertyType }
     *     
     */
    public void setLod4MultiCurve(MultiCurvePropertyType value) {
        this.lod4MultiCurve = value;
    }

    public boolean isSetLod4MultiCurve() {
        return (this.lod4MultiCurve!= null);
    }

    /**
     * Ruft den Wert der lod1MultiSurface-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MultiSurfacePropertyType }
     *     
     */
    public MultiSurfacePropertyType getLod1MultiSurface() {
        return lod1MultiSurface;
    }

    /**
     * Legt den Wert der lod1MultiSurface-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiSurfacePropertyType }
     *     
     */
    public void setLod1MultiSurface(MultiSurfacePropertyType value) {
        this.lod1MultiSurface = value;
    }

    public boolean isSetLod1MultiSurface() {
        return (this.lod1MultiSurface!= null);
    }

    /**
     * Ruft den Wert der lod2MultiSurface-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MultiSurfacePropertyType }
     *     
     */
    public MultiSurfacePropertyType getLod2MultiSurface() {
        return lod2MultiSurface;
    }

    /**
     * Legt den Wert der lod2MultiSurface-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiSurfacePropertyType }
     *     
     */
    public void setLod2MultiSurface(MultiSurfacePropertyType value) {
        this.lod2MultiSurface = value;
    }

    public boolean isSetLod2MultiSurface() {
        return (this.lod2MultiSurface!= null);
    }

    /**
     * Ruft den Wert der lod3MultiSurface-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MultiSurfacePropertyType }
     *     
     */
    public MultiSurfacePropertyType getLod3MultiSurface() {
        return lod3MultiSurface;
    }

    /**
     * Legt den Wert der lod3MultiSurface-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiSurfacePropertyType }
     *     
     */
    public void setLod3MultiSurface(MultiSurfacePropertyType value) {
        this.lod3MultiSurface = value;
    }

    public boolean isSetLod3MultiSurface() {
        return (this.lod3MultiSurface!= null);
    }

    /**
     * Ruft den Wert der lod4MultiSurface-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MultiSurfacePropertyType }
     *     
     */
    public MultiSurfacePropertyType getLod4MultiSurface() {
        return lod4MultiSurface;
    }

    /**
     * Legt den Wert der lod4MultiSurface-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiSurfacePropertyType }
     *     
     */
    public void setLod4MultiSurface(MultiSurfacePropertyType value) {
        this.lod4MultiSurface = value;
    }

    public boolean isSetLod4MultiSurface() {
        return (this.lod4MultiSurface!= null);
    }

    /**
     * Ruft den Wert der lod1Solid-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SolidPropertyType }
     *     
     */
    public SolidPropertyType getLod1Solid() {
        return lod1Solid;
    }

    /**
     * Legt den Wert der lod1Solid-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SolidPropertyType }
     *     
     */
    public void setLod1Solid(SolidPropertyType value) {
        this.lod1Solid = value;
    }

    public boolean isSetLod1Solid() {
        return (this.lod1Solid!= null);
    }

    /**
     * Ruft den Wert der lod2Solid-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SolidPropertyType }
     *     
     */
    public SolidPropertyType getLod2Solid() {
        return lod2Solid;
    }

    /**
     * Legt den Wert der lod2Solid-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SolidPropertyType }
     *     
     */
    public void setLod2Solid(SolidPropertyType value) {
        this.lod2Solid = value;
    }

    public boolean isSetLod2Solid() {
        return (this.lod2Solid!= null);
    }

    /**
     * Ruft den Wert der lod3Solid-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SolidPropertyType }
     *     
     */
    public SolidPropertyType getLod3Solid() {
        return lod3Solid;
    }

    /**
     * Legt den Wert der lod3Solid-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SolidPropertyType }
     *     
     */
    public void setLod3Solid(SolidPropertyType value) {
        this.lod3Solid = value;
    }

    public boolean isSetLod3Solid() {
        return (this.lod3Solid!= null);
    }

    /**
     * Ruft den Wert der lod4Solid-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SolidPropertyType }
     *     
     */
    public SolidPropertyType getLod4Solid() {
        return lod4Solid;
    }

    /**
     * Legt den Wert der lod4Solid-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SolidPropertyType }
     *     
     */
    public void setLod4Solid(SolidPropertyType value) {
        this.lod4Solid = value;
    }

    public boolean isSetLod4Solid() {
        return (this.lod4Solid!= null);
    }

    /**
     * Gets the value of the address property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the address property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddressPropertyType }
     * 
     * 
     */
    public List<AddressPropertyType> getAddress() {
        if (address == null) {
            address = new ArrayList<AddressPropertyType>();
        }
        return this.address;
    }

    public boolean isSetAddress() {
        return ((this.address!= null)&&(!this.address.isEmpty()));
    }

    public void unsetAddress() {
        this.address = null;
    }

    /**
     * Gets the value of the equippedWith property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the equippedWith property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEquippedWith().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FacilitiesPropertyType }
     * 
     * 
     */
    public List<FacilitiesPropertyType> getEquippedWith() {
        if (equippedWith == null) {
            equippedWith = new ArrayList<FacilitiesPropertyType>();
        }
        return this.equippedWith;
    }

    public boolean isSetEquippedWith() {
        return ((this.equippedWith!= null)&&(!this.equippedWith.isEmpty()));
    }

    public void unsetEquippedWith() {
        this.equippedWith = null;
    }

    /**
     * Gets the value of the consistsOf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the consistsOf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConsistsOf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BuildingUnitPartPropertyType }
     * 
     * 
     */
    public List<BuildingUnitPartPropertyType> getConsistsOf() {
        if (consistsOf == null) {
            consistsOf = new ArrayList<BuildingUnitPartPropertyType>();
        }
        return this.consistsOf;
    }

    public boolean isSetConsistsOf() {
        return ((this.consistsOf!= null)&&(!this.consistsOf.isEmpty()));
    }

    public void unsetConsistsOf() {
        this.consistsOf = null;
    }

    public void setUsage(List<CodeType> value) {
        this.usage = value;
    }

    public void setFunction(List<CodeType> value) {
        this.function = value;
    }

    public void setEnergyPerformanceCertification(List<EnergyPerformanceCertificationPropertyType> value) {
        this.energyPerformanceCertification = value;
    }

    public void setAddress(List<AddressPropertyType> value) {
        this.address = value;
    }

    public void setEquippedWith(List<FacilitiesPropertyType> value) {
        this.equippedWith = value;
    }

    public void setConsistsOf(List<BuildingUnitPartPropertyType> value) {
        this.consistsOf = value;
    }

}
