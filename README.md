# turkuazgrafik_app
> Günlük korona tablosu andorid uygulaması.



Türkiye Cumhuriyeti Sağlık Bakanlığı tarafından yayınlanan korona hasta tablosu yayınlandığında bildirim gönderen ve son yayımlanan hasta tablosunu tablosunu gösteren ugulamadır. 

![](covidapp_1.png) ![](covidapp_2.png) ![](covidapp_3.png) ![](covidapp_4.png) ![](covidapp_5.png)

## Kurulum

Uygulamayı indirip andoid studio ile açabilirsiniz.
```sh
git clone https://github.com/icetin-yu/turkuazgrafik_app.git
```
Ya da app-debug.apk dosyasını telefonunuza atarak test edebilirsiniz.


## Uygulana kullanımı

turkuazgrafik_app 3 ana pencereden oluşmaktadır. Bugün, Bu hafta, Toplam olacak şekilde resimlere gösterildiği gibidir. Sayfalar arasında sürüklüyerek geçiş yapabilirsiniz.
turkuazgrafik_app sizin için, linux tabanlı bir server tarafından tablo yayınlandığı gibi size bildirim gönderecektir.
Push notification almak için Huawei Mobil Service kurulu olmalıdır.

##
Turkuaz grafik diğer:

Twitter - [@trturkuazgrafik](https://twitter.com/trturkuazgrafik) – Telegram https://t.me/turkuazgrafik


## Diğer teknolojiler
Uygulamada Hms push kit ve Hms analytics kit kullandım. 
Cihazın push kit için ürettiği tokenleri, pythoneverywhere.com'da python django ile yazdığım bir rest api'ye göndermekteyim.
Django uygulaması sqlite ile bu tokenleri unique olarak kaydetmektedir ve zamanı gelince push notificationları Huawei'ye göndermektedir. Huawei'den dönen mesajlar cihazlara ulaşmaktadır.
Fakat django uygulaması tokenlerı alma, kaydetme ve push mesajı isteği atma gibi işlemleri yapsa dahi yeni verileri kontrol etme işini yapmamaktadadır. Bunun sebebi serverın kısıtlı imkalarını aşmamak içindir.
Django uygulamasına mesajın gönderilme metodunu tetikleten başka bir python scriptidir. Bu script heroku.com da çalışmaktadır. Saat 18:30 dan sonra python scripti aktif olup her 5 dk'da bir yeni veri kontrolü yapmaktadır. 
Yeni veri tespitinde django sunucusunun tetikletici komutu çalıştırılıp push mesaj cihazlara ulaştırılmaktadır.  

## Benim her soruma usanmadan cevap vererek yardımcı olmaya çalışan [bkayranci](https://github.com/bkayranci)'a teşekkürü borç bilirim.



## Release History

* 0.1.0
    * Work in progress
    * Add: mainactivity, fragments, layouts
* 0.0.1
    * Work in progress

## Meta

İsrafil ÇETİN – Twitter [@icetin_](https://twitter.com/icetin_) – icetin93@gmail.com.com


[https://github.com/israfil-cetin/](https://github.com/israfil-cetin/)

## Contributing

1. Fork it (<https://github.com/icetin-yu/turkuazgrafik_app/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

