-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2021-05-28 14:56:36 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create tables ************************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- test_building 
-- -------------------------------------------------------------------- 
CREATE TABLE test_building
(
    id INTEGER NOT NULL,
    energyperform_certificatio_1 VARCHAR(1000),
    energyperforma_certification VARCHAR(1000),
    floorarea NUMERIC,
    floorarea_uom VARCHAR(1000),
    ownername VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_energyperformancecer 
-- -------------------------------------------------------------------- 
CREATE TABLE test_energyperformancecer
(
    id INTEGER NOT NULL,
    certificationid VARCHAR(1000),
    certificationname VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- test_industrialbuilding 
-- -------------------------------------------------------------------- 
CREATE TABLE test_industrialbuilding
(
    id INTEGER NOT NULL,
    remark VARCHAR(1000),
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

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create Indexes ************************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
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
MAXVALUE 2147483647
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


