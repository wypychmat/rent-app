CREATE TABLE IF NOT EXISTS`register_token`
(
    `id`               BIGINT NOT NULL AUTO_INCREMENT,
    `confirmed_at`     DATETIME(6)  DEFAULT NULL,
    `epoch_created_at` BIGINT       DEFAULT NULL,
    `epoch_expired_at` BIGINT       DEFAULT NULL,
    `is_confirmed`     BIT(1) NOT NULL,
    `token`            VARCHAR(255) DEFAULT NULL,
    `user_id`          BIGINT       DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_register_token_token` (`token`),
    KEY `FK_register_token_user_user_id` (`user_id`),
    CONSTRAINT `FK_register_token_user_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);