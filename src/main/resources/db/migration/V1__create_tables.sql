create SCHEMA IF NOT EXISTS springproject;
create TABLE IF NOT EXISTS springproject.users (
    id BIGINT NOT NULL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    user_name VARCHAR(255),
    password VARCHAR(255),
    last_password_reset_date TIMESTAMP,
    email VARCHAR(255),
    user_created TIMESTAMP,
    user_updated TIMESTAMP,
    user_status VARCHAR(255)
);

create TABLE IF NOT EXISTS springproject.files (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    file_path VARCHAR(255),
    deleted BOOLEAN
);

create TABLE IF NOT EXISTS springproject.events (
    id BIGINT NOT NULL PRIMARY KEY,
    file_created TIMESTAMP,
    file_status VARCHAR(255),
    user_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES springproject.users (id),
    FOREIGN KEY (file_id) REFERENCES springproject.files (id)
);

create TABLE IF NOT EXISTS springproject.roles (
    id BIGINT NOT NULL PRIMARY KEY,
    user_status VARCHAR(255),
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES springproject.users (id)
);

create TABLE IF NOT EXISTS springproject.user_roles (
    user_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES springproject.users (id),
    FOREIGN KEY (file_id) REFERENCES springproject.files (id)
);