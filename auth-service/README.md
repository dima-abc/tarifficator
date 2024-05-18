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