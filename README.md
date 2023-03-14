# **Nexus**

Es una red social similar a Twitter, pero con diferente implementación, al utilizar elementos de la Web3. La interfaz gráficamente será levemente similar a la de Discord.

* [Fase 0](#fase-0) 

* [Fase 1](#fase-1) 

* [Fase 2](#fase-2)

## Fase 0

## Integrantes
| Nombre y apellidos | Correo electrónico | Usuario GIT | 
| ------ | ------ | ------ | 
| Oskar Denis Siodmok | DenisUrjc@proton.me | DenisUrjc |  
| Stefano Tomasini Hoefner | s.tomasini.2019@alumnos.urjc.es | Dariagan | 
| Alberto Jesús García Guerra | aj.garciag.2019@alumnos.urjc.es | tomy014 |  
| Álvaro Pindado Castiñeira | a.pindado.2019@alumnos.urjc.es | Alvaropin17 |  


## Entidades
* Usuarios
* Publicaciones
* Etiquetas
* Mensajería
* Tablones


## Funcionalidades
* Registro e inicio de sesión: el usuario debe poder registrarse con un correo y una contraseña y poder acceder a su cuenta con esas credenciales.
* Ingreso y modificación de información personal: el usuario debe poder añadir la información personal que considere necesaria y modificarla cuando quiera.
* Cambio y recuperación de contraseña: el usuario debe poder cambiar la contraseña y, en caso de haberla olvidado, solicitar un método de recuperación.
* Creación y publicación de publicaciones: el usuario debe poder publicar textos, imagenes o vídeos y etiquetarlos para facilitar el descubrimiiento y seguimiento del contenido por parte de otros usuarios.
* Seguimiento de contenido afín: el usuario debe poder seguir a otros usuarios y etiquetas.
* Bloqueo y silinciación de contenido: el usuario debe poder bloquear etiquetas y usuarios que no le gusten.
* "Me gusta" y guardado de publicaciones: el usuario debe poder dar "me gusta" y guardar publicaciones para la posteridad.
* Personalización del perfil: el usuario debe poder personalizar su perfil.
* Búsqueda de perfiles o publicaciones: el usuario debe poder realizar búsquedas de otros perfiles, publicaciones o etiquetas.


## Permisos de los usuarios
* Usuario registrado: publicaciones, mensajes, tablones
* Usuario no registrado: publicaciones, tablones
* Administrador: publicaciones, mensajes, tablones


## Imágenes
* Avatares
* Publicaciones de usuario con imagenes 
* Bandera/Pancarta (también conocido como "banner")
* Adjuntar imagen en mensaje privado


## Gráficos

Se utilizarán gráficos para representar:

* Interacciones del usuario.
* Tiempo de uso de la aplicación


## Tecnología complementaria
* Blockchain
* Notificaciones al usuario de las interacciones


## Algoritmo o consulta avanzada

Mostrar publicaciones relacionadas con los gustos y preferencias del usuario. Se tendrá en cuenta las publicaciones a las que el usuario ha reaccionado de manera positiva y las etiquetas que sigue.  

# Fase 1 

## Páginas maquetadas en la fase 1
Se han pasado 3 tipos diferentes de imágenes (no registrado, registrado y moderador) para hacer ver de manera sencilla las diferencias mínimas que tendrá la interfaz dados los diferentes tipos de usuarios que contemplamos.
* Homepage: esta es una página estatica que se encargara de presentar la aplicación. Hay un boton "launch app" con el que lanzaremos la aplicacion.   
![image](https://user-images.githubusercontent.com/80198176/218340455-5d6fe757-cc35-450a-955e-4b19e57bfdd7.png)
![image](https://user-images.githubusercontent.com/80198176/218340466-e6e287e6-8fa2-4bc4-86de-587445c83635.png)
![image](https://user-images.githubusercontent.com/80198176/218340478-aee24d6c-ca63-4219-ae84-32b11054cd43.png)
 
* Signup: esta es la página en la que los usuario se registraran. Habrán de rellenar los campos correspondientes y registrarse o, en caso de ya disponer de una cuenta, podrán ir a la página de log in.  
![image](https://user-images.githubusercontent.com/80198176/218340784-5e431798-736b-4dbe-aacf-97eade571e74.png)

* Login: esta es la página en la que los usuarios iniciarán sesión. Habrán de rellenar los campos con credenciales válidas e iniciar sesión o, en caso de no disponer de una cuenta, podrán ir a la página de sign up.  
![image](https://user-images.githubusercontent.com/80198176/218340675-859a9dd2-48f2-43bb-b4e1-f6444924d425.png)

* Hilo (no registrado): esta es la vista que tendran los usuarios no registrados al clickar en un hilo.  
![image](https://user-images.githubusercontent.com/80198176/218341250-2160f6ad-1267-4946-a57b-0eff54df0104.png)


* Feed (moderador): esta es la feed que tendrán los usuarios con rango de moderador.  
![image](https://user-images.githubusercontent.com/80198176/218340991-dc763338-5769-48aa-a436-5f5b9aaf870e.png)

* Perfil (registrado): este es el perfil que tendrán los usuarios registrados.  
![image](https://user-images.githubusercontent.com/80198176/218341314-80b96318-5d9d-44b6-8796-f7141a5115d1.png)


## Diagrama de navegación
![diagrama_navegacion](https://user-images.githubusercontent.com/80198176/218341414-53fe26b0-4dee-4f52-aae6-27d9028d141c.png)


# Fase 2 

## CONFIGURATION

You must complete these steps in order to be able to test our application.

1. First download the project code, from https://github.com/CodeURJC-DAW-2022-23/webapp13 and then extract the zip and open some java ide.
2. Donwload the MySQL Workbench Community from https://dev.mysql.com/downloads/workbench/ and open the installation file.
3. Install version 8.0.32 version MySQL Workbench Community. Follow the instructions to install it correctly and set everything as default.
4. Launch MySQL Workbench from your program menu.
5. To connect to the default local MySQLL server click on the box that may appear under MySQL Connections.
6. If asked enter your MySQL server credentials.
7. Make sure to change the aplication.properties to create-drop instead of update.


## Database diagram: 
![Imagen de WhatsApp 2023-03-13 a las 02 17 51](https://user-images.githubusercontent.com/38651496/224676556-298982de-9e4a-442c-87a4-f234f6fa6334.jpg)

## Class diagram:
![image](https://user-images.githubusercontent.com/38651496/224727082-53f56eda-680a-497c-ac5f-d45678b3a308.png)

## Members participation: 

### 5 most representative commits: 

#### Oskar: 

|  Description | Link | 
| ------ | ------ | 
| Post deletion logic | [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/6d1345b29daa22bc49d680acb9bdb50c59ac217b) | 
| Did ResourcesBuilder and default set profile pic | [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/daa4cbf68ae9ec758b71c96635d825b08449d8ef) | 
| Tweet reporting endpoint and minor refactors | [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/0f4b89fa17e07a166e81077d8d47718fee749e9d) | 
| Paginated tweets endpoint and json ignore annotations  | [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/6151ddf756fd042dd61b817c2394db1079de31bc) 
| Paginated tweets front-end integration with Ajax | [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/c9cc77e58bfd5f3f0b18b2068001205c1c5bc800) | 

#### Stefano: 

|  Description | Link | 
| ------ | ------ | 
| Replaced HttpSession("user") att by DB query due to outdated-state bugs | [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/5a225131d9172d07e898527818e381dc699be28e) | 
| Post tweets functionality | [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/3e0b526bef8502f05473b704009d373a3791d8b0) | 
| Profile pic upload | [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/41cb81cb7738a169a7c75e46331ddbf597709fdb) | 
| Tweet.Builder and User.Builder improvements | [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/25e540053a7b6103975055fa9cc88f351ab990d6) | 
| Login and logout logic added and minor fixes | [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/926183d8c9039c101fee2ec00507092031a049a0) | 
| Improved my User.Builder class | [Commit 6](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/bbdbbfcc0ee74d675843035dc9272bd6dace0262) |

#### Alberto: 

|  Description | Link | 
| ------ | ------ | 
| Created program structure dependencies. | [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/195d5750abea4ec5cecfc1e86f195c5ec97ce4d4) | 
| Created database, models and inicializated the project | [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/8ff467e32452a6188cb83b8066a5101ad028f76e) | 
| Modified repository and created a HandlerNotFound | [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/c4db0f4a67e3df3d96fb963fdf621262b32a3acf) | 
| Login and logout logic added and minor fixes | [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/926183d8c9039c101fee2ec00507092031a049a0) | 
| Added sample data to the database and other fixes. | [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/aa86a848ff7134807ce57c668ad8d0187ea47994) | 

#### Álvaro: 

|  Description | Link | 
| ------ | ------ | 
| Creation of TweetService bean and fixed ProfileController params | [Commit 1](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/9c40ff853b7c2a63e17f5ee91e2bd9333e0ae119) | 
| Simplified LoginController | [Commit 2](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/9b4c8f84d9ffac5837aacdb61479456cebeb2a1f) | 
| First implementation of LoginController and UserService. | [Commit 3](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/e69251427d3fda7d6cb69469513962ae4c34ae2b) | 
| User update | [Commit 4](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/a8fb0f8105e2381df302729e72ad28aefba6e66e) | 
| Create feed controller and adjusted userfeed HTML | [Commit 5](https://github.com/CodeURJC-DAW-2022-23/webapp13/commit/e60581fe702673cdcd52af9679475efd64d50d16) | 

### 5 files with more participation: 

#### Oskar: 

Did a lot of radical refactors, a lot of implementations for feed controllers, and took care of the moderating section and all things web3.

* [FeedController.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/controller/FeedController.java) 
* [OptTwo.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/utilities/OptTwo.java)
* [OptPair.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/utilities/OptPair.java)
* [ResourcesBuilder.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/controller/ResourcesBuilder.java)
* [ProfileController.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/controller/ProfileController.java) 

#### Stefano: 

I created the majority of the controllers, did a lot of refactors, implemented a lot of methods and classes, worked on the DB model and persistence.

Click on "(Browse History)" to see pre-rename history of files.

* [ProfileController.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/controller/ProfileController.java) 
* [User.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/model/User.java) 
* [UserService.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/service/UserService.java) 
* [Tweet.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/model/Tweet.java) 
* [SignupController.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/controller/SignUpController.java) 
* [LoginController.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/controller/LoginController.java) 

#### Alberto: 

I've been working mainly on the program structure at the start of the split, and creating the database along with some sample data. Also I've worked creating the models and the controllers for the database and the tweet logic.

* [Tweet.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/model/Tweet.java) 
* [User.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/model/User.java)
* [TweetRepository.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/repository/TweetRepository.java)
* [UserRepository.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/repository/UserRepository.java)
* [DataBaseController.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/controller/DataBaseController.java)

#### Álvaro: 

I've edited and helped with the implementations of most controllers, connected the HTML posts and requests properly and fixed the login and signup issues that we had to deal with.

* [LoginController.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/controller/LoginController.java) 
* [ProfileController.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/controller/ProfileController.java) 
* [TweetService.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/service/TweetService.java)
* [SignupController.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/blob/dev/FASE2/src/main/java/es/codeurjc/backend/controller/SignUpController.java) 
* [FeedController.java](https://github.com/CodeURJC-DAW-2022-23/webapp13/commits/dev/FASE2/src/main/java/es/codeurjc/backend/controller/FeedController.java)

