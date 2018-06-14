

-- INSERTS SOCIS
INSERT INTO soci VALUES(1,'00000000A','Albert','2017-09-10','Cruz','Perez','Ktb5/9b91Gs=',null,1);
INSERT INTO soci VALUES(2,'11111111B','Antonia','2017-11-21','Perez','Flo','Ktb5/9b91Gs=',null,1);
INSERT INTO soci VALUES(3,'22222222C','Alfons','2018-03-03','JoRBA','Rodríguez','Ktb5/9b91Gs=',null,1);
INSERT INTO soci VALUES(4,'33333333D','Irene','2018-01-01','González','Ariza','Ktb5/9b91Gs=',null,1);
INSERT INTO soci VALUES(5,'44444444E','Carlos','2018-02-02','Cruz','Perez','Ktb5/9b91Gs=',null,1);
INSERT INTO soci VALUES(6,'55555555F','Joan','2015-08-12','Cruz','Perez','Ktb5/9b91Gs=',null,1);
INSERT INTO soci VALUES(7,'66666666G','Ricard','2016-09-30','Cantero','Perez','Ktb5/9b91Gs=',null,1);
INSERT INTO soci VALUES(8,'77777777H','JP','2017-04-17','Godo',null,'Ktb5/9b91Gs=',null,1);
INSERT INTO socis VALUES(9,'88888888H','Luis','2016-02-23',null,null,'Ktb5/9b91Gs=',null,1);
INSERT INTO socis VALUES(10,'99999999H','Angela','2016-05-12','Ariza','Aguilera','Ktb5/9b91Gs=',null,1);



-- INSERTS MODALITATS
INSERT INTO modalitat VALUES(1,'Mod 1');
INSERT INTO modalitat VALUES(2,'Mod 2');
INSERT INTO modalitat VALUES(3,'Mod 3');
INSERT INTO modalitat VALUES(4,'Mod 4');
INSERT INTO modalitat VALUES(5,'Mod 5');


-- INSERTS TORNEJOS
INSERT INTO torneig VALUES(1,5,'Torneig 1','2016-03-13','2016-04-13',0,1);
INSERT INTO torneig VALUES(2,3,'Torneig 2','2017-02-18','2017-03-13',0,1);
INSERT INTO torneig VALUES(3,1,'Torneig 3','2017-03-27','2017-05-13',0,1);
INSERT INTO torneig VALUES(4,4,'Torneig 4','2018-05-30','2018-07-13',1,1);
INSERT INTO torneig VALUES(5,2,'Torneig 5','2018-06-01','2018-06-13',0,1);
INSERT INTO torneig VALUES(6,3,'Torneig 6','2018-08-14','2018-09-13',1,0);
INSERT INTO torneig VALUES(7,3,'Torneig 7','2018-08-14','2018-09-13',1,0);


-- INSERTS GRUPS
INSERT INTO grup VALUES(1,1,'Grup torneig 1-1',12,20,1);
INSERT INTO grup VALUES(2,1,'Grup torneig 1-2',14,19,1);
INSERT INTO grup VALUES(3,2,'Grup torneig 2-1',16,18,1);
INSERT INTO grup VALUES(4,2,'Grup torneig 2-2',17,24,1);
INSERT INTO grup VALUES(5,3,'Grup torneig 3-1',18,30,1);
INSERT INTO grup VALUES(6,3,'Grup torneig 3-2',25,30,1);
INSERT INTO grup VALUES(7,3,'Grup torneig 3-3',28,30,1);
INSERT INTO grup VALUES(8,5,'Grup torneig 5-1',21,30,1);
INSERT INTO grup VALUES(9,5,'Grup torneig 5-2',19,25,1);
INSERT INTO grup VALUES(10,5,'Grup torneig 5-3',30,40,1);


-- INSERTS INSCRIPCIONS
INSERT INTO inscrit VALUES(1,'2016-02-23',1,1,1);
INSERT INTO inscrit VALUES(2,'2016-01-12',2,1,1);
INSERT INTO inscrit VALUES(3,'2016-02-15',3,1,2);
INSERT INTO inscrit VALUES(4,'2016-02-15',4,1,2);
INSERT INTO inscrit VALUES(5,'2017-03-17',5,2,3);
INSERT INTO inscrit VALUES(6,'2017-02-18',6,2,4);
INSERT INTO inscrit VALUES(7,'2017-03-17',7,2,3);
INSERT INTO inscrit VALUES(8,'2017-02-18',8,2,4);
INSERT INTO inscrit VALUES(9,'2017-02-25',9,3,5);
INSERT INTO inscrit VALUES(10,'2017-02-25',10,3,6);
INSERT INTO inscrit VALUES(11,'2017-02-25',1,3,5);
INSERT INTO inscrit VALUES(12,'2017-02-25',2,3,7);
INSERT INTO inscrit VALUES(13,'2017-02-25',3,3,5);
INSERT INTO inscrit VALUES(14,'2016-02-23',1,5,null);
INSERT INTO inscrit VALUES(15,'2016-02-23',2,5,null);
INSERT INTO inscrit VALUES(16,'2016-02-23',3,5,null);
INSERT INTO inscrit VALUES(17,'2016-02-23',4,5,9);
INSERT INTO inscrit VALUES(18,'2016-02-23',5,5,10);
INSERT INTO inscrit VALUES(19,'2016-02-23',6,5,null);
INSERT INTO inscrit VALUES(20,'2016-02-23',7,5,null);
INSERT INTO inscrit VALUES(21,'2016-02-23',8,5,null);
INSERT INTO inscrit VALUES(22,'2016-02-23',9,5,null);
INSERT INTO inscrit VALUES(23,'2016-02-23',10,5,10);


-- INSERTS PARTIDES
INSERT INTO partida VALUES(1,1,2,1,1,5,4,5,4,'2016-04-12',9,1,2,0);
INSERT INTO partida VALUES(2,3,4,1,2,5,4,5,4,'2016-04-12',9,1,2,0);
INSERT INTO partida VALUES(3,5,7,2,3,5,4,5,4,'2017-02-17',9,1,2,0);
INSERT INTO partida VALUES(4,6,8,2,4,5,4,5,4,'2017-02-17',9,1,2,0);
INSERT INTO partida VALUES(5,9,1,3,5,5,4,5,4,'2017-05-12',9,1,2,0);
INSERT INTO partida VALUES(6,1,3,3,5,15,15,15,15,'2017-05-12',30,1,1,1);


-- INSERTS ESTADISTICA_MODALITAT
INSERT INTO estadistica_modalitat VALUES(1,1,0.5,2,4);
INSERT INTO estadistica_modalitat VALUES(2,1,0.4,5,6);
INSERT INTO estadistica_modalitat VALUES(3,1,0.3,6,5);
INSERT INTO estadistica_modalitat VALUES(4,1,0.5,2,2);
INSERT INTO estadistica_modalitat VALUES(9,1,0.3,14,9);
INSERT INTO estadistica_modalitat VALUES(5,3,0.2,3,8);
INSERT INTO estadistica_modalitat VALUES(6,3,0.1,5,7);
INSERT INTO estadistica_modalitat VALUES(7,3,0.76,8,6);
INSERT INTO estadistica_modalitat VALUES(8,3,0.8,8,6);


-- INSERTS COMPTADORS
INSERT INTO comptadors VALUES('soci',8);
INSERT INTO comptadors VALUES('modalitat',6);
INSERT INTO comptadors VALUES('torneig',8);
INSERT INTO comptadors VALUES('grup',11);
INSERT INTO comptadors VALUES('inscrit',24);
INSERT INTO comptadors VALUES('partida',7);