CREATE TABLE `users`.`userMusicType` (
	`id` INT AUTO_INCREMENT,
	`id_user` INT NOT NULL,
	`id_musicType` INT NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`id_user`) REFERENCES `users`.`user` (id),
	FOREIGN KEY (`id_musicType`) REFERENCES `users`.`musicType` (id)); 