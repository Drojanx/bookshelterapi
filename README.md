# BookShelter API
***
### API orientada a la gestión de una tienda de libros

Esta rama del proyecto contiene el mismo fichero Dockerfile que la rama original (basada en H2).

Además, contiene un script que levanta automáticamente la base de datos del proyecto en un contenedor
de Docker de MySql

Por último, al ejecutar el fichero docker-compose se levantarán conjuntamente 2 contenedores docker:
- Uno creado con la propia imagen del proyecto
- Otro creado con una imagen de MySql que hara de base de datos del anterior contenedor. Creará un volumen en
  la misma carpeta en la que se ejecute el docker-compose para mantener los datos guardados aunque se ejecute
  un "docker-compose down"