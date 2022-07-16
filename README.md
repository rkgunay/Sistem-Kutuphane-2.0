# Sistem-Kutuphane-2.0
Kütüphane Kitap Takip Uygulaması

# Proje Tanımı

Sistem Kütüphane uygulamasınının yeni halidir. Programı baştan sona yeniden yazdım. Yeni ve daha interaktif arayüz tasarımları,
farklı kütüphanelerin sisteme kayıt olabilmesi, her kütüphanenin kendisine farklı personeller tanımlayabilmesi, 
öğrencilerin istedikleri kütüphaneye üye olabilmesi, kitap ödünç verildiğinde otomatik olarak kitabın adetinin düşmesi 
ve teslim alındığında artması, talep edilen kitapları kütüphaneye yeni kitap olarak ekleyebilmesi, 
sistemin yeni özelliklerindendir.


# Geliştirme Ortamı ve Kullanılan Teknolojiler

Veritabanı için MySQL dili kullanılmıştır. Programın kodlanması Java dili ile yapılmıştır. 
Geliştirme ortamı olarak Eclipse kullanılmıştır. 
Program için JCalendar, MySQL Driver kütüphaneleri projeye dahil edilmiştir. 
Localhost'ta çalışır. Veritabanı dosyasını projeye ekledim.

# Kurulum

Xampp kurulmalı. PhpMyAdmin'e giriş yapıldıktan sonra sistem_kutuphane adında veritabanı oluşturulmalıdır.
Sonra bu veritabanına proje dosyasında verdiğim **sistem_kutuphane.sql** dosyası içe aktarılmalıdır.. Program 3 farklı uygulamadan açılır. 
Yönetici, Personel ve Öğrenci için farklı giriş uygulaması vardır. Bu dosyaları dist klasöründe bulabilirsiniz.
Admin uygulamasından "**admin**" kullanıcı adı ve "**admin**" şifresiyle giriş yapabilirsiniz. 
.jar uzantılı açılmıyorsa .bat uzantılı dosyadan deneyebilirsiniz.  
Veritabanı bağlantısında sorun yaşıyorsanız benim kullandığım bağlantı bilgileriyle 
sizin localhost'unuzdaki bağlantı bilgileri farklı olabilir. 
Veritabanı sınıfındaki bağlantı bilgilerinizi kendi bilgilerinizle eşleştirmeniz gerekebilir. 


# Genel Arayüz Tasarımı

Yönetici giriş yaptıktan sonra bu sayfaya yönlendirilir.  <br/> 
<img src="https://github.com/rkgunay/Sistem-Kutuphane-2.0/blob/main/ekranGoruntuleri/yoneticiAnaSayfa.png" width="600">  <br/> <br/>


Personel giriş yaptıktan sonra bu sayfaya yönlendirilir.  <br/> 
<img src="https://github.com/rkgunay/Sistem-Kutuphane-2.0/blob/main/ekranGoruntuleri/personelAnaSayfa.png" width="600">  <br/> <br/>


Öğrenci giriş yaptıktan sonra bu sayfaya yönlendirilir.  <br/> 
<img src="https://github.com/rkgunay/Sistem-Kutuphane-2.0/blob/main/ekranGoruntuleri/ogrenciAnaSayfa.png" width="600">  <br/> <br/>


