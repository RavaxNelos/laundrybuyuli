# Laundry Bu Yuli - Database Integration

## Setup Database MySQL

### 1. Install MySQL
- Download dan install MySQL Server dari https://dev.mysql.com/downloads/mysql/
- Atau gunakan XAMPP yang sudah include MySQL

### 2. Buat Database
- Jalankan script `database_setup.sql` di MySQL Workbench atau command line:
```sql
mysql -u root -p < database_setup.sql
```
- Database yang dibuat: **`laundry_data`**

### 3. Konfigurasi Koneksi
Edit file `OrderDatabase.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3307/laundry_data";
private static final String USER = "root"; // ganti dengan username MySQL Anda
private static final String PASSWORD = ""; // ganti dengan password MySQL Anda
```

### 4. Tambahkan MySQL JDBC Driver
- Download mysql-connector-java-x.x.x.jar dari https://dev.mysql.com/downloads/connector/j/
- Tambahkan ke classpath project Anda

## Struktur Database

### Tabel `orders`
```sql
CREATE TABLE orders (
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
```

## Class Database

### OrderDatabase.java
Class tunggal yang menggabungkan koneksi database dan operasi CRUD untuk orders.
- **Koneksi Database**: Method untuk connect dan disconnect ke MySQL
- **CRUD Operations**: Semua operasi untuk mengelola data orders

**Method utama:**
- `saveOrder(Order order)` - Simpan order baru
- `getAllOrders()` - Ambil semua order
- `updateOrder(Order order)` - Update order existing
- `deleteOrder(String orderId)` - Hapus order
- `getOrdersByCustomerId(String customerId)` - Ambil order per customer
- `testConnection()` - Test koneksi database
- `closeConnection()` - Tutup koneksi database

## Migrasi dari File ke Database

### Perubahan yang dilakukan:
1. **tesGUI.java**: Load orders dari database di `main()`
2. **menuPesan.java**: Load orders dari database saat panel dibuat, simpan menggunakan `OrderDAO.saveOrder()`
3. **menuTampilkan.java**: Load orders dari database saat panel dibuat

### File yang masih menggunakan file system:
- `customers.txt` - data customer masih disimpan di file
- `services` - masih hardcoded di `appData.java`

## Testing

1. Jalankan script database
2. Compile dan run aplikasi
3. Test membuat pesanan baru
4. Test melihat pesanan di menuTampilkan
5. Cek data di database MySQL

## Troubleshooting

### Error: "MySQL JDBC Driver tidak ditemukan"
- Pastikan mysql-connector-java.jar sudah ditambahkan ke classpath

### Error: "Gagal koneksi ke database"
- Cek apakah MySQL service sedang running
- Verifikasi username, password, dan URL koneksi
- Pastikan database `laundry_data` sudah dibuat

### Error: "Unknown database 'laundry_buyuli'"
- Pastikan Anda sudah membuat database dengan nama **`laundry_data`**
- Jalankan script `database_setup.sql` terlebih dahulu

## Catatan
- Customer data masih disimpan di file `customers.txt`
- Services masih hardcoded di `appData.java`
- Database name: `laundry_data`
- Untuk production, sebaiknya semua data dipindah ke database