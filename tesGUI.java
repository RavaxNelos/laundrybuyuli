
import javax.swing.*;
public class tesGUI {

    static JFrame frame = new JFrame("Laundry Bu Yuli");
    static appData data = new appData();
    public static void main(String[] args) {
        // Load customer dan service dari database
        data.customers = customerDatabase.getAllCustomers();
        data.services = serviceDatabase.getAllServices();
        // Load orders dari database menggunakan OrderDatabase
        data.orders = OrderDatabase.getAllOrders();
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showLoginPanel();
        frame.setVisible(true);
    }

    public static void showLoginPanel() {
        frame.setContentPane(new loginPanel(data));
        frame.revalidate();
        frame.repaint();
    }

    public static void showRegisterPanel() {
        frame.setContentPane(new registerPanel(data));
        frame.revalidate();
        frame.repaint();
    }

    public static void showDashboardPanel() {
        dashboardPanel panel = new dashboardPanel(data);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }
    public static void showMenuPesan() {
        menuPesan panel = new menuPesan(data);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }
    public static void showMenuTampilkan() {
        menuTampilkan panel = new menuTampilkan(data);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }
}
