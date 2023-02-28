package es.codeurjc.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//Hola mundo predeterminado al crear el proyecto, borrar al comprobar que todo funcione correctamente.


@ComponentScan
@SpringBootApplication 
public class Application {

	public static void main(String[] args) {
		/*
		 * Se puede reutilizar este main, al fin y al cabo lanza la aplicacióm.
		 * Habría que cambiar el nombre de helloWorld a NexusAplication o algo similar...
		 * 
		 */
		SpringApplication.run(Application.class, args);
	}

}
