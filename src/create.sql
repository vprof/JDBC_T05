/*create schema*/
CREATE SCHEMA `myjoinsdb`;

/*create tables*/
  CREATE TABLE `employees` (
  `e_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`e_id`));
  
  CREATE TABLE `salary` (
  `e_id` INT NOT NULL,
  `salary` INT NOT NULL,
  `job_title` VARCHAR(30) NULL,
  PRIMARY KEY (`e_id`));
  
  CREATE TABLE `personalinfo` (
  `e_id` INT NOT NULL,
  `marital_status` VARCHAR(30) NULL,
  `birthday` DATE NULL,
  `address` VARCHAR(100) NULL,
  PRIMARY KEY (`e_id`));
  
  /*insert into employees
  (name, phone)
  values
  ('John', '555-963-147');
  
  insert into salary
  (e_id, salary, job_title)
  values
  (3, 59000, 'QA');
  
  insert into personalinfo
  (e_id, marital_status, birthday, address)
  values
  (3, 'single', '1997-02-28', 'NY');*/