CREATE TABLE IF NOT EXISTS `engine`
(
    `id`     BIGINT       NOT NULL AUTO_INCREMENT,
    `engine` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_engine_engine` (`engine` ASC) VISIBLE
);

CREATE TABLE IF NOT EXISTS `manufacturer`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT,
    `country_code` VARCHAR(255) NULL DEFAULT NULL,
    `manufacturer` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_manufacturer_manufacturer` (`manufacturer` ASC) VISIBLE
);

CREATE TABLE IF NOT EXISTS `type`
(
    `id`   BIGINT       NOT NULL AUTO_INCREMENT,
    `type` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_type_type` (`type` ASC) VISIBLE
);

CREATE TABLE IF NOT EXISTS `segment`
(
    `id`      BIGINT       NOT NULL AUTO_INCREMENT,
    `segment` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_segment_segment` (`segment` ASC) VISIBLE
);

CREATE TABLE IF NOT EXISTS `model`
(
    `id`                    BIGINT       NOT NULL AUTO_INCREMENT,
    `description`           VARCHAR(255) NULL DEFAULT NULL,
    `model`                 VARCHAR(255) NULL DEFAULT NULL,
    `start_production_year` INT          NOT NULL,
    `manufacturer_id`       BIGINT       NOT NULL,
    `segment_id`            BIGINT       NOT NULL,
    `type_id`               BIGINT       NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_model_manufacturer_id` (`manufacturer_id` ASC) VISIBLE,
    INDEX `FK_model_segment_id` (`segment_id` ASC) VISIBLE,
    INDEX `FK_model_type_id` (`type_id` ASC) VISIBLE,
    CONSTRAINT `FK_model_manufacturer_id` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturer` (`id`),
    CONSTRAINT `FK_model_type_id` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`),
    CONSTRAINT `FK_model_segment_id` FOREIGN KEY (`segment_id`) REFERENCES `segment` (`id`)
);

CREATE TABLE IF NOT EXISTS `model_engines`
(
    `model_id`  BIGINT NOT NULL,
    `engine_id` BIGINT NOT NULL,
    PRIMARY KEY (`model_id`, `engine_id`),
    INDEX `FK_model_engines_engine_id` (`engine_id` ASC) VISIBLE,
    CONSTRAINT `FK_model_engines_engine_id` FOREIGN KEY (`engine_id`) REFERENCES `engine` (`id`),
    CONSTRAINT `FK_model_engines_model_id` FOREIGN KEY (`model_id`) REFERENCES `model` (`id`)
);

CREATE TABLE IF NOT EXISTS `vehicle`
(
    `id`                 BIGINT       NOT NULL AUTO_INCREMENT,
    `production_year`    INT          NOT NULL,
    `registration_plate` VARCHAR(255) NULL DEFAULT NULL,
    `status`             INT          NOT NULL,
    `engine_id`          BIGINT       NULL DEFAULT NULL,
    `model_id`           BIGINT       NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_vehicle_registration_plate` (`registration_plate` ASC) VISIBLE,
    INDEX `FK_vehicle_engine_id` (`engine_id` ASC) VISIBLE,
    INDEX `FK_vehicle_model_id` (`model_id` ASC) VISIBLE,
    CONSTRAINT `FK_vehicle_model_id` FOREIGN KEY (`model_id`) REFERENCES `model` (`id`),
    CONSTRAINT `FK_vehicle_engine_id` FOREIGN KEY (`engine_id`) REFERENCES `engine` (`id`)
);
