### Запуск приложения

- Требования Java 21, Maven 3.9.6, git, docker, docker-compose <br>
- Клонируйте проект командой git clone <br>
- [тестовое задание 2](https://github.com/dima-abc/tarifficator.git) <br>
- - Перейди в корень проекта и соберите проект командой: <br>
    <b> mvn -DskipTests package </b> <br>
- При помощи docker-compose.yaml разверните контейнер всех приложений <br>
    <b> docker-compose up -d </b> <br>

# Перед началом работы зарегистрируйте пользователя и получите token доступа
## Сервис аутентификации

### Swagger

### [http://localhost:8086/swagger-ui/index.html](http://localhost:8086/swagger-ui/index.html)

### Основные ссылки

#### Работа с аутентификацией

создать пользователя POST [/users/signup](http://localhost:8086//users/signup) <br>
- body Json <br>
  {   <br>
  "username":"User Name", <br>
  "password":"Password", <br>
  "firstname":"User", <br>
  "lastname":"Name", <br>
  "email":"userName@mail.ru" <br>
  } <br>
- body request <br>
  { <br>
  "username": "user2", <br>
  "email": "user2@test.ru", <br>
  "firstname": "John", <br>
  "lastname": "B", <br>
  "password": "password", <br>
  "statusCode": 201, <br>
  "statusMessage": "Created" <br>
  }  <br>

получить токен доступа POST [/users/login](http://localhost:8086//users/login) <br>
- body Json <br>
  { <br>
  "username":"User name", <br>
  "password":"password" <br>
  } <br>
- body request <br>
  { <br>
  "accessToken": "token", <br>
  "refreshToken": "refresh_token", <br>
  "scope": "scopes_access", <br>
  "expiresIn": 300 <br>
  } <br>

#### Полученный токен доступа необходима добавить в Authorization Bearer + accessToken

---

### Swagger

### [http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html)

### Основные ссылки

#### Работа с тарифами

создать POST [/api/v1/client/tariffs](http://localhost:8084/api/v1/client/tariffs) <br>
удалить DELETE [/api/v1/client/tariffs/{tariffId}](http://localhost:8084/api/v1/client/tariffs/{tariffId}) <br>
обновить PATCH [/api/v1/client/tariffs/{tariffId}](http://localhost:8084/api/v1/client/tariffs/{tariffId}) <br>

#### Работа с версиями продукта

откатить на предыдущую POST [/api/v1/client/products/version/{uuid}/revert](http://localhost:8084/api/v1/client/products/version/{uuid}/revert) <br>
получить предыдущие версии GET [/api/v1/client/products/version/{uuid}/previous](http://localhost:8084/api/v1/client/products/version/{uuid}/previous) <br>
получить версии за период GET requestParam start-period=yyyy-mm-dd HH:mm:ss&&end-period=yyyy-mm-dd HH:mm:ss <br>
[/api/v1/client/products/version/{uuid}/previous](http://localhost:8084/api/v1/client/products/version/{uuid}/previous) <br>

получить актуальную версию продукта GET [/api/v1/client/products/version/{uuid}/actual](http://localhost:8084/api/v1/client/products/version/{uuid}/actual) <br>

#### Работа с продуктом

создать продукт POST [/api/v1/client/products/](http://localhost:8084/api/v1/client/products/)
удалить проудкт DELETE [/api/v1/client/products/{productId}](http://localhost:8084/api/v1/client/products/{productId})

#### Работа с учетными записями

получить все GET [/api/v1/client/accounts](http://localhost:8084/api/v1/client/accounts)
создать POST [/api/v1/client/accounts](http://localhost:8084/api/v1/client/accounts)
посик по ID GET [/api/v1/client/accounts/{id}](http://localhost:8084/api/v1/client/accounts/{id})

