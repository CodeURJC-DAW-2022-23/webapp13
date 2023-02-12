# **Nexus**

Es una red social similar a Twitter, pero con diferente implementación, al utilizar elementos de la Web3. La interfaz gráficamente será levemente similar a la de Discord.


## Integrantes
| Nombre y apellidos | Correo electrónico | Usuario GIT |
| ------ | ------ | ------ |
| Oskar Denis Siodmok | DenisUrjc@proton.me | DenisUrjc |  
| Stefano Tomasini Hoefner | s.tomasini.2019@alumnos.urjc.es | Dariagan |    
| Alberto Jesús García Guerra | aj.garciag.2019@alumnos.urjc.es | tomy014 |  
| Carlos Solsona Álvarez | c.solsona.2020@alumnos.urjc.es | azu173 |  
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

