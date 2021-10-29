-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2021-10-04 09:46:58 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Drop foreign keys ********************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- test_building 
-- -------------------------------------------------------------------- 
ALTER TABLE test_building
    DROP CONSTRAINT test_building_fk;

-- -------------------------------------------------------------------- 
-- test_buildingu_to_address 
-- -------------------------------------------------------------------- 
ALTER TABLE test_buildingu_to_address
    DROP CONSTRAINT test_buildin_to_addres_fk1;

ALTER TABLE test_buildingu_to_address
    DROP CONSTRAINT test_buildin_to_addres_fk2;

-- -------------------------------------------------------------------- 
-- test_buildingunit 
-- -------------------------------------------------------------------- 
ALTER TABLE test_buildingunit
    DROP CONSTRAINT test_buildingu_objectcl_fk;

ALTER TABLE test_buildingunit
    DROP CONSTRAINT test_buildingunit_fk;

ALTER TABLE test_buildingunit
    DROP CONSTRAINT test_buildi_build_build_fk;

ALTER TABLE test_buildingunit
    DROP CONSTRAINT test_buildinguni_parent_fk;

ALTER TABLE test_buildingunit
    DROP CONSTRAINT test_buildingunit_root_fk;

ALTER TABLE test_buildingunit
    DROP CONSTRAINT test_buildingu_lod1mult_fk;

ALTER TABLE test_buildingunit
    DROP CONSTRAINT test_buildingu_lod2mult_fk;

ALTER TABLE test_buildingunit
    DROP CONSTRAINT test_buildingu_lod3mult_fk;

ALTER TABLE test_buildingunit
    DROP CONSTRAINT test_buildingu_lod4mult_fk;

ALTER TABLE test_buildingunit
    DROP CONSTRAINT test_buildingu_lod1soli_fk;

ALTER TABLE test_buildingunit
    DROP CONSTRAINT test_buildingu_lod2soli_fk;

ALTER TABLE test_buildingunit
    DROP CONSTRAINT test_buildingu_lod3soli_fk;

ALTER TABLE test_buildingunit
    DROP CONSTRAINT test_buildingu_lod4soli_fk;

-- -------------------------------------------------------------------- 
-- test_energyperformancecer 
-- -------------------------------------------------------------------- 
ALTER TABLE test_energyperformancecer
    DROP CONSTRAINT test_energy_build_energ_fk;

-- -------------------------------------------------------------------- 
-- test_facilities 
-- -------------------------------------------------------------------- 
ALTER TABLE test_facilities
    DROP CONSTRAINT test_facilities_fk;

ALTER TABLE test_facilities
    DROP CONSTRAINT test_facilitie_objectcl_fk;

ALTER TABLE test_facilities
    DROP CONSTRAINT test_facili_build_equip_fk;

-- -------------------------------------------------------------------- 
-- test_industrialbuilding 
-- -------------------------------------------------------------------- 
ALTER TABLE test_industrialbuilding
    DROP CONSTRAINT test_industrialbuilding_fk;

-- -------------------------------------------------------------------- 
-- test_industrialbuildingpa 
-- -------------------------------------------------------------------- 
ALTER TABLE test_industrialbuildingpa
    DROP CONSTRAINT test_industrialbuildi_fk_1;

-- -------------------------------------------------------------------- 
-- test_industrialbuildingro 
-- -------------------------------------------------------------------- 
ALTER TABLE test_industrialbuildingro
    DROP CONSTRAINT test_industrialbuildi_fk_2;

-- -------------------------------------------------------------------- 
-- test_other_to_thema_surfa 
-- -------------------------------------------------------------------- 
ALTER TABLE test_other_to_thema_surfa
    DROP CONSTRAINT test_othe_to_them_surf_fk1;

ALTER TABLE test_other_to_thema_surfa
    DROP CONSTRAINT test_othe_to_them_surf_fk2;

-- -------------------------------------------------------------------- 
-- test_otherconstruction 
-- -------------------------------------------------------------------- 
ALTER TABLE test_otherconstruction
    DROP CONSTRAINT test_otherconstruction_fk;

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Drop tables *************************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- test_building 
-- -------------------------------------------------------------------- 
DROP TABLE test_building;

-- -------------------------------------------------------------------- 
-- test_buildingu_to_address 
-- -------------------------------------------------------------------- 
DROP TABLE test_buildingu_to_address;

-- -------------------------------------------------------------------- 
-- test_buildingunit 
-- -------------------------------------------------------------------- 
DROP TABLE test_buildingunit;

-- -------------------------------------------------------------------- 
-- test_energyperformancecer 
-- -------------------------------------------------------------------- 
DROP TABLE test_energyperformancecer;

-- -------------------------------------------------------------------- 
-- test_facilities 
-- -------------------------------------------------------------------- 
DROP TABLE test_facilities;

-- -------------------------------------------------------------------- 
-- test_industrialbuilding 
-- -------------------------------------------------------------------- 
DROP TABLE test_industrialbuilding;

-- -------------------------------------------------------------------- 
-- test_industrialbuildingpa 
-- -------------------------------------------------------------------- 
DROP TABLE test_industrialbuildingpa;

-- -------------------------------------------------------------------- 
-- test_industrialbuildingro 
-- -------------------------------------------------------------------- 
DROP TABLE test_industrialbuildingro;

-- -------------------------------------------------------------------- 
-- test_other_to_thema_surfa 
-- -------------------------------------------------------------------- 
DROP TABLE test_other_to_thema_surfa;

-- -------------------------------------------------------------------- 
-- test_otherconstruction 
-- -------------------------------------------------------------------- 
DROP TABLE test_otherconstruction;

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Drop Sequences ************************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

DROP SEQUENCE test_energyperformanc_seq;

PURGE RECYCLEBIN;
