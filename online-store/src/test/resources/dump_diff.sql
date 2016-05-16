ALTER TABLE `store`.`order_item` 
CHANGE COLUMN `order` `order_` INT(11) NOT NULL ;

ALTER TABLE `store`.`person` 
ADD UNIQUE INDEX `email_UNIQUE` (`email` ASC);

ALTER TABLE `store`.`person` 
CHANGE COLUMN `telephone` `phone` VARCHAR(100) NULL DEFAULT NULL ;

ALTER TABLE `store`.`person` 
CHANGE COLUMN `first_name` `first_name` VARCHAR(255) NULL ,
CHANGE COLUMN `password` `password` VARCHAR(255) NULL ;