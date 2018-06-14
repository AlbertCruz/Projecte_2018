
DROP TABLE IF EXISTS partida;
DROP TABLE IF EXISTS inscrit;
DROP TABLE IF EXISTS grup;
DROP TABLE IF EXISTS torneig;
DROP TABLE IF EXISTS estadistica_modalitat;
DROP TABLE IF EXISTS modalitat;
DROP TABLE IF EXISTS soci;
DROP TABLE IF EXISTS comptadors;

CREATE TABLE comptadors (
	clau VARCHAR(255) NOT NULL,
	comptador BIGINT,
	CONSTRAINT comptadors_pk PRIMARY KEY (clau)
)ENGINE=InnoDB;

CREATE TABLE soci (
	id INT NOT NULL,
	nif VARCHAR(13) NOT NULL,
	nom VARCHAR(50) NOT NULL,
	data_alta TIMESTAMP NOT NULL,
	cognom1 VARCHAR(40),
	cognom2 VARCHAR(40),
	password varchar(32) NOT NULL,
	foto LONGBLOB,
	actiu TINYINT(1) NOT NULL,

	PRIMARY KEY(id),
	CONSTRAINT soci_un_nif UNIQUE(nif)
)ENGINE=InnoDB;

CREATE INDEX soci_index_nif ON soci(nif);


CREATE TABLE modalitat (
	id INT NOT NULL,
	desc_modalitat VARCHAR(255) NOT NULL,
	
	PRIMARY KEY(id)
)ENGINE=InnoDB;


CREATE TABLE estadistica_modalitat (
	soci_id INT NOT NULL,
	modalitat_id INT NOT NULL,
	coeficient_base DOUBLE NOT NULL,
	total_caramboles_temp_actual INT NOT NULL,
	total_entrades_temp_actual INT NOT NULL,

	PRIMARY KEY (soci_id, modalitat_id),
	CONSTRAINT estadistica_modalitat_fk_soci FOREIGN KEY (soci_id) REFERENCES soci(id),
	CONSTRAINT estadistica_modalitat_fk_modalitat FOREIGN KEY (modalitat_id) REFERENCES modalitat(id)
)ENGINE=InnoDB;

CREATE INDEX estadistica_modalitat_index_coeficient_base ON estadistica_modalitat(coeficient_base);


CREATE TABLE torneig (
	id INT NOT NULL,
	modalitat_id INT NOT NULL,
	nom VARCHAR(100) NOT NULL,
	data_inici TIMESTAMP NOT NULL,
	data_final TIMESTAMP NOT NULL,
	preinscripcio_open TINYINT(1) NOT NULL,
	actiu TINYINT(1) NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT torneig_fk_modalitat FOREIGN KEY (modalitat_id) REFERENCES modalitat(id)
)ENGINE=InnoDB;

CREATE INDEX tornejos_index_actiu ON torneig(actiu);
CREATE INDEX tornejos_index_preinscripcio_oberta ON torneig(preinscripcio_open);


CREATE TABLE grup (
	id INT NOT NULL,
	torneig_id INT NOT NULL,
	descripcio VARCHAR(255) NOT NULL,
	caramboles_victoria INT NOT NULL,
	entrades_limit INT NOT NULL,
	actiu TINYINT(1) NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT grup_fk_torneig FOREIGN KEY (torneig_id) REFERENCES torneig(id) ON DELETE CASCADE
)ENGINE=InnoDB;

CREATE INDEX grups_index_torneig ON grup(torneig_id);


CREATE TABLE inscrit (
	id INT NOT NULL,
	data_creacio TIMESTAMP NOT NULL,
	soci_id INT NOT NULL,
	torneig_id INT NOT NULL,
	grup_id INT,

	PRIMARY KEY(id, soci_id, torneig_id),

	CONSTRAINT inscripcio_un_soci UNIQUE (soci_id, torneig_id, grup_id),
	CONSTRAINT inscripcio_fk_soci FOREIGN KEY (soci_id) REFERENCES soci(id),
	CONSTRAINT inscripcio_fk_torneig FOREIGN KEY (torneig_id) REFERENCES torneig(id) ON DELETE CASCADE,
	CONSTRAINT inscripcio_fk_grup FOREIGN KEY (grup_id) REFERENCES grup(id) ON DELETE CASCADE
)ENGINE=InnoDB;

CREATE TABLE partida (
	id INT NOT NULL,
	soci_a INT NOT NULL,
	soci_b INT NOT NULL,
	torneig_id INT NOT NULL,
	grup_id INT NOT NULL,
	carambolesA INT DEFAULT 0,
	carambolesB INT DEFAULT 0,
	entradesA INT DEFAULT 0,
	entradesB INT DEFAULT 0,
	data_partida TIMESTAMP NULL,
	num_entrades INT DEFAULT 0,
	estat_partida INT(1) NOT NULL,
	mode_victoria INT(1),
	guanyador INT(1),
	
	PRIMARY KEY (id),
	CONSTRAINT partida_fk_soci_a FOREIGN KEY (soci_a, torneig_id, grup_id) REFERENCES inscrit(soci_id, torneig_id, grup_id),
	CONSTRAINT partida_fk_soci_b FOREIGN KEY (soci_b, torneig_id, grup_id) REFERENCES inscrit(soci_id, torneig_id, grup_id)
)ENGINE=InnoDB;

CREATE INDEX partides_index_guanyador ON partida(guanyador);
CREATE INDEX partides_index_torneig ON partida(torneig_id);
CREATE INDEX partides_index_estat_partida ON partida(estat_partida);
