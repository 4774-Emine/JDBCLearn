package TelRehberi;

import java.util.ArrayList;
import java.util.Scanner;

public class Runner {

    static ArrayList<Kayit> telList = new ArrayList<Kayit>();
    static Kayit kayitObj;
    static Utilities dbMethods;
    static Scanner input = new Scanner(System.in);
    static Scanner inputLN = new Scanner(System.in);//dummy için oluşturduk

    public static void main(String[] args) {

        menu();

    }//main sonu

    private static void menu() {
        System.out.println("-------------------------");
        System.out.println("1 - List Records\n2 - Delete Records\n3 - UpDate Recod\n4 - Add new Record\nX - Exit\n" +
                "-------------------------\nSeçiminiz : ");
        String secim = input.next().toUpperCase();
        switch (secim) {
            case "1":
                listRecords();
                break;
            case "2":
                deleteRecord();
                break;
            case "3":
                updateRecord();
                break;
            case "4":
                addNewRecord();
                break;
            case "X":
                System.out.println("sistem çıkışınız yapılmıştır  ... ");
                break;
            default:
                System.out.println("hatalı giriş ");
                menu();

        }


    }

    private static void addNewRecord() {
        System.out.println("-------------------------\nrecord ekleme  : ");


        System.out.print(" isim : ");
        String isim = input.next();
        System.out.print("tel : ");
        String tel = inputLN.nextLine();

        kayitObj.setIsim(isim);
        kayitObj.setTel(tel);
        telList.add(kayitObj);
        dbMethods.addData(kayitObj);


        menu();
    }


    private static void updateRecord() {
        System.out.println("-------------------------\nupdate edilecek KAYIT ID : ");

        int updateId = input.nextInt();
        for (int i = 0; i < telList.size(); i++) {
            if (updateId == telList.get(i).getId()) {
                System.out.printf("%5d%20s%15s%n", telList.get(i).getId(), telList.get(i).getIsim(), telList.get(i).getTel());
                System.out.print("kayıt yenilemeyi onaylıyor musunuz (E:evet)");

                String onay = input.next();
                if (onay.equalsIgnoreCase("E")) {
                    System.out.println("güncellemek istemediğiniz alana X giriniz");
                    System.out.print("yeni isim : ");
                    String yeniIsim = input.next();
                    System.out.print("yeni tel : ");
                    String yeniTel = inputLN.nextLine();
                    if (yeniIsim.equalsIgnoreCase("x")) {
                        yeniIsim = telList.get(i).getIsim();//eski isim yeni isim olarak atandı
                    }
                    telList.get(i).setIsim(yeniIsim);
                    if (yeniTel.equalsIgnoreCase("x")) {
                        yeniTel = telList.get(i).getTel();//eski tel yeni tel olarak atandı
                    }
                    telList.get(i).setTel(yeniTel);


                    dbMethods.updatData(telList.get(i));
                } else System.out.println("update  işlemi iptal edildi");
            } else System.out.println("update ID bulunamadı");
        }
        menu();
    }

    private static void deleteRecord() {
        System.out.println("-------------------------\nsilinecek KAYIT ID : ");

        int silinecekId = input.nextInt();
        for (int i = 0; i < telList.size(); i++) {
            if (silinecekId == telList.get(i).getId()) {
                System.out.printf("%5d%20s%15s%n", telList.get(i).getId(), telList.get(i).getIsim(), telList.get(i).getTel());
                System.out.print("kayıt silmeyi onaylıyor musunuz (E:evet)");

                String onay = input.next();

                if (onay.equalsIgnoreCase("E")) {
                    telList.remove(telList.get(i));
                    dbMethods.deleteData(telList.get(i));
                } else System.out.println("silme işlemi iptal edildi");
            } else System.out.println("silinecek ID bulunamadı");
        }
        menu();
    }

    private static void listRecords() {
        System.out.printf("%4S%20S%17S\n", "id", "isim soyisim", "telefon no");
        System.out.printf("%4S%20S%17S\n", "--", "------------", "------------");
        for (int i = 0; i < telList.size(); i++) {
            System.out.printf("%4d%20s%17s\n", telList.get(i).getId(), telList.get(i).getIsim(), telList.get(i).getTel());
        }
        if (telList.size() == 0) {
            System.out.println("listelenecek kayıt bulunamadı");
        }
    }
}//Class sonu
