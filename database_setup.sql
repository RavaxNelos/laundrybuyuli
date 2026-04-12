-- Script SQL untuk membuat database dan tabel orders
-- Jalankan script ini di MySQL Workbench atau command line MySQL

-- Buat database jika belum ada
CREATE DATABASE IF NOT EXISTS laundry_data;
USE laundry_data;

-- Buat tabel customers
CREATE TABLE IF NOT EXISTS customers (
    id VARCHAR(20) PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    no_hp VARCHAR(20),
    alamat TEXT,
    password VARCHAR(255) NOT NULL
);

-- Contoh data untuk testing customer
INSERT INTO customers (id, nama, no_hp, alamat, password) VALUES
('CUST001', 'Budi', '08123456789', 'Jl. Merdeka 1', 'pass123'),
('CUST002', 'Siti', '08129876543', 'Jl. Mawar 2', 'pass456');

-- Buat tabel orders
CREATE TABLE IF NOT EXISTS orders (
    id_order VARCHAR(20) PRIMARY KEY,
    customer_id VARCHAR(20) NOT NULL,
    service_id VARCHAR(10) NOT NULL,
    berat DOUBLE NOT NULL,
    harga DOUBLE NOT NULL,
    status ENUM('DITERIMA', 'DICUCI', 'DISETRIKA', 'SELESAI', 'DIANTAR') NOT NULL DEFAULT 'DITERIMA',
    sudah_bayar BOOLEAN NOT NULL DEFAULT FALSE,
    antar_jemput BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Contoh data untuk testing
INSERT INTO orders (id_order, customer_id, service_id, berat, harga, status, sudah_bayar, antar_jemput) VALUES
('ORD1', 'CUST001', 'SRV1', 2.5, 12500, 'DITERIMA', FALSE, TRUE),
('ORD2', 'CUST002', 'SRV2', 3.0, 18000, 'DICUCI', TRUE, FALSE);

-- Tampilkan data
SELECT * FROM customers;
SELECT * FROM orders;