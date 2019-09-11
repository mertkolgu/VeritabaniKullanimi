/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Mert
 */
public class Baglanti {

    // veritabanı bilgileri
    private String userName = "root";
    private String password = "";
    private String dbName = "java";
    private String host = "localhost";
    private int port = 3306;
    private Connection con = null;

    // SQL sorgularını çalıştırmak için gereken class
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    Scanner sc = new Scanner(System.in);

    public Baglanti() {

        // jdbc:mysql://localhost:3306/java?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName
                + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver bulunamadı.");
        }

        try {
            con = DriverManager.getConnection(url, userName, password);
            System.out.println("Veritabanına bağlandı.");
        } catch (SQLException e) {
            System.out.println("Veritabanına bağlanılamadı.");
        }
    }

    public void commitVeRollback() {
        try {
            // javaya kendi işlemlerimi kendim yapacağım diyorum
            con.setAutoCommit(false);

            String sorgu1 = "DELETE FROM calisanlar WHERE id = 3";
            String sorgu2 = "UPDATE calisanlar set mail = 'mertkolgu@outlook.com' WHERE id = 1";

            System.out.println("Güncellenmeden Önce");
            calisanlariGetir();

            statement = con.createStatement();
            statement.executeUpdate(sorgu1);
            statement.executeUpdate(sorgu2);

            System.out.print("İşlemleriniz kaydedilsin mi? (evet/hayır) : ");
            String cevap = sc.nextLine();

            if (cevap.equalsIgnoreCase("evet")) {
                con.commit();   // yapılan işlemler veritabanına kaydediliyor
                calisanlariGetir();
                System.out.println("Veritabanı güncellendi.");
            } else {
                con.rollback();
                System.out.println("Veritabanı güncellemesi iptal edildi.");
                calisanlariGetir();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void preparedCalisanlariGetir(int id) {
        // istediğimiz id'deki kullanıcıyı getiriyoruz
        String sorgu = "SELECT * FROM calisanlar WHERE id > ? AND ad LIKE ?";

        try {
            preparedStatement = con.prepareStatement(sorgu);
            // birinci soru işaretinin yerindeki değer id'ye denk geliyor
            preparedStatement.setInt(1, id);
            // ikinci soru işaretinin yerindeki değer ad'a denk geliyor
            preparedStatement.setString(2, "M%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String mail = rs.getString("mail");

                System.out.println("Ad : " + ad + " Soyad : " + soyad + " Mail : " + mail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void calisanlariGetir() {
        // SELECT * FROM calisanlar WHERE id > 2 --> id'si 2'den büyük olanları al
        String sorgu = "SELECT * FROM calisanlar";

        // adı M harfi ile başlayanları alıyoruz
        // String sorgu = "SELECT * FROM calisanlar WHERE ad LIKE 'M%'";
        try {
            // tablonun içinde gezinme
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);

            // okunacak veri var mı?
            while (rs.next()) {
                int id = rs.getInt("id");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String mail = rs.getString("mail");

                System.out.println("ID : " + id + " Ad : " + ad + " Soyad : " + soyad + " Mail : " + mail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void calisanEkle() {
        try {
            statement = con.createStatement();
            String ad = "emre";
            String soyad = "küçük";
            String mail = "emrekucuk@gmail.com";

            // INSERT INTO `calisanlar`(`id`, `ad`, `soyad`, `mail`) VALUES (value-1, value-2, value-3, value-4)
            String sorgu = "INSERT INTO calisanlar(ad, soyad, mail) VALUES (" + "'" + ad + "'," + "'" + soyad + "'," + "'" + mail + "')";
            statement.executeUpdate(sorgu);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void calisanGuncelle() {
        try {
            statement = con.createStatement();

            // UPDATE `calisanlar` SET `id` = value-1, `ad` = value-2, `soyad` = value-3, `mail` = value-4 WHERE `id` = 1
            // id'si 1 olan kişinin mail adresini değiştirme
            // String sorgu = "UPDATE calisanlar SET mail = 'mertkolgu08@gmail.com' WHERE id = 1";
            // id'si 3'ten büyük olanları güncelle
            String sorgu = "UPDATE calisanlar SET mail = 'example@gmail.com' WHERE id > 3";
            statement.executeUpdate(sorgu);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void calisanSil() {
        try {
            statement = con.createStatement();

            // DELETE FROM `calisanlar`
            // id'si 3'ten büyük olanları sil
            String sorgu = "DELETE FROM calisanlar WHERE id > 3";
            // kaç tane veri silindiğini gösterir
            int deger = statement.executeUpdate(sorgu);
            System.out.println(deger + " tane veri etkilendi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Baglanti baglanti = new Baglanti();
        // System.out.println("Eklenmeden Önce");
        // System.out.println("Güncellenmeden Önce");
        // System.out.println("Silinmeden Önce");
        // baglanti.calisanlariGetir();
        // baglanti.calisanEkle();
        // System.out.println("Eklendikten Sonra");
        // System.out.println("Güncellendikten Sonra");
        // System.out.println("Silindikten Sonra");
        // System.out.println("****************************************************");
        // baglanti.calisanGuncelle();
        // baglanti.calisanSil();
        // baglanti.calisanlariGetir();
        // baglanti.preparedCalisanlariGetir(0);
        baglanti.commitVeRollback();
    }
}

// Database Transaction
// çoğu zaman programlarımızda bir çok veritabanı işlemini ard arda yaparız
// örneğin elimizde birbiriyle bağlantılı 3 tane tablo güncelleme işlemimiz var(delete, update)
// bu işlemleri ve sorguları ard arda çalıştırdığımızı düşünelim;
// statement.executeUpdate(sorgu1);
// statement.executeUpdate(sorgu2); // burada bir hata oldu ve programımız sona erdi
// statement.executeUpdate(sorgu3);
// böyle bir durumda 2. sorgumuzda herhangi bir hata oluyor
// ancak 1. sorguda hata olmadığı için bu sorgumuz veritabanımızı güncelledi
// ancak bu sorgular birbiriyle bağlantılı ise 1. sorgunun da çalışmaması gerekiyor
// işte biz böyle durumların önüne geçmek için Transaction'ları kullanıyoruz
// ATM Örneği
// Transaction mantığını kullanmak için bu sorguların sadece hiçbir sorun oluşturmadığında toplu çalışmasını istiyoruz
// Java bu sorguları yazdığımız andan itibaren otomatik olarak sorguları sorgusuz sualsiz çalıştırır
// onun için ilk olarak con.setAutoCommit(false) şeklinde bir şey yaparak bu durumu engelliyoruz
// commit(); -> bütün sorguları çalıştırır. sorun olmadığı zaman hepsini çalıştırmak için kullanıyoruz
// rollback (); -> bütün sorguları iptal et. sorun olduğu zaman hiçbirini çalıştırmamak için kullanıyoruz
// yani bu sefer programlarımızı biraz daha güvenli yazmış oluyoruz
// NOT : setAutoCommit(false) yazsak bile veritabanını güncellemeyen bir sorgumuz varsa herhangi bir
// güvenlik sıkıntısı olmayacağından çalıştırılır
