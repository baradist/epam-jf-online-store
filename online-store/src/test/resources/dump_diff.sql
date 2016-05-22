ALTER TABLE `store`.`order_` 
ADD COLUMN `sum` DOUBLE NULL DEFAULT 0 AFTER `state`;

ALTER TABLE `store`.`invoice` 
DROP FOREIGN KEY `invoice_person`;
ALTER TABLE `store`.`invoice` 
CHANGE COLUMN `sum` `sum` DOUBLE NULL ,
CHANGE COLUMN `person` `manager` INT(11) NULL DEFAULT NULL ;
ALTER TABLE `store`.`invoice` 
ADD CONSTRAINT `invoice_person`
  FOREIGN KEY (`manager`)
  REFERENCES `store`.`person` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `store`.`invoice` 
CHANGE COLUMN `date` `date` DATETIME NOT NULL ;

USE `store`;

DELIMITER $$

DROP TRIGGER IF EXISTS store.order_item_AFTER_INSERT$$
USE `store`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `store`.`order_item_AFTER_INSERT` AFTER INSERT ON `order_item` FOR EACH ROW
BEGIN

UPDATE `store`.`order_`
SET
`sum` = (SELECT SUM(price * quantity) FROM order_item WHERE order_ = NEW.order_)
WHERE `id` = NEW.order_;

END$$
DELIMITER ;
USE `store`;

DELIMITER $$

DROP TRIGGER IF EXISTS store.order_item_AFTER_UPDATE$$
USE `store`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `store`.`order_item_AFTER_UPDATE` AFTER UPDATE ON `order_item` FOR EACH ROW
BEGIN

UPDATE `store`.`order_`
SET
`sum` = (SELECT SUM(price * quantity) FROM order_item WHERE order_ = NEW.order_)
WHERE `id` = NEW.order_;

END$$
DELIMITER ;
USE `store`;

DELIMITER $$

DROP TRIGGER IF EXISTS store.order_item_AFTER_DELETE$$
USE `store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `store`.`order_item_AFTER_DELETE` AFTER DELETE ON `order_item` FOR EACH ROW
BEGIN

UPDATE `store`.`order_`
SET
`sum` = (SELECT SUM(price * quantity) FROM order_item WHERE order_ = OLD.order_)
WHERE `id` = OLD.order_;

END$$
DELIMITER ;

USE `store`;

DELIMITER $$

DROP TRIGGER IF EXISTS store.invoice_item_AFTER_INSERT$$
USE `store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `store`.`invoice_item_AFTER_INSERT` AFTER INSERT ON `invoice_item` FOR EACH ROW
BEGIN

UPDATE `store`.`invoice`
SET `sum` = (SELECT SUM(price * quantity) FROM invoice_item WHERE invoice = NEW.invoice)
WHERE `id` = NEW.invoice;

END$$
DELIMITER ;
USE `store`;

DELIMITER $$

DROP TRIGGER IF EXISTS store.invoice_item_AFTER_UPDATE$$
USE `store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `store`.`invoice_item_AFTER_UPDATE` AFTER UPDATE ON `invoice_item` FOR EACH ROW
BEGIN

UPDATE `store`.`invoice`
SET `sum` = (SELECT SUM(price * quantity) FROM invoice_item WHERE invoice = NEW.invoice)
WHERE `id` = NEW.invoice;

END$$
DELIMITER ;
USE `store`;

DELIMITER $$

DROP TRIGGER IF EXISTS store.invoice_item_AFTER_DELETE$$
USE `store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `store`.`invoice_item_AFTER_DELETE` AFTER DELETE ON `invoice_item` FOR EACH ROW
BEGIN

UPDATE `store`.`invoice`
SET `sum` = (SELECT SUM(price * quantity) FROM invoice_item WHERE invoice = OLD.invoice)
WHERE `id` = OLD.invoice;

END$$
DELIMITER ;

ALTER TABLE `store`.`set_price` 
DROP FOREIGN KEY `set_price_person`;
ALTER TABLE `store`.`set_price` 
CHANGE COLUMN `person` `manager` INT(11) NOT NULL ;
ALTER TABLE `store`.`set_price` 
ADD CONSTRAINT `set_price_person`
  FOREIGN KEY (`manager`)
  REFERENCES `store`.`person` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `store`.`set_price_item` 
DROP FOREIGN KEY `spi_c_set_price`;
ALTER TABLE `store`.`set_price_item` 
CHANGE COLUMN `c_set_price` `set_price` INT(11) NOT NULL ;
ALTER TABLE `store`.`set_price_item` 
ADD CONSTRAINT `spi_c_set_price`
  FOREIGN KEY (`set_price`)
  REFERENCES `store`.`set_price` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
