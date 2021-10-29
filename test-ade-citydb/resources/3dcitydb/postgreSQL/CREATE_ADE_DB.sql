-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2021-10-04 09:46:58 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create tables ************************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- test_building 
-- -------------------------------------------------------------------- 
CREATE TABLE test_building
(
    id BIGINT NOT NULL,
    energyperform_certificatio_1 VARCHAR(1000),
    energyperforma_certification VARCHAR(1000),
    floorarea NUMERIC,
    floorarea_uom VARCHAR(1000),
    ownername VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_buildingu_to_address 
-- -------------------------------------------------------------------- 
CREATE TABLE test_buildingu_to_address
(
    address_id BIGINT NOT NULL,
    buildingunit_id BIGINT NOT NULL,
    PRIMARY KEY (address_id, buildingunit_id)
);

-- -------------------------------------------------------------------- 
-- test_buildingunit 
-- -------------------------------------------------------------------- 
CREATE TABLE test_buildingunit
(
    id BIGINT NOT NULL,
    building_buildingunit_id BIGINT,
    buildingunit_parent_id BIGINT,
    buildingunit_root_id BIGINT,
    class VARCHAR(1000),
    class_codespace VARCHAR(1000),
    function VARCHAR(1000),
    function_codespace VARCHAR(1000),
    lod1multisurface_id BIGINT,
    lod1solid_id BIGINT,
    lod2multicurve geometry(GEOMETRYZ),
    lod2multisurface_id BIGINT,
    lod2solid_id BIGINT,
    lod3multicurve geometry(GEOMETRYZ),
    lod3multisurface_id BIGINT,
    lod3solid_id BIGINT,
    lod4multicurve geometry(GEOMETRYZ),
    lod4multisurface_id BIGINT,
    lod4solid_id BIGINT,
    objectclass_id INTEGER,
    usage VARCHAR(1000),
    usage_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_energyperformancecer 
-- -------------------------------------------------------------------- 
CREATE TABLE test_energyperformancecer
(
    id BIGINT NOT NULL,
    buildingunit_energyperfor_id BIGINT,
    certificationid VARCHAR(1000),
    certificationname VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_facilities 
-- -------------------------------------------------------------------- 
CREATE TABLE test_facilities
(
    id BIGINT NOT NULL,
    buildingunit_equippedwith_id BIGINT,
    objectclass_id INTEGER,
    totalvalue NUMERIC,
    totalvalue_uom VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_industrialbuilding 
-- -------------------------------------------------------------------- 
CREATE TABLE test_industrialbuilding
(
    id BIGINT NOT NULL,
    remark VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_industrialbuildingpa 
-- -------------------------------------------------------------------- 
CREATE TABLE test_industrialbuildingpa
(
    id BIGINT NOT NULL,
    remark VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_industrialbuildingro 
-- -------------------------------------------------------------------- 
CREATE TABLE test_industrialbuildingro
(
    id BIGINT NOT NULL,
    remark VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_other_to_thema_surfa 
-- -------------------------------------------------------------------- 
CREATE TABLE test_other_to_thema_surfa
(
    otherconstruction_id BIGINT NOT NULL,
    thematic_surface_id BIGINT NOT NULL,
    PRIMARY KEY (otherconstruction_id, thematic_surface_id)
);

-- -------------------------------------------------------------------- 
-- test_otherconstruction 
-- -------------------------------------------------------------------- 
CREATE TABLE test_otherconstruction
(
    id BIGINT NOT NULL,
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

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildi_build_build_fk FOREIGN KEY (building_buildingunit_id)
REFERENCES test_building (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildinguni_parent_fk FOREIGN KEY (buildingunit_parent_id)
REFERENCES test_buildingunit (id);

ALTER TABLE test_buildingunit ADD CONSTRAINT test_buildingunit_root_fk FOREIGN KEY (buildingunit_root_id)
REFERENCES test_buildingunit (id);

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
ALTER TABLE test_facilities ADD CONSTRAINT test_facilities_fk FOREIGN KEY (id)
REFERENCES cityobject (id);

ALTER TABLE test_facilities ADD CONSTRAINT test_facilitie_objectcl_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

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
-- *********************************** Create Indexes ************************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- test_buildingu_to_address 
-- -------------------------------------------------------------------- 
CREATE INDEX test_buildi_to_addres_fk2x ON test_buildingu_to_address
    USING btree
    (
      address_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_buildi_to_addres_fk1x ON test_buildingu_to_address
    USING btree
    (
      buildingunit_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- test_buildingunit 
-- -------------------------------------------------------------------- 
CREATE INDEX test_build_build_build_fkx ON test_buildingunit
    USING btree
    (
      building_buildingunit_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_buildingun_parent_fkx ON test_buildingunit
    USING btree
    (
      buildingunit_parent_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_buildingunit_root_fkx ON test_buildingunit
    USING btree
    (
      buildingunit_root_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_building_lod1mult_fkx ON test_buildingunit
    USING btree
    (
      lod1multisurface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_building_lod1soli_fkx ON test_buildingunit
    USING btree
    (
      lod1solid_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_building_lod2mult_spx ON test_buildingunit
    USING gist
    (
      lod2multicurve
    );

CREATE INDEX test_building_lod2mult_fkx ON test_buildingunit
    USING btree
    (
      lod2multisurface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_building_lod2soli_fkx ON test_buildingunit
    USING btree
    (
      lod2solid_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_building_lod3mult_spx ON test_buildingunit
    USING gist
    (
      lod3multicurve
    );

CREATE INDEX test_building_lod3mult_fkx ON test_buildingunit
    USING btree
    (
      lod3multisurface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_building_lod3soli_fkx ON test_buildingunit
    USING btree
    (
      lod3solid_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_building_lod4mult_spx ON test_buildingunit
    USING gist
    (
      lod4multicurve
    );

CREATE INDEX test_building_lod4mult_fkx ON test_buildingunit
    USING btree
    (
      lod4multisurface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_building_lod4soli_fkx ON test_buildingunit
    USING btree
    (
      lod4solid_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_building_objectcl_fkx ON test_buildingunit
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- test_energyperformancecer 
-- -------------------------------------------------------------------- 
CREATE INDEX test_energ_build_energ_fkx ON test_energyperformancecer
    USING btree
    (
      buildingunit_energyperfor_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- test_facilities 
-- -------------------------------------------------------------------- 
CREATE INDEX test_facil_build_equip_fkx ON test_facilities
    USING btree
    (
      buildingunit_equippedwith_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_faciliti_objectcl_fkx ON test_facilities
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- test_other_to_thema_surfa 
-- -------------------------------------------------------------------- 
CREATE INDEX test_othe_to_them_surf_fk1 ON test_other_to_thema_surfa
    USING btree
    (
      otherconstruction_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX test_othe_to_them_surf_fk2 ON test_other_to_thema_surfa
    USING btree
    (
      thematic_surface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create Sequences *********************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

CREATE SEQUENCE test_energyperformanc_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


