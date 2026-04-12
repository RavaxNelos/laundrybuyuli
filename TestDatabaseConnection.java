public class TestDatabaseConnection {
    public static void main(String[] args) {
        System.out.println("Testing OrderDatabase connection...");

        // Test koneksi
        if (OrderDatabase.testConnection()) {
            System.out.println("✓ Database connection successful!");

            // Test ambil semua orders
            appData data = new appData();
            customerManage.loadCustomerDariFile(data.customers);

            System.out.println("Loading orders from database...");
            data.orders = OrderDatabase.getAllOrders(data.customers, data.services);

            System.out.println("Found " + data.orders.size() + " orders in database");

            // Tampilkan orders
            for (Order order : data.orders) {
                System.out.println(order);
            }

            // Tutup koneksi
            OrderDatabase.closeConnection();
            System.out.println("✓ Database test completed!");
        } else {
            System.out.println("✗ Database connection failed!");
        }
    }
}