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