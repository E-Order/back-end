-- MySQL Script generated by MySQL Workbench
-- Fri May 18 22:57:49 2018
-- Model: New Model    Version: 1.0
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
CREATE SCHEMA IF NOT EXISTS `e-order` DEFAULT CHARACTER SET utf8mb4;
USE `e-order` ;

-- -----------------------------------------------------
-- Table `mydb`.`ProductCategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `e-order`.`product_category` (
  `category_id` INT NOT NULL auto_increment,
  `category_name` VARCHAR(64) NOT NULL,
  `category_type` INT NOT NULL,
  `create_time` timestamp not null default current_timestamp comment '创建时间',
  `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
  PRIMARY KEY (`category_id`),
  INDEX `index_category_type` (`category_type` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`product_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `e-order`.`product_info` (
  `product_id` VARCHAR(32) NOT NULL,
  `product_name` VARCHAR(64) NOT NULL,
  `product_price` DECIMAL(8,2) NOT NULL,
  `product_description` VARCHAR(64) NULL,
  `product_icon` VARCHAR(512) NULL,
  `product_stock` INT NOT NULL DEFAULT 0,
  `category_type` INT NOT NULL,
  `create_time` timestamp not null default current_timestamp comment '创建时间',
  `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
  PRIMARY KEY (`product_id`),
  INDEX `category_type_idx` (`category_type` ASC),
  CONSTRAINT `category_type`
    FOREIGN KEY (`category_type`)
    REFERENCES `e-order`.`product_category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`order_master`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `e-order`.`order_master` (
  `order_id` VARCHAR(32) NOT NULL,
  `desk_id` INT NOT NULL,
  `buyer_openid` VARCHAR(64) NOT NULL,
  `order_amount` DECIMAL(8,2) NOT NULL,
  `order_status` TINYINT(3) NOT NULL DEFAULT 0,
  `pay_status` TINYINT(3) NOT NULL DEFAULT 0,
  `create_time` timestamp not null default current_timestamp comment '创建时间',
  `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
  PRIMARY KEY (`order_id`),
  INDEX `index_buyer_openid` (`buyer_openid` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`order_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `e-order`.`order_detail` (
  `detail_id` VARCHAR(32) NOT NULL,
  `order_id` VARCHAR(32) NOT NULL,
  `product_quantity` INT NOT NULL,
  `product_id` VARCHAR(32) NOT NULL,
  `create_time` timestamp not null default current_timestamp comment '创建时间',
  `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
  PRIMARY KEY (`detail_id`),
  INDEX `index_order_id` (`order_id` ASC),
  INDEX `index_product_id` (`product_id` ASC),
  CONSTRAINT `order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `e-order`.`order_master` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `e-order`.`product_info` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
