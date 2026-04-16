package main;

import gui.*;
import javax.swing.*;
import model.*;

public class MainMenuCourier {

    private static JFrame frame;
    private static AppData data;

    public static void main(String[] args) {
        data = new AppData();

        frame = new JFrame("Courier Laundry");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        showCourierLoginPanel();

        frame.setVisible(true);
    }

    // ===== SET PANEL =====
    public static void setPanel(JPanel panel) {
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint(); // 🔥 penting
    }

    public static void showCourierLoginPanel() {
        frame.setContentPane(new CourierLoginPanel(data));
        frame.revalidate();
        frame.repaint();
    }

    public static void showCourierMainPanel() {
        frame.setContentPane(new CourierMainPanel(data));
        frame.revalidate();
        frame.repaint();
    }

    public static void showCourierKirimPanel() {
        frame.setContentPane(new CourierKirimPanel(data));
        frame.revalidate();
        frame.repaint();
    }
}