package petstore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class PetTest {
	// Atributos
	String uri = "https://petstore.swagger.io/v2/pet"; // ENDEREÇO ENTIDADE PET

	// Métodos
	public String lerJson(String caminhoJson) throws IOException {
		return new String(Files.readAllBytes(Paths.get(caminhoJson)));
	}

	// Incluir - Create - Post
	@Test // Identifica o metodo como um teste para o TestNG
	public void incluirPet() throws IOException {
		String jsonBody = lerJson("db/pet1.json");
		
		// Sintaxe Gherkin
		
		RestAssured.given() // Dado
				.contentType("application/json") // Comum em API Rest - antiga era text/xml
				.log().all().body(jsonBody)
		.when() // Quando
				.post(uri)
		.then() // Entao
				.log().all()
				.statusCode(200)
		;		
	}

}
