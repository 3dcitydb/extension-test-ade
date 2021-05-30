-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2021-05-29 23:41:00 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create tables ************************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- test_building 
-- -------------------------------------------------------------------- 
CREATE TABLE test_building
(
    id INTEGER NOT NULL,
    energyperform_certificatio_1 VARCHAR2(1000),
    energyperforma_certification VARCHAR2(1000),
    floorarea NUMBER,
    floorarea_uom VARCHAR2(1000),
    ownername VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_energyperformancecer 
-- -------------------------------------------------------------------- 
CREATE TABLE test_energyperformancecer
(
    id INTEGER NOT NULL,
    certificationid VARCHAR2(1000),
    certificationname VARCHAR2(1000),
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
    lod2multicurve MDSYS.SDO_GEOMETRY,
    lod2solid_id INTEGER,
    PRIMARY KEY (id)
);

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create foreign keys ******************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- test_building 
-- -------------------------------------------------------------------- 
ALTER TABLE test_building ADD CONSTRAINT test_building_fk FOREIGN KEY (id)
REFERENCES building (id);

-- -------------------------------------------------------------------- 
-- test_industrialbuilding 
-- -------------------------------------------------------------------- 
ALTER TABLE test_industrialbuilding ADD CONSTRAINT test_industrialbuilding_fk FOREIGN KEY (id)
REFERENCES building (id);

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

ALTER TABLE test_otherconstruction ADD CONSTRAINT test_othercons_lod2soli_fk FOREIGN KEY (lod2solid_id)
REFERENCES surface_geometry (id);

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create Indexes ************************************* 
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
-- test_other_to_thema_surfa 
-- -------------------------------------------------------------------- 
CREATE INDEX test_othe_to_them_surf_fk1 ON test_other_to_thema_surfa (otherconstruction_id);

CREATE INDEX test_othe_to_them_surf_fk2 ON test_other_to_thema_surfa (thematic_surface_id);

-- -------------------------------------------------------------------- 
-- test_otherconstruction 
-- -------------------------------------------------------------------- 
DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='TEST_OTHERCONSTRUCTION' AND COLUMN_NAME='LOD2MULTICURVE';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('TEST_OTHERCONSTRUCTION','LOD2MULTICURVE',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX test_othercon_lod2mult_spx ON test_otherconstruction (lod2multicurve) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

CREATE INDEX test_othercon_lod2soli_fkx ON test_otherconstruction (lod2solid_id);

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create Sequences *********************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

CREATE SEQUENCE test_energyperformanc_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

