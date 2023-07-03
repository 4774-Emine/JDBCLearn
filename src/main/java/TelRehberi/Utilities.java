package TelRehberi;

import java.sql.*;
import java.util.ArrayList;

public class Utilities {

    Connection connection = null;  //*** sql query db'ye göndermek için DB bağlantısı yapacak obje create edildi
    Statement statement = null;   //*** sql query'lerini result' unu return edecek obje crate edildi
    PreparedStatement pStatement = null; //*** parametreli statement obj create edildi-->güven ve hız avantajı

    private void dBaseConnect() {//mysql connectin-> mysql' bağlanan method...
        String url = "jdbc:mysql://localhost:3306/"; //mysql bağlantı adresi (path) atandı
        String username = "root"; //mysql bağlantı için usernsme atandı
        String password = "Adnan46.";

        try {
            connection = DriverManager.getConnection(url, username, password);// *** mysql bağlanacak obj pğarametre girerek bağlantı sağlandı
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    private void usetelefonRehberi() {
        String sql_query = "Use telefonRehberi";// *** ilgili db ye bağlanması için sql query tanımlandı

        try {
            statement = connection.createStatement(); //ilgili db için bağlantı yapıldı
        } catch (SQLException e) {
            System.out.println(e.toString());
            ;
        }
    }


    private void dBaseClose() {// db bağlantısını kapatır
        try {
            if (connection != null) {// bağlantı açık kontrolu

                connection.close();//bağlantı kapatıldı..
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

    }

    private void createDBase() {//schema ve table create eden method
        dBaseConnect();//db bağlan methodu call edildi
        try {
            String sql_query = "CREATE DATABASE if not exists telefonrehberi"; //mysql bağlantısında db ve table create sql query tanımlandı
            statement = connection.createStatement();
            statement.executeUpdate(sql_query); //şimşeğe bastık :)query çalıştırıldı
            usetelefonRehberi(); //database aktif oldu
            sql_query = "CREATE TABLE if not exists tel_nolar" + //ilgili db(telefonrehberi) table create edecek query tanımlandı
                    "(id int not null primary key auto_increment," +
                    "isim varchar(45)," +
                    "tel varchar(20));";
            statement.executeUpdate(sql_query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dBaseClose(); //güvenlik için db kapatan method call edildi

        }


    }

    public void addData(Kayit kayit) { //ilgili db deki table'a record insert eden method
        dBaseConnect();
        usetelefonRehberi();
        String sql_query = "insert into tel_nolar(isim,tel) values(?,?)"; //ilgili table column'a record insert edecek query tanımlandı
        try {
            pStatement = connection.prepareStatement(sql_query);
            pStatement.setString(1, kayit.getIsim());//table 'da isim column'a kayit objeden isim variable record olarak insert yapıldı
            pStatement.setString(2, kayit.getTel());//table 'da tel column'a kayit objeden tel variable record olarak insert yapıldı

            pStatement.executeUpdate();//kayıt ekledikten sonra şimşek yaptık

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            dBaseClose();
        }
    }


    public void updatData(Kayit kayit) {//ilgili tablodaki ilgili column'lar yeni record ile update eden meth.
        dBaseConnect();
        usetelefonRehberi();
        String sql_query = "UPDATE tel_nolar SET isim=?,tel=? WHERE id=?;";//ilgili table column'a record insert edecek query tanımlandı
        try {
            pStatement = connection.prepareStatement(sql_query);
            pStatement.setString(1, kayit.getIsim());//table'da isim column'a kayıt obj isim variable record olarak insert yapıldı
            pStatement.setString(2, kayit.getTel());//table'da tel column'a kayıt obj tel variable record olarak insert yapıldı
            pStatement.setInt(3, kayit.getId());//table'da id column'a kayıt obj id variable record olarak insert yapıldı

            pStatement.executeUpdate();// table record insert query çalıştırıldı ->şimşek

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            dBaseClose();//mysql  bağlantısı kapatıldı
        }


    }

   public void deleteData(Kayit kayit) {
        dBaseConnect();
        usetelefonRehberi();
        String sql_query = "DELETE FROM tel_nolar  WHERE id=?;";//ilgili table column'a record insert edecek query tanımlandı
        try {
            pStatement = connection.prepareStatement(sql_query);
            pStatement.setInt(1, kayit.getId());//table'da isim column'a kayıt obj isim variable record olarak insert yapıldı


            pStatement.executeUpdate();// table record insert query çalıştırıldı ->şimşek

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            dBaseClose();//mysql  bağlantısı kapatıldı
        }


    }


    private ArrayList<Kayit> listData() {// table'daki tum column record'ları return eden meth. -> SELECT
        ArrayList<Kayit> liste = new ArrayList<Kayit>();//select'tn gelen record'ları atanacagı boş liste

        dBaseConnect();// mysql connection-> mysql baglanan meth. call
        usetelefonRehberi();// mysql'de db(schema)'ye baglanan meth.(USE javacan) call...
        ResultSet resultSet = null;//*** sql query sonuçlarını return edecek obj. create edildi.
        String sql_query = "SELECT * FROM tel_nolar ;";// mysql'deki db'deki ilgili table ve column'a record listelemek (read) sql query tanımlandı
        try {
            resultSet = statement.executeQuery(sql_query);// mysql'deki db'deki ilgili table ve column'a record listelemek (read) sql query run edildi(şimşek)
            Kayit k = new Kayit();//kayıt class yeni bir obj
            while (resultSet.next()) {//iterator loop ile datalar listelendi
                k.setId(resultSet.getInt(1));//iterator ie db den gelen result recodrlar obj'de ilgili ins. variable atandı
                k.setIsim(resultSet.getString(2));//iterator ie db den gelen result recodrlar obj'de ilgili ins. variable atandı
                k.setTel(resultSet.getString(3));//iterator ie db den gelen result recodrlar obj'de ilgili ins. variable atandı
                liste.add(k);//iterator ie db den gelen result recodrlar obj liste eklendi
            }


        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            dBaseClose();//mysql  bağlantısı kapatıldı
        }


        return liste;
    }

    }

