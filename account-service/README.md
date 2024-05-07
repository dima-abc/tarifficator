### Запуск приложения

- Требования Java 17, Maven 3.9.6, git, docker, docker-compose <br>
- Клонируйте проект командой git
  clone [тестовое задание](https://github.com/dima-abc/customeraccounts.git) <br>
- При помощи docker-compose.yaml разверните контейнер баз данных postgres <br>
- Перейди в корень проекта и соберите проект командой: <br>
  <b> mvn -DskipTests package </b> <br>
- После успешной сборки проекта перейдите в каталог собранного проекта target и запустите приложение командой: <br>
  """java -jar customeraccounts-0.0.1.jar""" <br>

---

### Swagger

### [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

### Основные ссылки

#### Работа с учетной записью

GET запрос поиск учетных записей по полям <br>
Обрабатываемые параметры: <br>

lastName=string <br>
firstName=string <br>
middleName=string <br>
phone=string <br>
email=string <br>

[/api/v1/accounts](http://localhost:8081/api/v1/accounts) <br>

---

GET запрос чтение учетной записи по ID <br>
[/api/v1/accounts/{accountId}](http://localhost:8081/api/v1/accounts/{uuid}) <br>

---

POST запрос создание учетной записи <br>
[/customer-api/accounts](http://localhost:8081/api/v1/accounts/ <br>
Для валидации полей требуется указать заголовок x-Source [mail, mobile, bank, gosuslugi] <br>

{ <br>
"bankId": 0, <br>
"firstName": "string", <br>
"lastName": "string", <br>
"middleName": "string", <br>
"birthDate": "6888-08-88", <br>
"passport": "5520 920905", <br>
"placeBirth": "string", <br>
"phone": "71168867071", <br>
"email": "string", <br>
"addressRegistered": "string", <br>
"addressLife": "string" <br>
} <br>

--- 

#### Работа с платформами

* GET запрос поиск платформы по ID <br>
[/api/v1/platforms/{platformId}](http://localhost:8081/api/v1/platforms/1) <br>
* GET запрос поиска учетной записи по имени не строгий, request param p-name. <br>
[/api//v1/platforms](http://localhost:8081/api/v1/platforms?p-name=mail) <br>
* POST запрос создание платформы <br>
[/api/v1/platforms](http://localhost:8081/api/v1/platforms) <br>
{
  "platformName": "string", <br>
  "bankId": true, <br>
  "lastName": true, <br> 
  "firstName": true, <br>
  "middleName": true, <br>
  "birthDate": true, <br>
  "passport": true, <br>
  "placeBirth": true, <br>
  "phone": true, <br>
  "email": true, <br>
  "addressRegistered": true, <br>
  "addressLife": true <br>
}
* PUT запрос обновления платформы <br>
[/api/v1/platforms/{platformId}](http://localhost:8081/api/v1/platforms/1L)
{
  "platformName": "string", <br>
  "bankId": true, <br>
  "lastName": true, <br>
  "firstName": true, <br>
  "middleName": true, <br>
  "birthDate": true, <br>
  "passport": true, <br>
  "placeBirth": true, <br>
  "phone": true, <br>
  "email": true, <br>
  "addressRegistered": true, <br>
  "addressLife": true <br>
}
* DELETE запрос удаления платформы <br>
[/api/v1/platforms/{platformId}](http://localhost:8081/api/v1/platforms/1L)

---

### Задание

#### Необходимо разработать веб-сервис для работы с учетными записями клиентов со следующей функциональностью: <br>

1. Создание учетной записи <br>
2. Чтение учетной записи по id <br>
3. Поиск учетной записи по полям [фамилия, имя, отчество, телефон, емейл]. <br>
   Поиск осуществляется только при условии <br>
   указания хотя бы 1 поля. <br>

--- 

#### Поля учетной записи клиента:

- id <br>
- bank_id (идентификатор клиента в банке) <br>
- фамилия <br>
- имя <br>
- отчество <br>
- дата рождения <br>
- номер паспорта (вместе с серией в формате ХХХХ ХХХХХХ) <br>
- место рождения <br>
- телефон (в формате 7ХХХХХХХХХХ) <br>
- емейл <br>
- адрес регистрации <br>
- адрес проживания <br>

--- 

- Клиент может быть создан из разных приложений. <br>
- В зависимости от приложения отличается логика валидации полей при создании учетной записи. <br>
- Приложение определяется по обязательному для указания http-заголовку "x-Source". <br>

---

#### Список значений http-заголовка и правила валидации полей:

- mail - только имя и емейл обязательные <br>
- mobile - только номер телефона обязательный <br>
- bank - bank_id, фамилия, имя, отчество, дата рождения, номер паспорта обязательные <br>
- gosuslugi - все поля кроме емейла и адреса проживания обязательные <br>

---

#### Дополнительные требования:

- Код должен быть приспособлен для появления новых приложений со своими правилами валидации. <br>
- Основная бизнес-логика должна быть покрыта тестами. <br>
- Стек: java 11+, spring boot, hibernate, postgres, maven. <br>
- Должен быть docker-compose для подготовки окружения для локального запуска сервиса <br>
- Выполненное задание выложить на GitHub или GitLab <br>