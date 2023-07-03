package TelRehberi;

public class Kayit {
    //default parametresiz const var.

    int id;//db için table'a ilgili column tanımlandı
    String isim;//db için table'a ilgili column tanımlandı
    String tel;//db için table'a ilgili column tanımlandı

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
