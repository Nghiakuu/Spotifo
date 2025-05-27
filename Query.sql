-- Tạo database
CREATE DATABASE IF NOT EXISTS Spotifo;
USE Spotifo;

-- Bảng categories
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Bảng users
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    avatar VARCHAR(255),
    created_at DATETIME
);

-- Bảng music
CREATE TABLE IF NOT EXISTS music (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,categories
    category_id BIGINT,
    title VARCHAR(255) NOT NULL,
    lyrics TEXT,
    audio_file VARCHAR(255) NOT NULL,
    cover_image VARCHAR(255),
    uploaded_id BIGINT NOT NULL,
    created_at DATETIME,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (uploaded_id) REFERENCES users(id)
);

