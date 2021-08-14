CREATE TABLE IF NOT EXISTS `spring`.`user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted_on` datetime(6) DEFAULT NULL,
    `uid` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `enabled` bit(1) NOT NULL,
    `forgot_password_code` varchar(255) DEFAULT NULL,
    `forgot_password_code_expires_on` datetime(6) DEFAULT NULL,
    `height` int(11) DEFAULT NULL,
    `measurement_system` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `verification_code` varchar(255) DEFAULT NULL,
    `verification_expires_on` datetime(6) DEFAULT NULL,
    `weight` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `spring`.`role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted_on` datetime(6) DEFAULT NULL,
    `uid` varchar(255) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `spring`.`user_roles` (
      `user_id` bigint(20) NOT NULL,
      `roles_id` bigint(20) NOT NULL,
      PRIMARY KEY (`user_id`,`roles_id`),
      KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`),
      CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
      CONSTRAINT `FKj9553ass9uctjrmh0gkqsmv0d` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `spring`.`exercise` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted_on` datetime(6) DEFAULT NULL,
    `uid` varchar(255) DEFAULT NULL,
    `category` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `equipment` varchar(255) DEFAULT NULL,
    `force_type` varchar(255) DEFAULT NULL,
    `level` varchar(255) DEFAULT NULL,
    `mechanic` varchar(255) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    `primary_muscles` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `spring`.`workout` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   `deleted_on` datetime DEFAULT NULL,
   `uid` varchar(255) DEFAULT NULL,
   `name` varchar(255) DEFAULT NULL,
   `user_id` bigint(20) NOT NULL,
   PRIMARY KEY (`id`),
   KEY `FKfd6lahc24vib7n7vw2ekecn00` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `spring`.`workout_exercise` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_on` datetime DEFAULT NULL,
    `uid` varchar(255) DEFAULT NULL,
    `reps` int(11) NOT NULL,
    `rest_period` int(11) NOT NULL,
    `exercise_id` bigint(20) DEFAULT NULL,
    `workout_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKalytxvdcpsg2e2oo8ihk55dm2` (`exercise_id`),
    KEY `FKqultuq4g6w47iqdaf0vb8ff3j` (`workout_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
