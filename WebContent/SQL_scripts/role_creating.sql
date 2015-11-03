CREATE TABLE `users`.`role` (
	`id` INT AUTO_INCREMENT,
	`roleName` ENUM ('user', 'moderator', 'admin') NOT NULL,
	PRIMARY KEY (`id`));