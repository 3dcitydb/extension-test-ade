//
// Generated with ade-xjc - XML Schema binding compiler for CityGML ADEs, version 2.10.0
// ade-xjc is part of the citygml4j project, see https://github.com/citygml4j
// Any modifications to this file will be lost upon recompilation of the source
// Generated: Sat May 29 23:25:10 CEST 2021
//


package org.citygml.ade.test._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import net.opengis.gml.AreaType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.citygml.ade.testade._1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OwnerName_QNAME = new QName("http://www.citygml.org/ade/TestADE/1.0", "ownerName");
    private final static QName _FloorArea_QNAME = new QName("http://www.citygml.org/ade/TestADE/1.0", "floorArea");
    private final static QName _EnergyPerformanceCertificationProperty_QNAME = new QName("http://www.citygml.org/ade/TestADE/1.0", "energyPerformanceCertification");
    private final static QName _EnergyPerformanceCertification_QNAME = new QName("http://www.citygml.org/ade/TestADE/1.0", "EnergyPerformanceCertification");
    private final static QName _IndustrialBuilding_QNAME = new QName("http://www.citygml.org/ade/TestADE/1.0", "IndustrialBuilding");
    private final static QName _OtherConstruction_QNAME = new QName("http://www.citygml.org/ade/TestADE/1.0", "OtherConstruction");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.citygml.ade.testade._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EnergyPerformanceCertificationPropertyType }
     * 
     */
    public EnergyPerformanceCertificationPropertyType createEnergyPerformanceCertificationPropertyType() {
        return new EnergyPerformanceCertificationPropertyType();
    }

    /**
     * Create an instance of {@link EnergyPerformanceCertificationType }
     * 
     */
    public EnergyPerformanceCertificationType createEnergyPerformanceCertificationType() {
        return new EnergyPerformanceCertificationType();
    }

    /**
     * Create an instance of {@link IndustrialBuildingType }
     * 
     */
    public IndustrialBuildingType createIndustrialBuildingType() {
        return new IndustrialBuildingType();
    }

    /**
     * Create an instance of {@link OtherConstructionType }
     * 
     */
    public OtherConstructionType createOtherConstructionType() {
        return new OtherConstructionType();
    }

    /**
     * Create an instance of {@link IndustrialBuildingPropertyType }
     * 
     */
    public IndustrialBuildingPropertyType createIndustrialBuildingPropertyType() {
        return new IndustrialBuildingPropertyType();
    }

    /**
     * Create an instance of {@link OtherConstructionPropertyType }
     * 
     */
    public OtherConstructionPropertyType createOtherConstructionPropertyType() {
        return new OtherConstructionPropertyType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.citygml.org/ade/TestADE/1.0", name = "ownerName", substitutionHeadNamespace = "http://www.opengis.net/citygml/building/2.0", substitutionHeadName = "_GenericApplicationPropertyOfAbstractBuilding")
    public JAXBElement<String> createOwnerName(String value) {
        return new JAXBElement<String>(_OwnerName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AreaType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AreaType }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.citygml.org/ade/TestADE/1.0", name = "floorArea", substitutionHeadNamespace = "http://www.opengis.net/citygml/building/2.0", substitutionHeadName = "_GenericApplicationPropertyOfAbstractBuilding")
    public JAXBElement<AreaType> createFloorArea(AreaType value) {
        return new JAXBElement<AreaType>(_FloorArea_QNAME, AreaType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnergyPerformanceCertificationPropertyType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EnergyPerformanceCertificationPropertyType }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.citygml.org/ade/TestADE/1.0", name = "energyPerformanceCertification", substitutionHeadNamespace = "http://www.opengis.net/citygml/building/2.0", substitutionHeadName = "_GenericApplicationPropertyOfAbstractBuilding")
    public JAXBElement<EnergyPerformanceCertificationPropertyType> createEnergyPerformanceCertificationProperty(EnergyPerformanceCertificationPropertyType value) {
        return new JAXBElement<EnergyPerformanceCertificationPropertyType>(_EnergyPerformanceCertificationProperty_QNAME, EnergyPerformanceCertificationPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnergyPerformanceCertificationType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EnergyPerformanceCertificationType }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.citygml.org/ade/TestADE/1.0", name = "EnergyPerformanceCertification", substitutionHeadNamespace = "http://www.opengis.net/gml", substitutionHeadName = "_Object")
    public JAXBElement<EnergyPerformanceCertificationType> createEnergyPerformanceCertification(EnergyPerformanceCertificationType value) {
        return new JAXBElement<EnergyPerformanceCertificationType>(_EnergyPerformanceCertification_QNAME, EnergyPerformanceCertificationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IndustrialBuildingType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IndustrialBuildingType }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.citygml.org/ade/TestADE/1.0", name = "IndustrialBuilding", substitutionHeadNamespace = "http://www.opengis.net/citygml/building/2.0", substitutionHeadName = "_AbstractBuilding")
    public JAXBElement<IndustrialBuildingType> createIndustrialBuilding(IndustrialBuildingType value) {
        return new JAXBElement<IndustrialBuildingType>(_IndustrialBuilding_QNAME, IndustrialBuildingType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OtherConstructionType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OtherConstructionType }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.citygml.org/ade/TestADE/1.0", name = "OtherConstruction", substitutionHeadNamespace = "http://www.opengis.net/citygml/2.0", substitutionHeadName = "_Site")
    public JAXBElement<OtherConstructionType> createOtherConstruction(OtherConstructionType value) {
        return new JAXBElement<OtherConstructionType>(_OtherConstruction_QNAME, OtherConstructionType.class, null, value);
    }

}
