CREATE TABLE IF NOT EXISTS role
(
    id bigserial NOT NULL,
    name character varying(15) COLLATE pg_catalog.default NOT NULL,
    CONSTRAINT PK_role_Id PRIMARY KEY (id),
    CONSTRAINT UQ_role_Name UNIQUE (name)
    );

CREATE TABLE IF NOT EXISTS "user"
(
    id bigserial NOT NULL,
    username character varying(45) COLLATE pg_catalog.default NOT NULL,
    email character varying(50) COLLATE pg_catalog.default NOT NULL,
    role_id bigint NOT NULL DEFAULT 1,
    password character varying(60) COLLATE pg_catalog.default NOT NULL,
    CONSTRAINT PK_User_Id PRIMARY KEY (id),
    CONSTRAINT UQ_User_Username UNIQUE (username),
    CONSTRAINT UQ_Email UNIQUE (email),
    CONSTRAINT FK_User_has_Role FOREIGN KEY (role_id)
    REFERENCES role (id) MATCH SIMPLE
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
    CONSTRAINT "CK_User_Username" CHECK (username::text ~ '^[A-Za-z0-9 А-Яа-яЁё-]{3,45}'::text),
    CONSTRAINT CK_Email CHECK (email::text ~ '^[a-zA-Z0-9.!#$%&''*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$'::text),
    CONSTRAINT CK_Password_strength CHECK (password::text ~ '^[A-Za-z0-9#$&\/%-\._]{8,60}$'::text)
    );

CREATE TABLE IF NOT EXISTS personality
(
    series_and_number character varying(10) NOT NULL,
    firstname character varying(45) COLLATE pg_catalog.default NOT NULL,
    lastname character varying(45) COLLATE pg_catalog.default NOT NULL,
    patronymic character varying(45) COLLATE pg_catalog.default,
    user_id bigint NOT NULL,
    CONSTRAINT PK_Personality_Id PRIMARY KEY (series_and_number),
    CONSTRAINT FK_Personality_belongs_to_User FOREIGN KEY (user_id)
    REFERENCES "user" (id) MATCH SIMPLE
    ON UPDATE cascade
    ON DELETE NO ACTION,
    CONSTRAINT CK_Personality_Name CHECK (firstname::text ~ '^[ А-Яа-яЁё]{2,45}$'::text),
    CONSTRAINT CK_Personality_Lastname CHECK (lastname::text ~ '^[ А-Яа-яЁё-]{2,45}$'::text),
    CONSTRAINT CK_Personality_Patronymic CHECK (patronymic::text ~ '^[ А-Яа-яЁё]{2,45}$'::text),
    CONSTRAINT CK_Personality_Series_and_number CHECK (series_and_number::text ~ '^[0-9]{10}$'::text)
    );

CREATE TABLE IF NOT EXISTS trip_status
(
    id bigserial NOT NULL,
    name character varying(45) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT PK_Trip_status_Id PRIMARY KEY (id),
    CONSTRAINT UQ_Trip_status_Name UNIQUE (name),
    CONSTRAINT CH_Trip_status_Name CHECK (name::text ~ '^[ А-Яа-яЁё]{3,45}$'::text)
);

CREATE TABLE IF NOT EXISTS trip
(
    id bigserial NOT NULL,
    status_id bigint NOT NULL,
    places smallint NOT NULL,
    cost bigint NOT NULL,
    driver_id bigint NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    CONSTRAINT PK_Trip_Id PRIMARY KEY (id),
    CONSTRAINT FK_Trip_has_Status FOREIGN KEY (status_id)
        REFERENCES trip_status (id) MATCH SIMPLE
        ON UPDATE cascade
        ON DELETE NO ACTION,
    CONSTRAINT CH_Trip_Places CHECK ( places > 0 AND places < 201 ),
    CONSTRAINT CH_Trip_Cost CHECK ( cost >= 0 AND cost <= 150000 ),
    CONSTRAINT CH_Route_Departure_time CHECK (departure_time > now()),
    CONSTRAINT FK_Trip_occupies_Driver FOREIGN KEY (driver_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE cascade
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS location
(
    id bigserial NOT NULL,
    name character varying(45) COLLATE pg_catalog.default NOT NULL,
    CONSTRAINT PK_Location_Id PRIMARY KEY (id),
    CONSTRAINT UQ_Location_Name UNIQUE (name),
    CONSTRAINT CH_Location_Name CHECK (name::text ~ '^[А-Яа-яЁё ,.-]{4,45}$'::text)
);

CREATE TABLE IF NOT EXISTS route_sequence
(
    id bigserial NOT NULL,
    trip_id bigint NOT NULL,
    location_id bigint NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    CONSTRAINT PK_Route_Sequence_Id PRIMARY KEY (id),
    CONSTRAINT FK_Route_sequence_belongs_to_Trip FOREIGN KEY (trip_id)
        REFERENCES trip (id) MATCH SIMPLE
        ON UPDATE cascade
        ON DELETE NO ACTION,
    CONSTRAINT FK_Route_sequence_has_Location FOREIGN KEY (location_id)
        REFERENCES location (id) MATCH SIMPLE
        ON UPDATE cascade
        ON DELETE NO ACTION,
    CONSTRAINT CH_Route_sequence_Arrival_time CHECK (arrival_time > now())
);

CREATE TABLE IF NOT EXISTS bill_status
(
    id bigserial NOT NULL,
    name character varying(45) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT PK_Bill_status_Id PRIMARY KEY (id),
    CONSTRAINT UQ_Bill_status_Name UNIQUE (name),
    CONSTRAINT CH_Bill_status_Name CHECK (name::text ~ '^[А-Яа-яЁё]{3,45}$'::text)
);

CREATE TABLE IF NOT EXISTS bill
(
    id bigserial NOT NULL,
    status_id bigint NOT NULL default 1,
    trip_id bigint NOT NULL,
    consumer_id bigint NOT NULL,
    CONSTRAINT PK_Bill_Id PRIMARY KEY (id),
    CONSTRAINT FK_Bill_has_Status FOREIGN KEY (status_id)
        REFERENCES bill_status (id) MATCH SIMPLE
        ON UPDATE cascade
        ON DELETE NO ACTION,
    CONSTRAINT FK_Bill_belongs_to_Trip FOREIGN KEY (trip_id)
        REFERENCES trip (id) MATCH SIMPLE
        ON UPDATE cascade
        ON DELETE NO ACTION,
    CONSTRAINT FK_Bill_has_Consumer FOREIGN KEY (consumer_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE cascade
        ON DELETE NO ACTION
);