INSERT INTO `engine` (id , engine)
SELECT *
FROM (SELECT '1', 'PETROL') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `engine` WHERE id = '1') LIMIT 1;

INSERT INTO `engine` (id , engine)
SELECT *
FROM (SELECT '2', 'DIESEL') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `engine` WHERE id = '2') LIMIT 1;

INSERT INTO `engine` (id , engine)
SELECT *
FROM (SELECT '3', 'HYBRID') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `engine` WHERE id = '3') LIMIT 1;

INSERT INTO `engine` (id , engine)
SELECT *
FROM (SELECT '4', 'ELECTRIC') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `engine` WHERE id = '4') LIMIT 1;


INSERT INTO `manufacturer` (id , manufacturer, country_code)
SELECT *
FROM (SELECT '1', 'Volkswagen', 'DE') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `manufacturer` WHERE id = '1') LIMIT 1;

INSERT INTO `manufacturer` (id , manufacturer,country_code)
SELECT *
FROM (SELECT '2', 'Peugeot', 'FR') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `manufacturer` WHERE id = '2') LIMIT 1;

INSERT INTO `manufacturer` (id , manufacturer, country_code)
SELECT *
FROM (SELECT '3', 'Opel', 'DE') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `manufacturer` WHERE id = '3') LIMIT 1;

INSERT INTO `manufacturer` (id , manufacturer, country_code)
SELECT *
FROM (SELECT '4', 'Skoda', 'DE') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `manufacturer` WHERE id = '4') LIMIT 1;



INSERT INTO `type` (id , type)
SELECT *
FROM (SELECT '1', 'CAR') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `type` WHERE id = '1') LIMIT 1;

INSERT INTO `type` (id , type)
SELECT *
FROM (SELECT '2', 'VAN') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `type` WHERE id = '2') LIMIT 1;

INSERT INTO `type` (id , type)
SELECT *
FROM (SELECT '3', 'MOTOR') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `type` WHERE id = '3') LIMIT 1;



INSERT INTO `segment` (id , segment)
SELECT *
FROM (SELECT '1', 'ECONOMY') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `segment` WHERE id = '1') LIMIT 1;

INSERT INTO `segment` (id , segment)
SELECT *
FROM (SELECT '2', 'BASIC') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `segment` WHERE id = '2') LIMIT 1;

INSERT INTO `segment` (id , segment)
SELECT *
FROM (SELECT '3', 'STANDARD') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `segment` WHERE id = '3') LIMIT 1;

INSERT INTO `segment` (id , segment)
SELECT *
FROM (SELECT '4', 'PREMIUM') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `segment` WHERE id = '4') LIMIT 1;

INSERT INTO `segment` (id , segment)
SELECT *
FROM (SELECT '5', 'LUXURY') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `segment` WHERE id = '5') LIMIT 1;

INSERT INTO `segment` (id , segment)
SELECT *
FROM (SELECT '6', 'SPORT') AS tmpTable
WHERE NOT EXISTS (SELECT id FROM `segment` WHERE id = '6') LIMIT 1;