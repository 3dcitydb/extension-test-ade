-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2018-04-12 14:10:36 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- ***********************************  Create tables ************************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- test_building 
-- -------------------------------------------------------------------- 
CREATE TABLE test_building
(
    id INTEGER NOT NULL,
    ownername VARCHAR2(1000),
    floorarea_uom VARCHAR2(1000),
    floorarea NUMBER,
    energyperforma_certification VARCHAR2(1000),
    energyperform_certificatio_1 VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_buildingu_to_address 
-- -------------------------------------------------------------------- 
CREATE TABLE test_buildingu_to_address
(
    buildingunit_id INTEGER NOT NULL,
    address_id INTEGER NOT NULL,
    PRIMARY KEY (buildingunit_id, address_id)
);

-- -------------------------------------------------------------------- 
-- test_buildingunit 
-- -------------------------------------------------------------------- 
CREATE TABLE test_buildingunit
(
    id INTEGER NOT NULL,
    objectclass_id INTEGER,
    buildingunit_parent_id INTEGER,
    buildingunit_root_id INTEGER,
    building_buildingunit_id INTEGER,
    class_codespace VARCHAR2(1000),
    class VARCHAR2(1000),
    usage_codespace VARCHAR2(1000),
    usage VARCHAR2(1000),
    function_codespace VARCHAR2(1000),
    function VARCHAR2(1000),
    lod2multicurve MDSYS.SDO_GEOMETRY,
    lod3multicurve MDSYS.SDO_GEOMETRY,
    lod4multicurve MDSYS.SDO_GEOMETRY,
    lod1multisurface_id INTEGER,
    lod2multisurface_id INTEGER,
    lod3multisurface_id INTEGER,
    lod4multisurface_id INTEGER,
    lod1solid_id INTEGER,
    lod2solid_id INTEGER,
    lod3solid_id INTEGER,
    lod4solid_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_energyperformancecer 
-- -------------------------------------------------------------------- 
CREATE TABLE test_energyperformancecer
(
    id INTEGER NOT NULL,
    buildingunit_energyperfor_id INTEGER,
    certificationname VARCHAR2(1000),
    certificationid VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_facilities 
-- -------------------------------------------------------------------- 
CREATE TABLE test_facilities
(
    id INTEGER NOT NULL,
    objectclass_id INTEGER,
    buildingunit_equippedwith_id INTEGER,
    totalvalue_uom VARCHAR2(1000),
    totalvalue NUMBER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_industrialbuilding 
-- -------------------------------------------------------------------- 
CREATE TABLE test_industrialbuilding
(
    id INTEGER NOT NULL,
    remark VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_industrialbuildingpa 
-- -------------------------------------------------------------------- 
CREATE TABLE test_industrialbuildingpa
(
    id INTEGER NOT NULL,
    remark VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_industrialbuildingro 
-- -------------------------------------------------------------------- 
CREATE TABLE test_industrialbuildingro
(
    id INTEGER NOT NULL,
    remark VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_other_to_thema_surfa 
-- -------------------------------------------------------------------- 
CREATE TABLE test_other_to_thema_surfa
(
    otherconstruction_id INTEGER NOT NULL,
    thematic_surface_id INTEGER NOT NULL,
    PRIMARY KEY (otherconstruction_id, thematic_surface_id)
);

-- -------------------------------------------------------------------- 
-- test_otherconstruction 
-- -------------------------------------------------------------------- 
CREATE TABLE test_otherconstruction
(
    id INTEGER NOT NULL,
    PRIMARY KEY (id)
);

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************  Create foreign keys  ******************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- test_building 
-- -------------------------------------------------------------------- 
ALTER TABLE test_building ADD CONSTRAINT test_building_fk FOREIGN KEY (id)
REFERENCES building (id);

-- -------------------------------------------------------------------- 
-- test_buildingu_to_address 
-- -------------------------------------------------------------------- 
ALTER TABLE test_buildingu_to_address ADD CONSTRAINT test_buildin_to_addres_fk1 FOREIGN KEY (buildingunit_id)
REFERENCES test_buildingunit (id);

ALTER TABLE test_buildingu_to_address ADD CONSTRAINT test_buildin_to_addres_fk2 FOREIGN KEY (address_id)
REFERENCES address (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- test_buildingunit 
-- -------------------------------------------------------------------- 
ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildingu_objectcl_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildingunit_fk FOREIGN KEY (id)
REFERENCES cityobject (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildinguni_parent_fk FOREIGN KEY (buildingunit_parent_id)
REFERENCES test_buildingunit (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildingunit_root_fk FOREIGN KEY (buildingunit_root_id)
REFERENCES test_buildingunit (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildi_build_build_fk FOREIGN KEY (building_buildingunit_id)
REFERENCES test_building (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildingu_lod1mult_fk FOREIGN KEY (lod1multisurface_id)
REFERENCES surface_geometry (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildingu_lod2mult_fk FOREIGN KEY (lod2multisurface_id)
REFERENCES surface_geometry (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildingu_lod3mult_fk FOREIGN KEY (lod3multisurface_id)
REFERENCES surface_geometry (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildingu_lod4mult_fk FOREIGN KEY (lod4multisurface_id)
REFERENCES surface_geometry (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildingu_lod1soli_fk FOREIGN KEY (lod1solid_id)
REFERENCES surface_geometry (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildingu_lod2soli_fk FOREIGN KEY (lod2solid_id)
REFERENCES surface_geometry (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildingu_lod3soli_fk FOREIGN KEY (lod3solid_id)
REFERENCES surface_geometry (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildingu_lod4soli_fk FOREIGN KEY (lod4solid_id)
REFERENCES surface_geometry (id);

-- -------------------------------------------------------------------- 
-- test_energyperformancecer 
-- -------------------------------------------------------------------- 
ALTER TABLE test_energyperformancecer ADD CONSTRAINT test_energy_build_energ_fk FOREIGN KEY (buildingunit_energyperfor_id)
REFERENCES test_buildingunit (id);

-- -------------------------------------------------------------------- 
-- test_facilities 
-- -------------------------------------------------------------------- 
ALTER TABLE test_facilities ADD CONSTRAINT test_facilitie_objectcl_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE test_facilities ADD CONSTRAINT test_facilities_fk FOREIGN KEY (id)
REFERENCES cityobject (id);

ALTER TABLE test_facilities ADD CONSTRAINT test_facili_build_equip_fk FOREIGN KEY (buildingunit_equippedwith_id)
REFERENCES test_buildingunit (id);

-- -------------------------------------------------------------------- 
-- test_industrialbuilding 
-- -------------------------------------------------------------------- 
ALTER TABLE test_industrialbuilding ADD CONSTRAINT test_industrialbuilding_fk FOREIGN KEY (id)
REFERENCES building (id);

-- -------------------------------------------------------------------- 
-- test_industrialbuildingpa 
-- -------------------------------------------------------------------- 
ALTER TABLE test_industrialbuildingpa ADD CONSTRAINT test_industrialbuildi_fk_1 FOREIGN KEY (id)
REFERENCES building (id);

-- -------------------------------------------------------------------- 
-- test_industrialbuildingro 
-- -------------------------------------------------------------------- 
ALTER TABLE test_industrialbuildingro ADD CONSTRAINT test_industrialbuildi_fk_2 FOREIGN KEY (id)
REFERENCES thematic_surface (id);

-- -------------------------------------------------------------------- 
-- test_other_to_thema_surfa 
-- -------------------------------------------------------------------- 
ALTER TABLE test_other_to_thema_surfa ADD CONSTRAINT test_othe_to_them_surf_fk1 FOREIGN KEY (otherconstruction_id)
REFERENCES test_otherconstruction (id);

ALTER TABLE test_other_to_thema_surfa ADD CONSTRAINT test_othe_to_them_surf_fk2 FOREIGN KEY (thematic_surface_id)
REFERENCES thematic_surface (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- test_otherconstruction 
-- -------------------------------------------------------------------- 
ALTER TABLE test_otherconstruction ADD CONSTRAINT test_otherconstruction_fk FOREIGN KEY (id)
REFERENCES cityobject (id);

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************  Create Indexes  ************************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

SET SERVEROUTPUT ON
SET FEEDBACK ON
SET VER OFF
VARIABLE SRID NUMBER;
BEGIN
  SELECT SRID INTO :SRID FROM DATABASE_SRS;
END;
/

column mc new_value SRSNO print
select :SRID mc from dual;

prompt Used SRID for spatial indexes: &SRSNO; 

-- -------------------------------------------------------------------- 
-- test_buildingunit 
-- -------------------------------------------------------------------- 
CREATE INDEX test_building_objectcl_fkx ON test_buildingunit (objectclass_id);

CREATE INDEX test_buildingun_parent_fkx ON test_buildingunit (buildingunit_parent_id);

CREATE INDEX test_buildingunit_root_fkx ON test_buildingunit (buildingunit_root_id);

CREATE INDEX test_build_build_build_fkx ON test_buildingunit (building_buildingunit_id);

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='TEST_BUILDINGUNIT' AND COLUMN_NAME='LOD2MULTICURVE';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('TEST_BUILDINGUNIT','LOD2MULTICURVE',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX test_building_lod2mult_spx ON test_buildingunit (lod2multicurve) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='TEST_BUILDINGUNIT' AND COLUMN_NAME='LOD3MULTICURVE';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('TEST_BUILDINGUNIT','LOD3MULTICURVE',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX test_building_lod3mult_spx ON test_buildingunit (lod3multicurve) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='TEST_BUILDINGUNIT' AND COLUMN_NAME='LOD4MULTICURVE';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('TEST_BUILDINGUNIT','LOD4MULTICURVE',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX test_building_lod4mult_spx ON test_buildingunit (lod4multicurve) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

CREATE INDEX test_building_lod1mult_fkx ON test_buildingunit (lod1multisurface_id);

CREATE INDEX test_building_lod2mult_fkx ON test_buildingunit (lod2multisurface_id);

CREATE INDEX test_building_lod3mult_fkx ON test_buildingunit (lod3multisurface_id);

CREATE INDEX test_building_lod4mult_fkx ON test_buildingunit (lod4multisurface_id);

CREATE INDEX test_building_lod1soli_fkx ON test_buildingunit (lod1solid_id);

CREATE INDEX test_building_lod2soli_fkx ON test_buildingunit (lod2solid_id);

CREATE INDEX test_building_lod3soli_fkx ON test_buildingunit (lod3solid_id);

CREATE INDEX test_building_lod4soli_fkx ON test_buildingunit (lod4solid_id);

-- -------------------------------------------------------------------- 
-- test_energyperformancecer 
-- -------------------------------------------------------------------- 
CREATE INDEX test_energ_build_energ_fkx ON test_energyperformancecer (buildingunit_energyperfor_id);

-- -------------------------------------------------------------------- 
-- test_facilities 
-- -------------------------------------------------------------------- 
CREATE INDEX test_faciliti_objectcl_fkx ON test_facilities (objectclass_id);

CREATE INDEX test_facil_build_equip_fkx ON test_facilities (buildingunit_equippedwith_id);

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************  Create Sequences  *********************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

CREATE SEQUENCE test_energyperformanc_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

