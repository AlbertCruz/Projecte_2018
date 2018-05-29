CREATE TABLE Socis{
	id as Number NOT NULL,
	NIF as NVARCHAR(9),
	nom as NVARCHAR(50),
	dataAlta as Date,
	cognom1 as NVARCHAR(50),
	cognom2 as NVARCHAR(50),
	passwordHash as NVARCHAR(50),
	fotoPath as NVARCHAR(50),
	PRIMARY KEY (id)
}

CREATE TABLE EstadisticaModalitat{
	coeficientBase as Number,
	totalCarambolesTemporadaActual as Number,
	totalEntradesTemporadaActual as Number
}

CREATE TABLE Taula{
	id as Number NOT NULL,
	PRIMARY KEY (id)
}

CREATE TABLE Reserva{
	data as Date,
	numHores as Number
}	

CREATE TABLE Torneig{
	id as Number NOT NULL,
	nom as NVARCHAR(50),
	dataInici as Date,
	PRIMARY KEY (id)
}

CREATE TABLE Grup{
	id as Number NOT NULL,
	desc as NVARCHAR(100),
	carambolesVictoria as Number,
	limitEntrades as Number,
	PRIMARY KEY (id)
}

CREATE TABLE Partida{
	id as Number NOT NULL,
	carambolesA as Number,
	carambolesB as Number,
	data as Date,
	numEntrades as Number,
	motiuVictoria ENUM('PER_CARAMBOLES','ENTRADES_ASSOLIDES','ABANDONAMENT'),
	guanyador ENUM('A','B'),
	estatPartida('PENDENT','JUGADA'),
	PRIMARY KEY (id)
}

CREATE TABLE Modalitat{
	id as Number NOT NULL,
	desc as NVARCHAR(100),
	PRIMARY KEY (id)
}
