CREATE TABLE IF NOT EXISTS `permission`
(
    `id`              BIGINT       NOT NULL AUTO_INCREMENT,
    `permission_name` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `role`
(
    `id`        BIGINT       NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `role_permissions`
(
    `role_id`       BIGINT NOT NULL,
    `permission_id` BIGINT NOT NULL,
    PRIMARY KEY (`role_id`, `permission_id`),
    KEY `fk_role_permissions_key` (`permission_id` ASC),
    CONSTRAINT `fk_role_permissions_key` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
    CONSTRAINT `fk_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);


CREATE TABLE IF NOT EXISTS `user`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `email`      VARCHAR(70) NULL DEFAULT NULL,
    `first_name` VARCHAR(45) NULL DEFAULT NULL,
    `is_enabled` BIT(1)      NOT NULL,
    `last_name`  VARCHAR(60) NULL DEFAULT NULL,
    `password`   VARCHAR(60) NULL DEFAULT NULL,
    `username`   VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_user_email` (`email` ASC),
    UNIQUE INDEX `UK_user_username` (`username` ASC)
);


CREATE TABLE IF NOT EXISTS `user_roles`
(
    `user_id` BIGINT NOT NULL,
    `role_id` BIGINT NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    KEY `fk_user_roles_key` (`role_id` ASC),
    CONSTRAINT `fk_user_roles_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `fk_user_roles_key` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);


INSERT INTO `role` (id , role_name)
SELECT *
FROM (SELECT '1', 'ADMIN') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `role` WHERE id = '1') LIMIT 1;

INSERT INTO `role` (id , role_name)
SELECT *
FROM (SELECT '2', 'MODERATOR') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `role` WHERE id = '2') LIMIT 1;

INSERT INTO `role` (id , role_name)
SELECT *
FROM (SELECT '3', 'USER') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `role` WHERE id = '3') LIMIT 1;



INSERT INTO `permission` (id , permission_name)
SELECT *
FROM (SELECT '1', 'read') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM permission WHERE id = '1') LIMIT 1;

INSERT INTO `permission` (id , permission_name)
SELECT *
FROM (SELECT '2', 'write') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM permission WHERE id = '2') LIMIT 1;

INSERT INTO `permission` (id , permission_name)
SELECT *
FROM (SELECT '3', 'manage') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM permission WHERE id = '3') LIMIT 1;

INSERT INTO `permission` (id , permission_name)
SELECT *
FROM (SELECT '4', 'rent') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM permission WHERE id = '4') LIMIT 1;



INSERT INTO `role_permissions` (role_id , permission_id)
SELECT *
FROM (SELECT '1' AS role_id, '1' AS permission_id) AS tmpTable
WHERE NOT EXISTS (SELECT role_id ,permission_id  FROM role_permissions WHERE role_id = '1' AND permission_id = '1') LIMIT 1;

INSERT INTO `role_permissions` (role_id , permission_id)
SELECT *
FROM (SELECT '1' AS role_id, '2' AS permission_id) AS tmpTable
WHERE NOT EXISTS (SELECT role_id ,permission_id  FROM role_permissions WHERE role_id = '1' AND permission_id = '2') LIMIT 1;

INSERT INTO `role_permissions` (role_id , permission_id)
SELECT *
FROM (SELECT '1' AS role_id, '3' AS permission_id) AS tmpTable
WHERE NOT EXISTS (SELECT role_id ,permission_id  FROM role_permissions WHERE role_id = '1' AND permission_id = '3') LIMIT 1;

INSERT INTO `role_permissions` (role_id , permission_id)
SELECT *
FROM (SELECT '2' AS role_id, '1' AS permission_id) AS tmpTable
WHERE NOT EXISTS (SELECT role_id ,permission_id  FROM role_permissions WHERE role_id = '2' AND permission_id = '1') LIMIT 1;

INSERT INTO `role_permissions` (role_id , permission_id)
SELECT *
FROM (SELECT '2' AS role_id, '3' AS permission_id) AS tmpTable
WHERE NOT EXISTS (SELECT role_id ,permission_id  FROM role_permissions WHERE role_id = '2' AND permission_id = '3') LIMIT 1;

INSERT INTO `role_permissions` (role_id , permission_id)
SELECT *
FROM (SELECT '3' AS role_id, '1' AS permission_id) AS tmpTable
WHERE NOT EXISTS (SELECT role_id ,permission_id  FROM role_permissions WHERE role_id = '3' AND permission_id = '1') LIMIT 1;

INSERT INTO `role_permissions` (role_id , permission_id)
SELECT *
FROM (SELECT '3' AS role_id, '4' AS permission_id) AS tmpTable
WHERE NOT EXISTS (SELECT role_id ,permission_id  FROM role_permissions WHERE role_id = '3' AND permission_id = '4') LIMIT 1;

