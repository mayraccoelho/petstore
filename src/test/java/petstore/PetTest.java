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
	String petId = "1305"; // ID PET

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
				.body("category.name", is("MM1208DOG"))
				.body("tags.name", contains("data"))
				
		;		
	}

	@Test(priority=2)
	public void consultarPet() {
//		String petId = "1305";
		String token = 
				
		RestAssured.given()
				.contentType("application/json")
				.log().all()
		
		.when()
				.get(uri + "/" + petId)
				
		.then()
				.log().all()
				.statusCode(200)
				.body("name", is("Julio Cesar"))
				.body("category.name", is("MM1208DOG"))
				.body("status", is("unavailable"))
		.extract()
				.path("category.name")
		;
				
				System.out.println("O token é " + token);
	}

	@Test(priority=3)
	public void alterarPet() throws IOException {
		String jsonBody = lerJson("db/pet2.json");
		
		RestAssured.given()
				.contentType("application/json")
				.log().all()
				.body(jsonBody)
		.when()
				.put(uri)
		.then()
				.log().all()
				.statusCode(200)
				.body("name", is("Julio Cesar"))
				.body("category.name", is("MM1208DOG"))
				.body("status", is("soldForMayra"))
		;
	}
	
	@Test(priority=4)
	public void deletarPet() throws IOException {
//		String petId = "1305";
		
		RestAssured.given()
				.contentType("application/json")
				.log().all()
		.when()
				.delete(uri + "/" + petId)
		.then()
				.log().all()
				.statusCode(200)
				.body("code", is(200))
				.body("type", is("unknown"))
				.body("message", is(petId))
		;
	}

}
