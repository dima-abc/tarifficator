## Сервис продукты

### Swagger

### [http://localhost:8083/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html)

### Основные ссылки

#### Работа с продуктами

получить все GET [/api/v1/products](http://localhost:8083/api/v1/products) <br>
создать POST [/api/v1/products](http://localhost:8083/api/v1/products) <br>
поиск по ID GET [/api/v1/products/{uuid}](http://localhost:8083/api/v1/products/{uuid}) <br>
удалить DELETE [/api/v1/products/{uuid}](http://localhost:8083/api/v1/products/{uuid}) <br>
обновить PATCH [/api/v1/products/{uuid}](http://localhost:8083/api/v1/products/{uuid}) <br>

#### Работа с версиями продукта

откатить на предыдущую POST <br>
[/api/v1/products/version/{uuid}/revert](http://localhost:8083/api/v1/products/version/{uuid}/revert) <br>
получить предыдущие версии GET <br> 
[/api/v1/products/version/{uuid}/previous](http://localhost:8083/api/v1/products/version/{uuid}/previous) <br>
получить версии за период GET requestParam start-period=yyyy-mm-dd HH:mm:ss&&end-period=yyyy-mm-dd HH:mm:ss <br>
[/api/v1/products/version/{uuid}/period](http://localhost:8083/api/v1/products/version/{uuid}/period) <br>

получить актуальную версию продукта GET <br> 
[/api/v1/products/version/{uuid}/period](http://localhost:8083/api/v1/products/version/{uuid}/period) <br>