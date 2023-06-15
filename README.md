# GAMES FOR ALL
En este repositorio podeís encontrar el desarollo de una aplicación llamada Games For All, cuyo propósito es crear una plataforma de compra/venta de videojuegos de segunda mano.


## BACKEND
La API se ha programada con Spring Boot y JAVA 
El perfil que he dejado activo es el de producción. Para arrancar la base de datos levantaremos un contenedor de docker con ProstgreSQL  mediante el comando -docker compose up (para ello deberemos tener Docker Desktop instalado en nuestro ordenador).
También podemos usar el perfil predeterminado o de desarollo si queremos arrancar la aplicación directamente con base de datos en H2 en la aplicación, configurando el arranque de Maven con -spring boot: run

Dentro de la carpeta del Backend podemos encontrar los diseños de StarUML de las diferentes entidades, así como el archivo de POSTMAN con todas las peticiones.



## FRONTEND CON FLUTTER
Esta parte de la aplicación se ha desarollado con Flutter y Dart y está enfocada para su visualización en dispositivos móviles. Por lo tanto está preparada específicamente para su visualización en el emulador de Android Studio. 
Su utilización se dará mayormente por los clientes o usuarios, que serán los interesador en ver los productos disponibles, subir sus productos o chatear con otros usuarios.

Una vez abierto con Visual Studio Code y encendido nuestro backend , pulsaremos Control +Shift + P para desplegar los modos de arranque posibles.

En nuestro caso nos hemos basado en el modelo Pixel_3a. Puedes encontrar la apk en flutter-apk.7z en el directorio del proyecto de Flutter.

Para arrancar la aplicación existen dos formas:

1- Usar un dispositivo del emulador, el cual usará la dirección : http://10.0.2.2:8080 en sus peticiones (esta es la dirección que he dejado ya que está pensada para ello). 
2- Usar flutter run -d windows para obtener otra visualización desde el emulador de Windows. Este usara la dirección http://localhost:8080 que he dejado comentada.

Ruta del prototipado con Figma: https://www.figma.com/file/WYazfLqXiPnEPlBDWfmveF/GAMESFORALLMOBILE?type=design&t=SgrGR35RoIzSiXoV-1

## FRONTEND CON ANGULAR
Esta parte de la aplicación se ha desarollado con Angular y Typescript y está enfocada para su visualización en ordenadores. Su utilización se dará por parte del administrador, que es el que tendrá permisos para gestionar las diferentes 
categorías, plataformas, usuarios, etc.
Una vez abierto con Visual Studio Code usaremos el comando y encendido nuestro backend, introduciremos el comando -npm i (para ello necesitaremos el Node Modules). 
Una vez instalado haremos "ng serve -o" para ver el front en una pestaña del navegador.
Desde ahí podremos acceder al panel de control de la aplicación y solo realizar los cambios si el usuario logado tiene perfil de Admiministrador.



### DATOS DE ADMIN Y USUARIOS

Perfil de Administrador : user - 1234
Perfil de Usuario: user2 - password2

## URL base

- http://localhost:8080

## ENDPOINTS

Todos los endpoints disponibles en la API. Para consultar toda la información al detalle , diríjase a la documentación en http://localhost:8080/swagger-ui/index.html

### Productos

```
GET /product/search -> Se obtienen todos los productos.

GET /product/{id} -> Se obtienen los detalles de un producto.

GET /myproducts -> Se obtienen todos los productos del usuario registrado.

POST /product -> Se crea un producto.

PUT /product/{id} -> Se edita un producto.

DELETE /product/{id} -> Se elimina un producto.

```

### Users

```

GET /auth/users -> Se obtienen todos los usuarios.

GET /me -> Se obtienen los datos del usuario logado.

POST /auth/login -> Se logea un usuario.

POST /auth/register -> Se resgistra un usuario.

POST /auth/register/owner -> Se resgistra un usuario como propietario.

POST /auth/register/admin -> Se resgistra un usuario como administrador.

POST /refreshtoken -> Se refresca el token.

PUT /user/changePassword -> Se cambia la contraseña del usuario logado.

DELETE /auth/user -> Se borra la cuenta del usuario.

```


### Mensajes

```
GET /messages/search/{id} -> Se obtienen todos los mensajes de un usuario.

GET /messages/{id} -> Se obtienen los mensajes que tiene el usuario logado con el usuario pasado.

POST /messages/{id} -> Se crea un mensaje desde el usuario logado hacia el usuario dado.

```

### Favoritos

```
GET /favorites -> Se obtienen todos los productos favoritos del usuario logado.

GET /favoritesnotpaged/ -> Se obtienen todos los productos favoritos del usuario logado de forma no paginada.

POST /favorites/{id} -> Se añade un producto a los favoritos del usuario logado.

DELETE /favorites/{id} -> Se elimina un producto de los favoritos del usuario logado.

```

### PlataformaS
```
GET /platform/list -> Se obtienen todas las plataformas.

POST /platform -> Se crea una plataforma.

PUT /platform/{id} -> Se edita una plataforma.

DELETE /platform/{id} -> Se elimina una plataforma.
```
### Categorías
```
GET /category/list -> Se obtienen todas las categorías.

POST /category -> Se crea una categoría.

PUT /category/{id} -> Se edita una categoría.

DELETE /category/{id} -> Se elimina una categoría.
```



## Créditos

- [Nicolás Fernández de la Fuente](https://github.com/nicoffb)


Login
![Login]([https://i.imgur.com/pCgxM6t.gif](https://i.imgur.com/pCgxM6t.gif))


El producto por tanto ofrece la capacidad de registrarse con un usuario, este
usuario tendrá la capacidad de marcar los videojuegos que tenga en su punto
de mira con los favoritos. Al entrar verá la lista principal con todos los
videojuegos disponibles, pero podrá alternar para ver únicamente sus
favoritos, o únicamente los que tiene dicho cliente en venta o ha vendido

![Listados](https://i.imgur.com/EOfeEhL.gif)

Otras funcionalidades disponibles son subir y editar tus productos.

![Subir y editar producto](https://imgur.com/QzkCpFX.gif)


También ofrecerá la posibilidad de de ver los detalles de los productos para
saber más acerca de ellos o del vendedor, pudiendo iniciar una conversación
con estos para aclarar los precios o el sitio de encuentro para la venta.

![Conversación](https://i.imgur.com/PtbXjT8.gif)




