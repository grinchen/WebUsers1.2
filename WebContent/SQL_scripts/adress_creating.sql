CREATE TABLE `users`.`adress` (
	`id` INT AUTO_INCREMENT,
	`country` VARCHAR(30) NOT NULL,
	`region` VARCHAR(30) NOT NULL,
	`city` VARCHAR(30),
	`street` VARCHAR(30),
	`building` VARCHAR(30),
	`app` INT,
	`id_user` INT NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX (`id_user`),
	FOREIGN KEY (`id_user`) REFERENCES `users`.`user` (id));