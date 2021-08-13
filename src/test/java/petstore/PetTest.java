package petstore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.contains;

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
				.body("name", is("Julio Cesar"))
				.body("status", is("unavailable"))
				.body("category.name", is("dog"))
				.body("tags.name", contains("sta"))
				
		;		
	}

	@Test
	public void consultarPet() {
		String petId = "1305";
		
		RestAssured.given()
				.contentType("application/json")
				.log().all()
		
		.when()
				.get(uri + "/" + petId)
				
		.then()
				.log().all()
				.statusCode(200)
				.body("category.name", is("dog"))
				.body("status", is("unavailable"))
		;
				
		
	}
}
