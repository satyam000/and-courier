CREATE SCHEMA IF NOT EXISTS `schoolProject` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `schoolProject` ;

-- -----------------------------------------------------
-- Table `schoolProject`.`Couriers`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `schoolProject`.`Couriers` (
  `courier_id` INT NOT NULL AUTO_INCREMENT ,
  `login` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`courier_id`) ,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) );


-- -----------------------------------------------------
-- Table `schoolProject`.`Customers`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `schoolProject`.`Customers` (
  `customer_id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `surname` VARCHAR(45) NOT NULL ,
  `city` VARCHAR(45) NOT NULL ,
  `street` VARCHAR(45) NOT NULL ,
  `postal_code` VARCHAR(6) NOT NULL ,
  `building_num` INT NOT NULL ,
  `apartment_num` VARCHAR(45) NULL ,
  PRIMARY KEY (`customer_id`));


-- -----------------------------------------------------
-- Table `schoolProject`.`ParcelType`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `schoolProject`.`ParcelType` (
  `parceltype_id` INT NOT NULL AUTO_INCREMENT ,
  `type` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`parceltype_id`) );


-- -----------------------------------------------------
-- Table `schoolProject`.`Parcels`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `schoolProject`.`Parcels` (
  `package_id` INT NOT NULL AUTO_INCREMENT ,
  `weight` FLOAT(5,2) NOT NULL ,
  `sent_on` DATE NOT NULL ,
  `recipient_customer_id` INT NOT NULL ,
  `sender_customer_id` INT NOT NULL ,
  `assigned_to` INT NULL ,
  `price` FLOAT(6,2) NOT NULL DEFAULT 0 ,
  `parceltype_id` INT NOT NULL ,
  `delivered_on` DATE NULL ,
  PRIMARY KEY (`package_id`),
  INDEX `fk_Packages_Couriers1` (`assigned_to` ASC) ,CONSTRAINT `fk_Packages_Couriers1`
    FOREIGN KEY (`assigned_to` )
    REFERENCES `schoolProject`.`Couriers` (`courier_id` ));


-- -----------------------------------------------------
-- Table `schoolProject`.`Logins`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `schoolProject`.`Logins` (
  `logins_id` INT NOT NULL AUTO_INCREMENT ,
  `courier_id` INT NOT NULL ,
  `logged_on` DATETIME NOT NULL ,
  PRIMARY KEY (`logins_id`));

ALTER TABLE logins ADD CONSTRAINT FOREIGN KEY (courier_id) REFERENCES couriers(courier_id);
ALTER TABLE parcels ADD CONSTRAINT FOREIGN KEY (sender_customer_id) REFERENCES customers(customer_id);
ALTER TABLE parcels ADD CONSTRAINT FOREIGN KEY (recipient_customer_id) REFERENCES customers(customer_id);
ALTER TABLE parcels ADD CONSTRAINT FOREIGN KEY (parceltype_id) REFERENCES parceltype(parceltype_id);