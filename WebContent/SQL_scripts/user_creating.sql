CREATE TABLE `users`.`user` (
	`id` INT AUTO_INCREMENT,
	`login` VARCHAR(30) NOT NULL,
	`password` VARCHAR(30) NOT NULL,
	`birthDate` DATE NOT NULL,
	`e-mail` VARCHAR(30),
	`id_role` INT NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY (`id_role`) REFERENCES `users`.`role` (id)); 