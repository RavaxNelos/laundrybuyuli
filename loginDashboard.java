
import java.util.Scanner;

public class loginDashboard {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String passLogin, confirmPass = "", nameLogin = "", noReg = "", nameReg = "", passReg = "";
        int choose;
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
                        System.out.print("Masukkan Password   : ");
                        passReg = sc.nextLine();
                        System.out.print("Konfirmasi Password : ");
                        confirmPass = sc.nextLine();
                    } while (!passReg.equals(confirmPass));
                    System.out.println("Pendaftaran berhasil!");
                    break;
                case 2:
                    do {
                        System.out.print("Masukkan Nama       : ");
                        nameLogin = sc.nextLine();
                        System.out.print("Masukkan Password   : ");
                        passLogin = sc.nextLine();
                    } while (nameLogin.equalsIgnoreCase(nameReg) && passLogin.equals(passReg));
                    System.out.println("Anda berhasil login!");
                    break;
            }
        } while (choose != 0);

    }
}
