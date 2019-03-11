-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Player` (
  `Id_p` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(200) NULL,
  `Pass` VARCHAR(256) NULL,
  `Salt` VARCHAR(256) NULL,
  PRIMARY KEY (`Id_p`),
  UNIQUE INDEX `Id_p_UNIQUE` (`Id_p` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `mydb`.`Game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Game` (
  `Id_g` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(100) NULL,
  `Started` DATETIME NOT NULL DEFAULT NOW(),
  `Finished` DATETIME NULL,
  PRIMARY KEY (`Id_g`),
  UNIQUE INDEX `Id_g_UNIQUE` (`Id_g` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `mydb`.`Results`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Results` (
  `Id_r` INT NOT NULL AUTO_INCREMENT,
  `Game` INT NOT NULL,
  `Player` INT NOT NULL,
  `Score` DOUBLE NOT NULL DEFAULT 0,
  `Role` VARCHAR(45) NOT NULL,
  `Finished` TINYINT NOT NULL,
  PRIMARY KEY (`Id_r`),
  UNIQUE INDEX `Id_r_UNIQUE` (`Id_r` ASC),
  INDEX `Player_idx` (`Player` ASC),
  INDEX `Game_idx` (`Game` ASC),
  CONSTRAINT `Player`
    FOREIGN KEY (`Player`)
    REFERENCES `mydb`.`Player` (`Id_p`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Game`
    FOREIGN KEY (`Game`)
    REFERENCES `mydb`.`Game` (`Id_g`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `mydb`.`Move`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Move` (
  `Id_m` INT NOT NULL AUTO_INCREMENT,
  `Player` INT NOT NULL,
  `Game` INT NOT NULL,
  `FromX` INT NOT NULL,
  `ToX` INT NOT NULL,
  `FromY` INT NOT NULL,
  `ToY` INT NOT NULL,
  `FigureId` INT NOT NULL,
  `Time` DATETIME NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`Id_m`),
  UNIQUE INDEX `Id_m_UNIQUE` (`Id_m` ASC),
  INDEX `Player_idx` (`Player` ASC),
  INDEX `Game_idx` (`Game` ASC),
  CONSTRAINT `Player`
    FOREIGN KEY (`Player`)
    REFERENCES `mydb`.`Player` (`Id_p`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Game`
    FOREIGN KEY (`Game`)
    REFERENCES `mydb`.`Game` (`Id_g`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
