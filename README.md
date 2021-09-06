Инструкция по запуску RichOrBrokeCurrencyHistoryApp

Приведенная инструкция по запуску приложения RichOrBrokeCurrencyHistoryApp базируется на шагах, необходимых к выполнению в среде операционной системы Windows 10. Шаги для систем семейства Linux/MacOS будут идентичными с небольшими различиями в запуске команд в терминале.
1.	Загрузите и установите Apache Tomcat Server с официального сайта https://tomcat.apache.org/
Разработка и тестирование приложения RichOrBrokeCurrencyHistoryApp проведены на сервере версии 9.0.
2.	Загрузите и установите IntelliJ IDEA IDE (Community или Ultimate) версию с официального сайта https://www.jetbrains.com/idea/download/. Настройте Вашу IDE для работу с Github репозиторием.
3.	Импортируйте проект с приложением RichOrBrokeCurrencyHistoryApp по ссылке Github:
https://github.com/gtuignatov/RichOrBrokeCurrencyHistory.git
Для сборки проекта используется Gradle.
4.	После импорта проекта откройте файл build.gradle для загрузки всех необходимых компонентов приложения.
5.	Запустите приложение RichOrBrokeCurrencyHistoryApp из файла src\main\java\com\beginwithsoftware\richorbrokecurrencyhistory\RichOrBrokeCurrencyHistoryApplication.java (через CTRL+Shift+F10)
6.	После создания Spring’ом контекста приложения, запуска Apache Tomcat сервера в окне лога запуска приложения (Run) будет выведено сообщение об успешном запуске:
Started RichOrBrokeCurrencyHistoryApplication in NN.DD seconds (JVM running for N2.TTT), где NN.DD и N2.TTT – время запуска в секундах.
7.	Приложение настроено на работу на порту 8080 по адресу: http://localhost:8080/getNNN
где getNNN – адреса маппингов для различных задач микросервиса. Перечень поддерживаемых задач:
•	http://localhost:8080/getLatest - получение текущего курса валют по отношению к USD в JSON формате с REST API сервиса https://openexchangerates.org/api
Бесплатный режим использования REST API сервиса https://openexchangerates.org/api возвращает курсы валют по отношению к USD, этот факт учтен в работе приложения.

•	http://localhost:8080/getYesterday - получение вчерашнего курса валют по отношению к USD в JSON формате с REST API сервиса https://openexchangerates.org/api

•	http://localhost:8080/getGiphy - получение JSON файла с REST API сервиса https://api.giphy.com/v1/gifs по запросу rich, с количеством объектов images, определенных в файле src\main\resources\application.properties

•	http://localhost:8080/getRandomGiphy – получение JSON файла со случайно выбранной картинкой. В данном JSON файле доступны URL картинок/видео через объекты url, mp4, webp. Запрос по умолчанию – rich. Поддерживается работа с любым параметром запроса через searchParam: http://localhost:8080/getRandomGiphy?searchParam=broke

•	http://localhost:8080/compareCurrency - основной маппинг REST API микросервиса, возвращает JSON с рандомной картинкой, в зависимости от соотношения к рублю сегодняшнего и вчерашнего курса валют.

8.	Перечень настроек для микросервиса, доступных к изменению:

server.port=8080 – адрес порта для работы микросервиса

openexchangerates.app-id=Ваш AppID от REST API сервиса https://openexchangerates.org/api

openexchangerates.currency-foreign=UAH – трехсимвольный код иностранной валюты, изменение которой мы отслеживаем в микросервисе. Может задаваться из списка поддерживаемых валют.

openexchangerates.currency-base=RUB – трехсимвольный код базовой валюты, по отношению которой мы следим за изменением иностранной валюты в микросервисе. Может задаваться из списка поддерживаемых валют.
Важно: Бесплатный режим использования REST API сервиса https://openexchangerates.org/api возвращает курсы валют по отношению к USD, поэтому изменение соотношений курсов валют сегодня/вчера производится через значения в объектах rates, возвращаемых в JSON.

giphy.app-id=Ваш AppID от REST API сервиса https://api.giphy.com/v1/gifs

giphy.search-limit=25 – количество картинок/видео, возвращаемых с REST API https://api.giphy.com/v1/gifs для работы микросервиса

giphy.dto-json-property-value=original – имя вложенного объекта в объекте images JSON файла, получаемого с REST API https://api.giphy.com/v1/gifs, определяющее разрешение/формат/качество картинки/видео. Разработчик должен протестировать различные вложенные объекты в возвращаемом JSON файле, так как для различных объектов типа original, downsized, downsized_large, и т.д. поддерживается/отсутствуют комбинации объектов url, mp4, webp.

9.	Для тестирования микросервиса написаны тесты для классов в пакете
src\test\java\com\beginwithsoftware\richorbrokecurrencyhistory\:

controller\CurrencyControllerTest.java
controller\GiphyControllerTest.java
helperdto\DtoMethodsTest.java
Для мока внешних сервисов в тесте применена аннотация @MockBean

10.	Сборка и запуск Docker контейнера:
Необходимо установить и настроить работу Docker в Вашей ОС. Далее приводится порядок действия для Windows 10 на примере *.bat файлов, команды в Linux/MacOS системах идентичные. Все необходимые bat файлы выложены в репозитории проекта на Github по ссылке https://github.com/gtuignatov/RichOrBrokeCurrencyHistory.git

•	Сборка и компиляция приложения с помощью gradlew (compile_app_gradle.bat):

gradlew build && java -jar build/libs/rich-broke-spring-boot-docker-0.1.0.jar

•	Контейнеризация и создание Docker образа для Gradle (docker_containerize_it_gradle.bat):

docker build --build-arg JAR_FILE=build/libs/*.jar -t springio/rich-broke-spring-boot-docker .

•	Проверка наличия образа в списке Docker (docker_images_list.bat):

docker image ls

•	Запуск процесса в изолированном Docker контейнере: (docker_run.bat)

docker run springio/rich-broke-spring-boot-docker
