CREATE TABLE IF NOT EXISTS fitness_user (
    id bigserial PRIMARY KEY,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_on timestamp DEFAULT NULL,
    uid varchar(255) DEFAULT NULL,
    email varchar(255) DEFAULT NULL,
    enabled boolean NOT NULL,
    forgot_password_code varchar(255) DEFAULT NULL,
    forgot_password_code_expires_on timestamp DEFAULT NULL,
    height bigint DEFAULT NULL,
    measurement_system varchar(255) DEFAULT NULL,
    password varchar(255) DEFAULT NULL,
    verification_code varchar(255) DEFAULT NULL,
    verification_expires_on timestamp DEFAULT NULL,
    weight bigint DEFAULT NULL,
    image_url varchar(255) DEFAULT NULL
);




CREATE TABLE IF NOT EXISTS role (
    id bigserial PRIMARY KEY,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_on timestamp(6) DEFAULT NULL,
    uid varchar(255) DEFAULT NULL,
    name varchar(255) DEFAULT NULL
);



CREATE TABLE IF NOT EXISTS fitness_user_roles (
    user_id bigint NOT NULL,
    roles_id bigint NOT NULL,
    PRIMARY KEY (user_id,roles_id),
    FOREIGN KEY (user_id) REFERENCES fitness_user (id),
    FOREIGN KEY (roles_id) REFERENCES role (id)
);


CREATE TABLE IF NOT EXISTS exercise (
    id bigserial PRIMARY KEY,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_on timestamp DEFAULT NULL,
    uid varchar(255) DEFAULT NULL,
    equipment varchar(255) DEFAULT NULL,
    name varchar(255) DEFAULT NULL,
    target varchar(255) DEFAULT NULL,
    body_part varchar(255) DEFAULT NULL,
    image varchar(255) DEFAULT NULL
);


CREATE TABLE IF NOT EXISTS workout (
    id bigserial PRIMARY KEY,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_on timestamp DEFAULT NULL,
    uid varchar(255) DEFAULT NULL,
    name varchar(255) DEFAULT NULL,
    user_id bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES fitness_user (id)
);



CREATE TABLE IF NOT EXISTS workout_exercise (
    id bigserial PRIMARY KEY,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_on timestamp DEFAULT NULL,
    uid varchar(255) DEFAULT NULL,
    exercise_id bigint DEFAULT NULL,
    workout_id bigint NOT NULL,
    FOREIGN KEY (exercise_id) REFERENCES exercise (id),
    FOREIGN KEY (workout_id) REFERENCES workout (id)
);


CREATE TABLE IF NOT EXISTS exercise_set (
    id bigserial PRIMARY KEY,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_on timestamp DEFAULT NULL,
    uid varchar(255) DEFAULT NULL,
    reps bigint DEFAULT NULL,
    rest_period bigint DEFAULT NULL,
    weight bigint DEFAULT NULL,
    workout_exercise_id bigint DEFAULT NULL,
    FOREIGN KEY (workout_exercise_id) REFERENCES workout_exercise (id)
);

