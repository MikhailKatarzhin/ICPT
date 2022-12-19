INSERT INTO
    role (id, name)
    VALUES
       (1, 'ПОТРЕБИТЕЛЬ'), (2, 'ВОДИТЕЛЬ'), (3, 'АДМИНИСТРАТОР')
ON CONFLICT (id)
    DO UPDATE SET id = Excluded.id;

INSERT INTO
    "user" (email, username, role_id, password)
VALUES
('vodila1@mail.ru', 'Driver1', 2, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('vodila2@mail.ru', 'Driver2', 2, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('vodila3@mail.ru', 'Driver3', 2, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('vodila4@mail.ru', 'Driver4', 2, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('vodila5@mail.ru', 'Driver5', 2, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('vodila6@mail.ru', 'Driver6', 2, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('vodila7@mail.ru', 'Driver7', 2, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('vodila8@mail.ru', 'Driver8', 2, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('vodila9@mail.ru', 'Driver9', 2, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('vodila10@mail.ru', 'Driver10', 2, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('vodila11@mail.ru', 'Driver11', 2, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
ON CONFLICT (id)
    DO NOTHING;

INSERT INTO
    personality (series_and_number, firstname, lastname, patronymic, user_id)
VALUES
    ('0000000001', 'Водила', 'Первый', 'Водятлович', 1)
     , ('0000000002', 'Водила', 'Второй', 'Водятлович', 2)
     , ('0000000003', 'Водила', 'Третий', 'Водятлович', 3)
     , ('0000000004', 'Водила', 'Четвёртый', 'Водятлович', 4)
     , ('0000000005', 'Водила', 'Пятый', 'Водятлович', 5)
     , ('0000000006', 'Водила', 'Шестой', 'Водятлович', 6)
     , ('0000000007', 'Водила', 'Седьмой', 'Водятлович', 7)
     , ('0000000008', 'Водила', 'Восьмой', 'Водятлович', 8)
     , ('0000000009', 'Водила', 'Девятый', 'Водятлович', 9)
     , ('0000000010', 'Водила', 'Десятый', 'Водятлович', 10)
     , ('0000000011', 'Водила', 'Одинадцатый', 'Водятлович', 11)
ON CONFLICT (series_and_number)
    DO NOTHING;

INSERT INTO
    "user" (email, username, role_id, password)
VALUES
    ('user1@mail.ru', 'User1', 1, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('user2@mail.ru', 'User2', 1, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('user3@mail.ru', 'User3', 1, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('user4@mail.ru', 'User4', 1, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('user5@mail.ru', 'User5', 1, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('user6@mail.ru', 'User6', 1, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('user7@mail.ru', 'User7', 1, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('user8@mail.ru', 'User8', 1, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('user9@mail.ru', 'User9', 1, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('user10@mail.ru', 'User10', 1, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
     , ('user11@mail.ru', 'User11', 1, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
ON CONFLICT (id)
    DO NOTHING;

INSERT INTO
    personality (series_and_number, firstname, lastname, patronymic, user_id)
VALUES
('0000000101', 'Пользователь', 'Первый', 'Водятлович', 12)
     , ('0000000202', 'Пользователь', 'Второй', 'Пользователевич', 13)
     , ('0000000203', 'Пользователь', 'Третий', 'Пользователевич', 14)
     , ('0000000204', 'Пользователь', 'Четвёртый', 'Пользователевич', 15)
     , ('0000000205', 'Пользователь', 'Пятый', 'Пользователевич', 16)
     , ('0000000206', 'Пользователь', 'Шестой', 'Пользователевич', 17)
     , ('0000000207', 'Пользователь', 'Седьмой', 'Пользователевич', 18)
     , ('0000000208', 'Пользователь', 'Восьмой', 'Пользователевич', 19)
     , ('0000000209', 'Пользователь', 'Девятый', 'Пользователевич', 20)
     , ('0000000210', 'Пользователь', 'Десятый', 'Пользователевич', 21)
     , ('0000000211', 'Пользователь', 'Одинадцатый', 'Пользователевич', 22)
ON CONFLICT (series_and_number)
    DO NOTHING;

INSERT INTO
    "user" (id, email, username, role_id, password)
VALUES
    (0, 'volodov@mail.ru', 'admin', 3, '$2a$10$iKUU3zfxo02jG21GsJsq/.oDu/YUIencDrMmz1kkg0pHE6XFYmSSy')
ON CONFLICT (id)
    DO NOTHING;

INSERT INTO
    personality (series_and_number, firstname, lastname, patronymic, user_id)
VALUES
    ('0000000000', 'Никита', 'Володов', 'Дмитриевич', 0)
ON CONFLICT (series_and_number)
    DO NOTHING;

INSERT INTO
    trip_status (id, name)
VALUES
    (1, 'Запланирована'), (2, 'Приём пассажиров'), (3, 'Проходит'), (4,'Окончена'), (5, 'Отменена')
ON CONFLICT (id)
    DO NOTHING;

INSERT INTO
    bill_status (id, name)
VALUES
    (1, 'Оплачен'), (2, 'Выполняется'), (3, 'Выполнен'), (4, 'Отменен'), (5, 'Возвращён')
ON CONFLICT (id)
    DO NOTHING;

INSERT INTO
    location (name)
VALUES
    ('г.Саратов, Первый-Пассажирский'), ('р.п.Лысые Горы'), ('с.Бутырки'), ('с.Первомайское'), ('г.Калининск'), ('п.Дубравный')
    , ('г.Аткарск'), ('г.Ртищево'), ('г.Аркадак'), ('г.Балашов'), ('г.Жирновс'), ('г.Котово')
    , ('г.Петров Вал'), ('г.Энгельс'), ('г.Красноармейск'), ('г.Маркс'), ('г.Петровск'), ('г.Пенза')
     , ('г.Никольск'), ('г.Городище'), ('г.Самара'), ('г.Тольятти'), ('г.Уральск'), ('г.Оренбург')
     , ('г.Москва'), ('г.Санкт-Петербург'), ('г.Волгоград'), ('г.Астрахань'), ('г.Уфа'), ('г.Краснодар');