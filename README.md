# extension-test-ade
---
The _TestADE_ is an artificial CityGML ADE (Application Domain Extension) which is intended to be used for testing the ADE support of the 3D City Database and to demonstrate developers how to implement the ADE-specific APIs of the citygml4j library and of the Importer/Exporter for real-word ADEs.

The TestADE has been deliberately designed to reflect the most relevant UML modelling techniques that may be used for CityGML ADEs such as subtyping or property injection. Its implementation of the citygml4j and Importer/Exporter APIs can therefore serve as template for implementing other ADEs. 

Most feature and data types of the TestADE have been copied from existing CityGML ADEs such as the EnergyADE and UtilityNetworkADE. Please note that compared to the original ADE models, the classes of the TestADE have been simplified by omitting attributes and associations in order to make this artificial ADE light-weight for software tests.

UML Data Model
-------
<p align="center">
<img src="resources/uml/CityGML-TestADE.png" width="800" />
</p>
