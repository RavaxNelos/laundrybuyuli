
import java.util.ArrayList;
import java.util.Scanner;

public class loginDashboard {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Customer> customers = new ArrayList<>();
        String regID = "", passLogin, confirmPass = "", nameLogin = "", noReg = "", nameReg = "", passReg = "", alamat = "";
        String namaUser = "", passUser = "";
        int choose;
        customerManage.loadCustomerDariFile(customers);
        do {
            System.out.println("Login Dashboard Laundry Bu Yuli");
            System.out.println("1. Register\n2. Login");
            System.out.print("Choose : ");
            choose = sc.nextInt();
            sc.nextLine();
            switch (choose) {
                case 0:
                    System.out.println("Keluar!");
                    break;
                case 1:
                    do {
                        System.out.print("Masukkan Nama       : ");
                        nameReg = sc.nextLine();
                        System.out.print("Masukkan Nomor HP   : ");
                        noReg = sc.nextLine();
                        System.out.print("Masukkan Alamat     : ");
                        alamat = sc.nextLine();
                        System.out.print("Masukkan Password   : ");
                        passReg = sc.nextLine();
                        System.out.print("Konfirmasi Password : ");
                        confirmPass = sc.nextLine();
                    } while (!passReg.equals(confirmPass));
                    Customer cust = new Customer(regID, nameReg, noReg, alamat, passReg);
                    customers.add(cust);
                    customerManage.simpanCustomer(cust);
                    System.out.println("Pendaftaran berhasil!");
                    break;
                case 2:
                    do {
                        System.out.print("Masukkan Nama       : ");
                        nameLogin = sc.nextLine();
                        System.out.print("Masukkan Password   : ");
                        passLogin = sc.nextLine();
                        try {
                            java.io.BufferedReader br = new java.io.BufferedReader(
                                    new java.io.FileReader("customers.txt"));
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] data = line.split("\\|");
                                namaUser = data[1];
                                passUser = data[4];
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    } while (namaUser == nameReg && passUser == passReg);
                    System.out.println("Anda berhasil login!");

            }

        } while (choose != 0);

    }
}
