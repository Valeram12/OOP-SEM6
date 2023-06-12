INSERT INTO crew_members (id, name, position, surname) VALUES (1, 'Viktor', 'NAVIGATOR', 'Muzyka');
INSERT INTO crew_members (id, name, position, surname) VALUES (2, 'Petro', 'PILOT', 'Donchuk-Dontsov');
INSERT INTO crew_members (id, name, position, surname) VALUES (3, 'Stepan', 'OPERATOR', 'Ilchuk');
INSERT INTO crew_members (id, name, position, surname) VALUES (4, 'Roman', 'PILOT', 'Prokopchuk');
ALTER TABLE crew_members ALTER COLUMN id RESTART WITH 5;

INSERT INTO tours (id, departure_time, departure_from, arrival_time, destination)
VALUES (1, '2023-05-23 23:53:00' , 'Kyiv', '2023-05-24 16:09:00', 'Krakow');

INSERT INTO tours (id, departure_time, departure_from, arrival_time, destination)
VALUES (2, '2023-05-24 06:41:00' , 'Frankurt', '2023-05-24 20:59:00', 'New York');

INSERT INTO tours (id, departure_time, departure_from, arrival_time, destination)
VALUES (3, '2023-04-12 15:12:00' , 'Ankara', '2023-04-13 00:49:00', 'Berlin');

ALTER TABLE tours ALTER COLUMN id RESTART WITH 4;

INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (1, 1);
INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (3, 1);
INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (4, 1);

INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (2, 2);
INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (3, 2);

INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (1, 3);
INSERT INTO crew_members_flights (fk_crew_member_id, fk_flight_id) VALUES (4, 3);