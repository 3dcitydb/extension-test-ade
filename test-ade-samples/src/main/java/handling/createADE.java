package handling;

import helpers.Logger;
import helpers.Util;
import org.citygml4j.ade.testade.TestADE;
import org.citygml4j.ade.testade.model.*;
import org.citygml4j.core.ade.ADERegistry;
import org.citygml4j.core.model.CityGMLVersion;
import org.citygml4j.core.model.building.Building;
import org.citygml4j.core.util.geometry.GeometryFactory;
import org.citygml4j.xml.CityGMLContext;
import org.citygml4j.xml.module.citygml.CoreModule;
import org.citygml4j.xml.reader.ChunkOptions;
import org.citygml4j.xml.reader.CityGMLInputFactory;
import org.citygml4j.xml.reader.CityGMLReadException;
import org.citygml4j.xml.reader.CityGMLReader;
import org.citygml4j.xml.writer.CityGMLChunkWriter;
import org.citygml4j.xml.writer.CityGMLOutputFactory;
import org.xmlobjects.gml.model.basictypes.Measure;
import org.xmlobjects.gml.model.geometry.GeometryProperty;
import org.xmlobjects.gml.model.geometry.primitives.Polygon;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class createADE {

    public static void main(String[] args) throws Exception {
        Logger log = Logger.start(createADE.class);

        ADERegistry.getInstance().loadADE(new TestADE());

        CityGMLContext context = CityGMLContext.newInstance();

        CityGMLInputFactory in = context.createCityGMLInputFactory()
                .withChunking(ChunkOptions.defaults());

        Path file = Util.SAMPLE_DATA_DIR.resolve("raw.gml");
        log.print("Reading the first building from the file " + file + " by using a filtered reader");

        Building building;
        try (CityGMLReader reader = in.createFilteredCityGMLReader(in.createCityGMLReader(file),
                name -> name.getLocalPart().equals("Building"))) {
            if (reader.hasNext()) {
                building = (Building) reader.next();
                log.print("Found " + reader.getName().getLocalPart() + " with gml:id " + building.getId());
            } else
                throw new CityGMLReadException("Failed to read a building from file " + file);
        }

        IndustrialBuilding industrialBuilding = new IndustrialBuilding();
        industrialBuilding.setDescription(building.getDescription());
        industrialBuilding.setNames(building.getNames());
        industrialBuilding.setClassifier(building.getClassifier());
        industrialBuilding.setRoofType(building.getRoofType());
        industrialBuilding.setStoreyHeightsAboveGround(building.getStoreyHeightsAboveGround());
        industrialBuilding.setStoreyHeightsBelowGround(building.getStoreyHeightsBelowGround());
        industrialBuilding.setStoreysAboveGround(building.getStoreysAboveGround());
        industrialBuilding.setStoreysBelowGround(building.getStoreysBelowGround());

        industrialBuilding.setRemark("remark");

        log.print("Enriching the building with TestADE properties and features");
        AbstractBuildingProperties buildingProperties = new AbstractBuildingProperties();
        industrialBuilding.addADEProperty(buildingProperties);

        log.print("Adding an owner name");
        buildingProperties.setOwnerName("Smith");

        log.print("Adding an energy performance certification");
        EnergyPerformanceCertification certification = new EnergyPerformanceCertification();
        certification.setCertificationId("certId");
        certification.getCertificationNames().add("certName");
        EnergyPerformanceCertificationProperty certificationProperty = new EnergyPerformanceCertificationProperty(certification);
        buildingProperties.setEnergyPerformanceCertification(certificationProperty);

        log.print("Adding a building Underground with geometry and lighting facility");
        BuildingUnderground buildingUnderground = new BuildingUnderground();

        GeometryFactory factory = GeometryFactory.newInstance();
        Polygon polygon = factory.createPolygon(new double[]{6.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 8.0, 0.0, 6.0, 8.0, 0.0, 6.0, 0.0, 0.0}, 3);
        buildingUnderground.setLod0GenericGeometry(new GeometryProperty<>(polygon));

        LightingFacility lightingFacility = new LightingFacility();
        lightingFacility.setElectricalPower(new Measure(4000.0, "W"));
        buildingUnderground.getEquippedWith().add(new FacilityProperty(lightingFacility));

        BuildingUndergroundProperty unitProperty = new BuildingUndergroundProperty(buildingUnderground);
        buildingProperties.getBuildingUnderground().add(unitProperty);

        CityGMLVersion version = CityGMLVersion.v3_0;
        CityGMLOutputFactory out = context.createCityGMLOutputFactory(version);

        Path output = Util.SAMPLE_DATA_DIR.resolve("industrialBuildings_ade_v3.gml");
        log.print("Writing the ADE-enriched building as CityGML " + version + " file " + output);

        try (CityGMLChunkWriter writer = out.createCityGMLChunkWriter(output, StandardCharsets.UTF_8.name())) {
            writer.withIndent("  ")
                    .withDefaultPrefixes()
                    .withDefaultNamespace(CoreModule.of(version).getNamespaceURI())
                    .writeMember(industrialBuilding);
        }

        log.finish();
    }
}
