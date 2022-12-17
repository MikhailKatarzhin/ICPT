INSERT INTO
    role (id, name)
    VALUES
       (1, 'ПОТРЕБИТЕЛЬ'), (2, 'ВОДИТЕЛЬ'), (3, 'АДМИНИСТРАТОР')
ON CONFLICT (id)
    DO UPDATE SET id = Excluded.id;

INSERT INTO
    "user" (id, email, role_id, password)
VALUES
    (0, 'volodov@mail.ru', 1, '$2a$10$m/GwtXT2pD5VeGgpdGkNLeZVg9C0NG/sRhiPkkvKXrZj6wXtiUJze')
ON CONFLICT (id)
    DO NOTHING;

INSERT INTO
    personality (series_and_number, firstname, lastname, user_id)
VALUES
    ('0000000000', 'Никита', 'Володов', 0)
ON CONFLICT (series_and_number)
    DO NOTHING;

INSERT INTO
    trip_status (id, name)
VALUES
    (1, 'Запланирована'), (2, 'Проходит'), (3,'Окончена'), (4, 'Отменена')
ON CONFLICT (id)
    DO NOTHING;

INSERT INTO
    bill_status (id, name)
VALUES
    (1, 'Оплачен'), (2, 'Выполняется'), (3, 'Выполнен'), (4, 'Отменен')
ON CONFLICT (id)
    DO NOTHING;