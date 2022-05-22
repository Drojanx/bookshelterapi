# BookShelter API
***
### API orientada a la gestión de una tienda de libros

Contiene un fichero .bat que, al ejecutarse, dockeriza el proyecto y lo lanza en un contenedor docker abriendo 
el puerto 2022. En esta versión el proyecto se ejecuta con una Base de Datos en memoria (H2). Estando en el directorio
del proyecto, ejecutar: `.\scprit-run-api.bat
`

En la carpeta  [**Postman Collection**](https://github.com/Drojanx/bookshelterapi/tree/develop/postman_collection) se
encuentra una colección de Postman para probar todas las operaciones CRUD del proyecto. **Apuntan a la 
[URL](https://bookshelterapi.azurewebsites.net) en la que se 
encuentra desplegada la apliación (en Azure), no al puerto en el que se abriría si lo ejecutas en local.**

En la carpeta [**Resources**](https://github.com/Drojanx/bookshelterapi/tree/develop/src/main/resources) se encuentra 
también un fichero Openapi 3.0 del proyecto.

Por otro lado, la imagen de este proyecto ( tagueada como "alanzbsapi" ) se subió a mi repositorio en Docker Hub público:
[**alanz-repo**](https://hub.docker.com/repository/docker/drojanx/alanz-repo)

