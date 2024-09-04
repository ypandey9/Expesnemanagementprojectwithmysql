-- Create users table
CREATE TABLE users (
    username VARCHAR(50) COLLATE utf8mb4_unicode_ci NOT NULL PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL
);

-- Create authorities table
CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
);

-- Create a unique index on the authorities table
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);
