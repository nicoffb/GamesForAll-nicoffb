# Flallery

Flallery es una red social de galería de arte que permite a los usuarios compartir y descubrir obras de arte. La aplicación se ha desarrollado en dos partes: el backend y el frontend, utilizando diversas tecnologías para garantizar su correcto funcionamiento.


## Tecnologías utilizadas

Backend: El backend de Flallery se ha desarrollado utilizando Spring, un framework de Java, y empleando PostgreSQL como base de datos en producción y H2 como base de datos en desarrollo. Además, en producción se ha utilizado Docker para garantizar que el despliegue de la base de datos de PostgreSQL sea lo más sencillo posible.

Frontend: El frontend de Flallery se ha desarrollado utilizando Dart y Flutter utilizando la arquitectura BloC, un framework de desarrollo de aplicaciones móviles multiplataforma. De esta forma, se ha conseguido un desarrollo ágil y sencillo, permitiendo que la aplicación pueda ser ejecutada en distintos sistemas operativos.


### Versiones y Arranque

    • Flutter version 3.8.0-14.0.pre.10 CHANNEL MASTER
    • Dart version 3.0.0 (build 3.0.0-259.0.dev) 
    • DevTools version 2.21.1

    Para iniciar el proyecto es necesario realizar los siguientes pasos:
    1-  Abrir el terminal y dirigirse a la carpeta FlalleryAPI donde tendremos nuestra API,
        ejecutar el comando "docker-compose up" para levantar la base de datos y el cliente PostgreSQL 
        en docker (es necesario que tengas instalado docker desktop y esté arrancado).
    2-  Abrir la misma carpeta con nuestro IDE (recomendable IntelliJ para un mejor funcionamiento) y arrancar el proyecto.
    3-  Abrir la carpeta flallery_frontend con Visual Studio Code y realizar los siguientes comandos
        - flutter pub get
        - flutter run -d windows
    
    Si todo va bien debemos tener nuestro proyecto funcionando correctamente.

Usuarios:
- ADMIN --> Username: mcampos // Password: Salesianos123_
- USER --> Username: lmlopez // Password: Salesianos123_

Pruebas
- Swagger / Documentación de endpoints: http://localhost:8080/swagger-ui/index.html#/
- Colección de Postman agregada.


## Apuntes

-   Queda implementar muchas cosas en flutter por motivos de tiempo no me ha sido posible, 
        trataré de realizarlo las próximas semanas:
    -   Crear, comentar, likear y borrar Artworks, por motivos de regla de negocio no permito
            editarlos una vez subidos como ocurre en cualquier red social como tiktok o instagram.

-   Por algún motivo cuando realizas un registro de usuario en frontend y luego realizas el login, 
        la página se queda cargando, pulsa shift+R en el terminal para entrar correctamente.