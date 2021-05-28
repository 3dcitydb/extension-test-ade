-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2021-05-28 14:56:36 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Disable Versioning ********************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

exec DBMS_WM.DisableVersioning('test_building,test_energyperformancecer,test_industrialbuilding,test_other_to_thema_surfa,test_otherconstruction,',true, true);
