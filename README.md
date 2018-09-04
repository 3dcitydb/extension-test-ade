# extension-test-ade
---
The _TestADE_ is an artificial CityGML ADE (Application Domain Extension) that is intended to be used for testing and demonstrating the ADE support of the 3D City Database. Firstly, the ADE Manager Plugin can be used to transform the [TestADE XML Schema](https://github.com/3dcitydb/extension-test-ade/blob/master/test-ade-citygml4j/src/main/resources/org/citygml/ade/test/schema/CityGML-TestADE.xsd) to database tables and objects to be used with the 3DCityDB core schema. Secondly, this repository shows developers how to implement the ADE-specific APIs of [citygml4j](https://github.com/citygml4j) (see `test-ade-citygml4j`package) and the Importer/Exporter (see `test-ade-citydb` package) for real-word ADEs. 

The TestADE has been deliberately designed to reflect the most relevant UML modelling techniques that may be used for CityGML ADEs such as subtyping or property injection. The implementation of the citygml4j and Importer/Exporter APIs should therefore serve as template for implementing other ADEs.

Most feature and data types of the TestADE have been copied from existing CityGML ADEs such as the EnergyADE and UtilityNetworkADE. Please note that compared to the original ADE models, the classes of the TestADE have been simplified by omitting attributes and associations in order to make this artificial ADE light-weight for software tests.

UML Data Model
-------
<p align="center">
<img src="resources/uml/CityGML-TestADE.png" width="800" />
</p>
