create TABLE IF NOT EXISTS springproject.users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
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
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    file_path VARCHAR(255),
    deleted BOOLEAN
);

create TABLE IF NOT EXISTS springproject.events (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    file_created TIMESTAMP,
    file_status VARCHAR(255),
    user_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (file_id) REFERENCES files (id)
);

create TABLE IF NOT EXISTS springproject.roles (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id)
);