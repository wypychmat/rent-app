CREATE TABLE IF NOT EXISTS `bookings`
(
    `id`              BIGINT      NOT NULL AUTO_INCREMENT,
    `book_date`       DATETIME(6) NOT NULL,
    `end_rent_date`   DATETIME(6) NOT NULL,
    `expires_date`    DATETIME(6) NOT NULL,
    `start_rent_date` DATETIME(6) NOT NULL,
    `user_id`         BIGINT      NOT NULL,
    `vehicle_id`      BIGINT      NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_bookings_user_id` (`user_id` ASC) VISIBLE,
    INDEX `FK_bookings_vehicle_id` (`vehicle_id` ASC) VISIBLE,
    CONSTRAINT `FK_bookings_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FK_bookings_vehicle_id` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`)
);



CREATE TABLE IF NOT EXISTS `canceled_book`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `cancel_date` DATETIME(6) NOT NULL,
    `user_id`     BIGINT      NOT NULL,
    `vehicle_id`  BIGINT      NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_canceled_book_user_id` (`user_id` ASC) VISIBLE,
    INDEX `FK_canceled_book_vehicle_id` (`vehicle_id` ASC) VISIBLE,
    CONSTRAINT `FK_canceled_book_vehicle_id` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`),
    CONSTRAINT `FK_canceled_book_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);



CREATE TABLE IF NOT EXISTS `rent_history`
(
    `id`             BIGINT      NOT NULL AUTO_INCREMENT,
    `hire_date`      DATETIME(6) NOT NULL,
    `odometer_end`   BIGINT      NULL DEFAULT NULL,
    `odometer_start` BIGINT      NOT NULL,
    `return_date`    DATETIME(6) NULL DEFAULT NULL,
    `user_id`        BIGINT      NULL DEFAULT NULL,
    `vehicle_id`     BIGINT      NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_rent_history_user_id` (`user_id` ASC) VISIBLE,
    INDEX `FK_rent_history_vehicle_id` (`vehicle_id` ASC) VISIBLE,
    CONSTRAINT `FK_rent_history_vehicle_id` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`),
    CONSTRAINT `FK_rent_history_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);


