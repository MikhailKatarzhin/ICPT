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
    CONSTRAINT "CK_User_Username" CHECK (username::text ~ '[A-Za-z0-9 А-Яа-я]{3,45}'::text),
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
    CONSTRAINT CK_Personality_Name CHECK (firstname::text ~ '^[ А-Яа-я]{2,45}$'::text),
    CONSTRAINT CK_Personality_Lastname CHECK (lastname::text ~ '^[ А-Яа-я]{2,45}$'::text),
    CONSTRAINT CK_Personality_Patronymic CHECK (patronymic::text ~ '^[ А-Яа-я]{2,45}$'::text),
    CONSTRAINT CK_Personality_Series_and_number CHECK (series_and_number::text ~ '^[0-9]{10}$'::text)
    );

CREATE TABLE IF NOT EXISTS bill_status
(
    id bigserial NOT NULL,
    name character varying(45) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT PK_Bill_status_Id PRIMARY KEY (id),
    CONSTRAINT UQ_Bill_status_Name UNIQUE (name),
    CONSTRAINT CH_Bill_status_Name CHECK (name::text ~ '^[А-Яа-я]{3,45}$'::text)
);

CREATE TABLE IF NOT EXISTS trip_status
(
    id bigserial NOT NULL,
    name character varying(45) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT PK_Trip_status_Id PRIMARY KEY (id),
    CONSTRAINT UQ_Trip_status_Name UNIQUE (name),
    CONSTRAINT CH_Trip_status_Name CHECK (name::text ~ '^[А-Яа-я]{3,45}$'::text)
);

CREATE TABLE IF NOT EXISTS route
(
    id bigserial NOT NULL,
    arrival_to character varying(45) COLLATE pg_catalog.default NOT NULL,
    arrival_from character varying(45) COLLATE pg_catalog.default NOT NULL,
    path_length bigint NOT NULL,
    cost bigint NOT NULL,
    CONSTRAINT PK_Route_Id PRIMARY KEY (id),
    CONSTRAINT CH_Route_Arrival_to CHECK (arrival_to::text ~ '^[А-Яа-я [punct]{4,45}$'::text),
    CONSTRAINT CH_Route_Arrival_from CHECK (arrival_from::text ~ '^[А-Яа-я [punct]{4,45}$'::text),
    CONSTRAINT CH_Route_Path_length CHECK (route.path_length > 0 AND route.path_length < 10000),
    CONSTRAINT CH_Route_Cost CHECK (route.cost >= 0 AND route.cost < 200000)
);

CREATE TABLE IF NOT EXISTS trip
(
    id bigserial NOT NULL,
    status_id bigint NOT NULL,
    places smallint NOT NULL,
    driver_id bigint NOT NULL,
    CONSTRAINT PK_Trip_Id PRIMARY KEY (id),
    CONSTRAINT FK_Trip_has_Status FOREIGN KEY (status_id)
        REFERENCES trip_status (id) MATCH SIMPLE
        ON UPDATE cascade
        ON DELETE NO ACTION,
    CONSTRAINT CH_Trip_Places CHECK ( places > 0 AND places < 201 ),
    CONSTRAINT FK_Trip_occupies_Driver FOREIGN KEY (driver_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE cascade
        ON DELETE NO ACTION
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

CREATE TABLE IF NOT EXISTS route_sequence
(
    id bigserial NOT NULL,
    trip_id bigint NOT NULL,
    rout_id bigint NOT NULL,
    sequence_number bigint NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    CONSTRAINT PK_Route_Sequence_Id PRIMARY KEY (id),
    CONSTRAINT FK_Route_sequence_belongs_to_Trip FOREIGN KEY (trip_id)
        REFERENCES trip (id) MATCH SIMPLE
        ON UPDATE cascade
        ON DELETE NO ACTION,
    CONSTRAINT FK_Route_sequence_has_Route FOREIGN KEY (rout_id)
        REFERENCES route (id) MATCH SIMPLE
        ON UPDATE cascade
        ON DELETE NO ACTION,
    CONSTRAINT CH_Route_Arrival_time CHECK (arrival_time > now() AND arrival_time > route_sequence.departure_time),
    CONSTRAINT CH_Route_Departure_time CHECK (route_sequence.departure_time > now() AND route_sequence.departure_time < route_sequence.arrival_time),
    CONSTRAINT CH_Sequence_number CHECK ( sequence_number > 0 )
);