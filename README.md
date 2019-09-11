# Java JDBC ile Veritabanı Kullanımı

JDBC, JavaDatabaseConnectivity (Java Veritabanı Bağlantısı) anlamına gelmektedir. JDBC, herhangi bir veritabanına bağlanarak SQL komutları ile verilere erişebildiğimiz bir yapıdır. JDBC kütüphanesi, her görev için genellikle veritabanı kullanımı ile ilişkili API’leri içerir.

* **JDBC Mimarisi**

**JDBC API:** Uygulama ve veritabanı arasında bağlantı sağlar.

**JDBC Driver API:** Uygulama ve kullanılan veritabanı sürücü bağlantısını destekler.

![jdbc](https://github.com/mertkolgu/veritabani-kullanimi/blob/master/image/jdbc.jpg)

* **JDBC Bileşenleri**

**DriverManager:** Bu sınıf, veritabanı sürücülerinin listesini yönetir.

**Driver:** Bu interface, veritabanı ile iletişimi ele alır.

**Connection:** Bu interface, bütün metotları ile veritabanına irtibat kurmak için kullanılır.

**Statement:** SQL ifadelerini veritabanına göndermek için bu interface’ten oluşturulan nesneler kullanılır.

**ResultSet:** Statements nesnelerini kullanarak SQL sorgusunu çalıştırdıktan sonra veritabanından alınan verileri tutmak için bu nesneler kullanılır. Onu taşımanıza izin veren bir yineleyici görevi görür.

**SQLException:** Bu sınıf, bir veritabanı uygulamasında ortaya çıkan hataları ele alır.
