-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema sgdibd
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sgdibd
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sgdibd` DEFAULT CHARACTER SET utf8 ;
USE `sgdibd` ;

-- -----------------------------------------------------
-- Table `sgdibd`.`setor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sgdibd`.`setor` (
  `idsetor` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idsetor`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sgdibd`.`colaborador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sgdibd`.`colaborador` (
  `idcolaborador` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `senha` CHAR(8) NOT NULL,
  `idsetor` INT NOT NULL,
  PRIMARY KEY (`idcolaborador`),
  INDEX `fk_colaborador_setor1_idx` (`idsetor` ASC) ,
  CONSTRAINT `fk_colaborador_setor1`
    FOREIGN KEY (`idsetor`)
    REFERENCES `sgdibd`.`setor` (`idsetor`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sgdibd`.`ideia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sgdibd`.`ideia` (
  `idideia` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(100) NOT NULL,
  `descricao` VARCHAR(255) NOT NULL,
  `datapublicacao` CHAR(10) NOT NULL,
  `feedback` VARCHAR(255) NOT NULL,
  `status` VARCHAR(15) NOT NULL,
  `idcolaborador` INT NOT NULL,
  `idsetor` INT NOT NULL,
  PRIMARY KEY (`idideia`),
  INDEX `fk_ideia_colaborador1_idx` (`idcolaborador` ASC) ,
  INDEX `fk_ideia_setor1_idx` (`idsetor` ASC) ,
  CONSTRAINT `fk_ideia_colaborador1`
    FOREIGN KEY (`idcolaborador`)
    REFERENCES `sgdibd`.`colaborador` (`idcolaborador`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ideia_setor1`
    FOREIGN KEY (`idsetor`)
    REFERENCES `sgdibd`.`setor` (`idsetor`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sgdibd`.`voto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sgdibd`.`voto` (
  `idvoto` INT NOT NULL AUTO_INCREMENT,
  `opcao` VARCHAR(10) NOT NULL,
  `idideia` INT NOT NULL,
  `idcolaborador` INT NOT NULL,
  PRIMARY KEY (`idvoto`),
  INDEX `fk_voto_ideia1_idx` (`idideia` ASC) ,
  INDEX `fk_voto_colaborador1_idx` (`idcolaborador` ASC) ,
  CONSTRAINT `fk_voto_ideia1`
    FOREIGN KEY (`idideia`)
    REFERENCES `sgdibd`.`ideia` (`idideia`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_voto_colaborador1`
    FOREIGN KEY (`idcolaborador`)
    REFERENCES `sgdibd`.`colaborador` (`idcolaborador`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
