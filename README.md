# BookShelter API
***
### API orientada a la gestión de una tienda de libros

Esta rama del proyecto contiene el mismo fichero Dockerfile que la rama original (basada en H2).

Además, contiene un script que levanta automáticamente la base de datos del proyecto en un contenedor
de Docker de MySql (en este caso en el puerto 3309), modificando la contraseña root usando este comando :

```
docker run -d -p 3309:3306 --name bookshelter-db -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=bsapi -e MYSQL_USER=bsuser -e MYSQL_PASSWORD=100316 mysql
```
La imagen creada a raíz del Dockerfile fué subida al repositorio de dockerhub 
[**alanz-repo**](https://hub.docker.com/repository/registry-1.docker.io/drojanx/alanz-repo/tags?page=1&ordering=last_updated)
tagueada como "alanzbookshelterapi-mysqldb"

Por último, al ejecutar el fichero docker-compose se levantarán conjuntamente 2 contenedores docker:
- Uno creado con la propia imagen del proyecto. Se especifica dentro del propio docker-compose que, si la imagen
  no se encuentra, la generará a partir de este mismo proyecto. 
  - Para crear la imágen sin usar docker-compose:
    ```
    docker build --tag=alanz/bookshelterapi:1.0 .
    ```
- Otro creado con una imágen de MySql que hará de base de datos del anterior contenedor. Creará un volúmen en
  la misma carpeta en la que se ejecute el docker-compose para mantener los datos guardados aunque se ejecute
  un "docker-compose down". (Es posible que haya que hacer un docker-compose up inicial para generar la carpeta del
  volumen, hacer un docker-compose down y volver a levantarlo para que funcione correctamente)

En la carpeta  [**Postman Collection**](https://github.com/Drojanx/bookshelterapi/tree/develop/postman_collection) se
encuentra una colección de Postman para probar todas las operaciones CRUD del proyecto. **Apuntan a la 
[URL](https://bookshelterapi.azurewebsites.net) en la que se encuentra desplegada la apliación (en Azure, con 
base de datos en memoria H2), no al puerto en el que se abriría si lo ejecutas en local.**

En la carpeta [**Resources**](https://github.com/Drojanx/bookshelterapi/tree/develop/src/main/resources) se encuentra
también un fichero Openapi 3.0 del proyecto.