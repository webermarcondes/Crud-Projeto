-- Postgresql script

-- Table setor
CREATE TABLE IF NOT EXISTS setor (
idsetor SERIAL PRIMARY KEY,
nome VARCHAR(100) NOT NULL
);

-- Table colaborador
CREATE TABLE IF NOT EXISTS colaborador (
idcolaborador SERIAL PRIMARY KEY,
nome VARCHAR(45) NOT NULL,
login VARCHAR(45) NOT NULL,
senha CHAR(8) NOT NULL,
idsetor INT NOT NULL,
CONSTRAINT fk_colaborador_setor1
FOREIGN KEY (idsetor)
REFERENCES setor (idsetor)
ON DELETE RESTRICT
ON UPDATE CASCADE
);

-- Table ideia
CREATE TABLE IF NOT EXISTS ideia (
idideia SERIAL PRIMARY KEY,
titulo VARCHAR(100) NOT NULL,
descricao VARCHAR(255) NOT NULL,
datapublicacao DATE NOT NULL,
feedback VARCHAR(255) NOT NULL,
status VARCHAR(15) NOT NULL,
idcolaborador INT NOT NULL,
idsetor INT NOT NULL,
CONSTRAINT fk_ideia_colaborador1
FOREIGN KEY (idcolaborador)
REFERENCES colaborador (idcolaborador)
ON DELETE RESTRICT
ON UPDATE CASCADE,
CONSTRAINT fk_ideia_setor1
FOREIGN KEY (idsetor)
REFERENCES setor (idsetor)
ON DELETE RESTRICT
ON UPDATE CASCADE
);

-- Table voto
CREATE TABLE IF NOT EXISTS voto (
idvoto SERIAL PRIMARY KEY,
opcao VARCHAR(10) NOT NULL,
idideia INT NOT NULL,
idcolaborador INT NOT NULL,
CONSTRAINT fk_voto_ideia1
FOREIGN KEY (idideia)
REFERENCES ideia (idideia)
ON DELETE RESTRICT
ON UPDATE CASCADE,
CONSTRAINT fk_voto_colaborador1
FOREIGN KEY (idcolaborador)
REFERENCES colaborador (idcolaborador)
ON DELETE RESTRICT
ON UPDATE CASCADE
);		