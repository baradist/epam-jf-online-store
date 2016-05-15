ALTER TABLE `store`.`order_item` 
CHANGE COLUMN `order` `order_` INT(11) NOT NULL ;

ALTER TABLE `store`.`person` 
ADD UNIQUE INDEX `email_UNIQUE` (`email` ASC);