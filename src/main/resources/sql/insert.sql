TRUNCATE TABLE theater CASCADE;
ALTER SEQUENCE theater_id_seq RESTART WITH 1;
INSERT INTO theater (name, address, info) VALUES
    ('Большой театр', 'Москва, Театральная площадь, 1', 'Самый большой театр Москвы.'),
    ('Маленький театр', 'Mосква, Театральная площадь, 2', 'Самый маленький театр Москвы'),
    ('Большой театр Уфы', 'Уфа, Театральная площадь, 2', 'Самый большой театр Уфы.');

TRUNCATE TABLE place CASCADE;
ALTER SEQUENCE place_id_seq RESTART WITH 1;
INSERT INTO place (number, place_type, theater_id) VALUES
    ('A1', 0, 1),
    ('A2', 0, 1),
    ('A3', 0, 1),
    ('Б1', 1, 1),
    ('Б2', 1, 1),
    ('Б3', 1, 1),
    ('В1', 2, 1),
    ('В2', 2, 1),
    ('В3', 2, 1),
    ('Г1', 3, 1),
    ('Г2', 3, 1),
    ('Г3', 3, 1),
    ('Е1', 4, 1),
    ('Е2', 4, 1),
    ('Е3', 4, 1);

TRUNCATE TABLE actor CASCADE;
ALTER SEQUENCE actor_id_seq RESTART WITH 1;
INSERT INTO actor (name, info, theater_id) VALUES
    ('Иванов Иван', 'Актер на 3 из 10', 1),
    ('Петров Иван', 'Актер на 4 из 10', 1),
    ('Андреев Адрей', 'Актер на 5 из 10', 2),
    ('Иванов Андрей', 'Актер на 6 из 10', 2),
    ('Петров Петр', 'Актер на 7 из 10', 3),
    ('Андреев Петр', 'Актер на 8 из 10', 3);

TRUNCATE TABLE director CASCADE;
ALTER SEQUENCE director_id_seq RESTART WITH 1;
INSERT INTO director (name, info, theater_id) VALUES
    ('Аврелий', 'Режиссер на 11 из 10', 1),
    ('Аргентий', 'Режиссер на 12 из 10', 1),
    ('Аквилий', 'Режиссер на 13 из 10', 2),
    ('Август', 'Режиссер на 14 из 10', 2),
    ('Аквитус', 'Режиссер на 15 из 10', 3),
    ('Августин', 'Режиссер на 16 из 10', 3);

TRUNCATE TABLE play CASCADE;
ALTER SEQUENCE play_id_seq RESTART WITH 1;
INSERT INTO play (name, duration, info, theater_id, director_id) VALUES
    ('Ромео и Джульетта', '3 hours', 'Оценка критиков: 8.9', 1, 1),
    ('Отелло и Дездемона', '2 hours', 'Оценка критиков: 8.2', 1, 2);

TRUNCATE TABLE role CASCADE;
ALTER SEQUENCE  role_id_seq RESTART WITH 1;
INSERT INTO role (actor_id, play_id, info) VALUES
    (1, 1, 'Ромео'),
    (2, 1, 'Джульетта'),
    (1, 2, 'Отелло'),
    (2, 2, 'Дездемона');

TRUNCATE TABLE performance CASCADE;
ALTER SEQUENCE performance_id_seq RESTART WITH 1;
INSERT INTO performance (play_id, datetime) VALUES
    (1, TIMESTAMP '2023-01-01 18:00:00'),
    (1, TIMESTAMP '2023-01-01 20:00:00'),
    (1, TIMESTAMP '2023-01-02 18:00:00'),
    (2, TIMESTAMP '2023-01-01 17:00:00'),
    (2, TIMESTAMP '2023-01-01 19:00:00'),
    (2, TIMESTAMP '2023-01-02 17:00:00');

TRUNCATE TABLE ticket_price;
ALTER SEQUENCE ticket_price_id_seq RESTART WITH 1;
INSERT INTO ticket_price (performance_id, place_type, price) VALUES
    (1, 0, 1000),
    (1, 1, 2000),
    (1, 2, 3000),
    (1, 3, 4000),
    (1, 4, 5000),
    (2, 0, 2000),
    (2, 1, 3000),
    (2, 2, 4000),
    (2, 3, 5000),
    (2, 4, 6000),
    (3, 0, 1000),
    (3, 1, 2000),
    (3, 2, 3000),
    (3, 3, 4000),
    (3, 4, 5000),
    (4, 0, 2000),
    (4, 1, 3000),
    (4, 2, 4000),
    (4, 3, 5000),
    (4, 4, 6000),
    (5, 0, 1000),
    (5, 1, 2000),
    (5, 2, 3000),
    (5, 3, 4000),
    (5, 4, 5000),
    (6, 0, 3000),
    (6, 1, 4000),
    (6, 2, 5000),
    (6, 3, 6000),
    (6, 4, 7000);

TRUNCATE TABLE ticket;
ALTER SEQUENCE ticket_id_seq RESTART WITH 1;
INSERT INTO ticket (performance_id, place_id, customer_name, customer_phone_number) VALUES
    (1, 1, 'Ваня 1', 'Телефон 1'),
    (2, 2, 'Ваня 2', 'Телефон 2'),
    (3, 3, 'Ваня 3', 'Телефон 3'),
    (4, 4, 'Ваня 4', 'Телефон 4'),
    (5, 5, 'Ваня 5', 'Телефон 5'),
    (6, 6, 'Ваня 6', 'Телефон 6'),
    (1, 7, 'Ваня 7', 'Телефон 7'),
    (2, 8, 'Ваня 8', 'Телефон 8'),
    (3, 9, 'Ваня 9', 'Телефон 9'),
    (4, 10, 'Ваня 10', 'Телефон 10'),
    (5, 11, 'Ваня 11', 'Телефон 11'),
    (6, 12, 'Ваня 12', 'Телефон 12'),
    (1, 13, 'Ваня 13', 'Телефон 13'),
    (2, 14, 'Ваня 14', 'Телефон 14'),
    (3, 15, 'Ваня 15', 'Телефон 15');
