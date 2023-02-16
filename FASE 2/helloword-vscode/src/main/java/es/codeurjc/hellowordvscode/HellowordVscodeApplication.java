package es.codeurjc.hellowordvscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Hola mundo predeterminado al crear el proyecto, borrar al comprobar que todo funcione correctamente.

@SpringBootApplication
public class HellowordVscodeApplication {

	public static void main(String[] args) {
		/*
		 * Se puede reutilizar este main, al fin y al cabo lanza la aplicacióm.
		 * Habría que cambiar el nombre de helloWorld a NexusAplication o algo similar...
		 * 
		 */
		SpringApplication.run(HellowordVscodeApplication.class, args);
	}

}
