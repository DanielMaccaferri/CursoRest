package br.ce.maccaferri.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.lessThan;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;



public class UserJsonTest {
	
	@Test
	public void deveVerificarPrimeiroNivel() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/1")
		.then()
		.statusCode(200)
		.body("id", is(1))
		.body("name", containsString("João da Silva"))
		.body("age", greaterThan(29));
		
		
	}
	
	@Test
	public void deveVerificarPrimeiroNivelOutrasFormas(){
		Response response = request(Method.GET, "http://restapi.wcaquino.me/users/1");
		
		//System.out.println(response.path("id"));
		//path
		Assert.assertEquals(new Integer(1), response.path("id"));   //esperado é 1 e o atual é o id // o path retorna um objeto
		Assert.assertEquals(new Integer(1), response.path("%s", "id")); 
		
		//jsonpath
		JsonPath jpath = new JsonPath(response.asString());
		Assert.assertEquals(1, jpath.getInt("id"));
		
		//from
		int id = JsonPath.from(response.asString()).getInt("id");
		Assert.assertEquals(1, id);  // eu espero que um seja o que venha no id
				
	}

	@Test
	public void deveVerificarSegundoNivel() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/2")
		.then()
		.statusCode(200)
		.body("id", is(2))
		.body("name", containsString("Joaquina"))
		.body("endereco.rua", is("Rua dos bobos"));
		
	}
	
	@Test
	public void deveVerificarLista() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/3")
		.then()
		.statusCode(200)
		.body("id", is(3))
		.body("name", containsString("Ana"))
		.body("age", greaterThan(17))
		.body("filhos", hasSize(2)) //tem dois objetos dentro da lista 
		.body("filhos[0].name", is("Zezinho"))
		.body("filhos[1].name", is("Luizinho"))
		.body("filhos.name", hasItem("Zezinho"))
		.body("filhos.name", hasItems("Zezinho", "Luizinho"))
		;

	}
	
	@Test
	public void deveRetornarErroUsuarioInexistente() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users/4")
		.then()
		.statusCode(404)	
		.body("error", is("Usuário inexistente"))
		;
	
	}
	
	
	@Test
	public void deveVerificarListaRaiz() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users")
		.then()
		.statusCode(200)	
		.body("$", hasSize(3)) //$ procurando na raiz poderia também deixar em branco ""
		.body("", hasSize(3))
		.body("name", hasItems("João da Silva", "Maria Joaquina", "Ana Júlia"))
		.body("age[1]", is(25)) //is Matcher do Hamcrest
		.body("filhos.name", hasItem(Arrays.asList("Zezinho", "Luizinho")))
		.body("salary", contains(1234.5678f, 2500, null)) // tem que mandar todos os valortes da lista e na ordem correta
		;		
	}	
	
	@Test
	public void devofazerVerificacoesAvancadas() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/users")
		.then()
		.statusCode(200)	
		.body("$", hasSize(3)) //$ procurando na raiz poderia também deixar em branco ""
		//.body("", hasSize(3))
		.body("age.findAll{it <= 25}.size()", is(2))
		.body("age.findAll{it <= 25 && it > 20}.size()", is(1))
		.body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))//lista
		.body("findAll{it.age <= 25}[0].name", is("Maria Joaquina"))//transforma a lista em objeto //[0] é o primeiro elemento da lista
		.body("findAll{it.age <= 25}[1].name", is("Ana Júlia"))//transforma a lista em objeto //[0] é o primeiro elemento da lista
		.body("findAll{it.age <= 25}[-1].name", is("Ana Júlia"))//transforma a lista em objeto //[0] é o primeiro elemento da lista
		.body("find{it.age <= 25}.name", is("Maria Joaquina"))//find pega o primeiro elemento
		.body("findAll{it.name.contains('n')}.name", hasItems("Maria Joaquina", "Ana Júlia"))
		//.body("findAll{it.name.lenght() > 10}.name", hasItems("João da Silva", "Maria Joaquina"))
		.body("name.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
		.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}", hasItem ("MARIA JOAQUINA"))
		.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}.toArray()", allOf(arrayContaining("MARIA JOAQUINA"), arrayWithSize(1)))
		.body("age.collect{it * 2}", hasItems (60 ,50 ,40))
		.body("id.max()", is (3))
		.body("id.min()", is (1))
		.body("salary.min()", is (1234.5678f))
		.body("salary.findAll{it != null}.sum()", is(closeTo(3734.5678f, 0.001)))
		.body("salary.findAll{it != null}.sum()", allOf(greaterThan(3000d), lessThan(4000d)))


		;
		
		
		}	
	}

