DROP DATABASE piggybankdb;
DROP USER piggybankuser;
CREATE USER piggybankuser WITH PASSWORD 'password';
CREATE DATABASE piggybankdb WITH template=template0 owner=piggybankuser;
SET TIMEZONE = 'Africa/Lagos';
\c piggybankdb;
ALTER DEFAULT PRIVILEGES GRANT ALL ON TABLES TO piggybankuser;
ALTER DEFAULT PRIVILEGES GRANT ALL ON SEQUENCES TO piggybankuser;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY NOT NULL UNIQUE,
    first_name VARCHAR(25) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    email VARCHAR(40) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    phone_number VARCHAR(25) NOT NULL,
    date_of_birth DATE NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    roles TEXT [] NOT NULL
);

CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY NOT NULL UNIQUE,
    amount BIGINT NOT NULL,
    type VARCHAR(25) NOT NULL,
    remarks VARCHAR(100) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    user_id BIGINT NOT NULL
);

ALTER TABLE transactions ADD CONSTRAINT transactions_users_fk FOREIGN KEY (user_id) REFERENCES users(id);

CREATE SEQUENCE users_seq INCREMENT 1 START 1;
CREATE SEQUENCE transactions_seq INCREMENT 1 START 1;