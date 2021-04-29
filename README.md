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


# Düzeltilmesi Gereken Hatalar ve Geliştirilebilecek Yerler

1.Arayüzler Absolute Layout ile oluşturuldu. Kullanılan sisteme göre arayüz, daha küçük veya daha büyük gözükebiliyor.

2.Normalde sistem aynı kullanıcı adına sahip öğrenci veya personel hesabı açmaya izin vermiyor. Fakat kullanıcı adınızı değiştirirken 
olan bir kullanıcı adını alabiliyorsunuz. src'den Öğrenci Ekleme veya Personel Ekleme ile ilgili sayfalardan ...kadiKontrol fonksiyonlarını
ve kullanıldığı yerleri inceleyebilirsiniz. 

3.Editlenebilen Tablolar. Bu öğrenci tarafında sıkıntı yaratmıyor. Fakat personel veya yöneticinin, kitap veya öğrenci tablolarını editleyip o şekilde
kitap ödüncü veya teslimi yapmasına izin verebiliyor. Örneğin kitap adetini tablodan editleyip ödünç verdiğinde kitap adetini azaltmadan ödünç verme
işlemini gerçekleştirebilir. isCellEditible Override edilerek sorun çözülebiliyormuş. 

# Neler Daha İyi?

1.Önceki projem Otel Teta'ya göre daha **temiz** ve standartlara uygun bir kod yazmayı başardım. "jtxt_oda_no"(Otel Teta) VS. "jtxtKitapAd"(Sistem Kütüphane 2)  

2.Birçok durumu hesaba katarak programdaki açık uçları en aza indirgemeye çalıştım. Kitap adedi kısmına sadece sayı girilebilmesi, tarih kısmı boş kaldığında
uyarı vermesi gibi. 

3.İlk versiyona göre daha çok seçenekler ve daha rahat kullanım. 

4.Daha çok fonksiyon ile bir tık daha optimize olmuş bir sistem. filtre(), jtxtTemizle() gibi.  
