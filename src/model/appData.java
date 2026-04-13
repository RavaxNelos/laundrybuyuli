package model;

import java.util.ArrayList;

public class AppData {

    public ArrayList<Customer> customers = new ArrayList<>();
    public ArrayList<Order> orders = new ArrayList<>();
    public ArrayList<LaundryService> services = new ArrayList<>();
    public ArrayList<Admin> admins = new ArrayList<>();
    public Customer currentUser;
    public Admin currentAdmin;
}
