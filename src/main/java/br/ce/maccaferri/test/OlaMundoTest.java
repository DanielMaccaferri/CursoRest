package br.ce.maccaferri.test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {

	@Test
	public void testOlaMUndo() {
		Response response = request(Method.GET, "http://restapi.wcaquino.me:80/ola");
		// criou um objeto response que está recebendo essa requisição
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(response.statusCode() == 200);
		Assert.assertTrue("O status code deveria ser 200", response.statusCode() == 200);
		Assert.assertEquals(200, response.statusCode()); //esperado é o primeiro e o atual é o segundo
		// valor esperado e atual = assert equals
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);

	}

//	@Test
//	public void segundoMetodoRestAssured() {
////		Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
////		ValidatableResponse validacao = response.then(); // validacao é objeto
////		validacao.statusCode(200);
//		
//		//get("http://restapi.wcaquino.me/ola").then().statusCode(200);
//		
//		given()// pre-consição		
//		.when() // ação de fato
//			.get("http://restapi.wcaquino.me/ola")		
//		.then()
//			.assertThat()	
//			.statusCode(200); //assertivas
//	}

	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		Response response = request(Method.GET, "http://restapi.wcaquino.me:80/ola");
		ValidatableResponse validacao = response.then();// validacao é um objeto de validação
		validacao.statusCode(200);
		// métodos estático pode colocar um import para não precisar colocar o
		// RestAssured
		get("http://restapi.wcaquino.me/ola").then().statusCode(200);

		given()// pre-consição
				.when() // ação de fato
				.get("http://restapi.wcaquino.me/ola").then().assertThat()// não faz nada só traz visibilidade.
				.statusCode(200); // assertivas

	}

	@Test
	public void devoConhecerMatchersHamcrest() {//Igualdades
		Assert.assertThat("Maria", Matchers.is("Maria")); //atual e esperado
		Assert.assertThat(128, Matchers.is(128));
		Assert.assertThat(128, Matchers.isA(Integer.class));
		Assert.assertThat(128d, Matchers.isA(Double.class));
		Assert.assertThat(128d, Matchers.greaterThanOrEqualTo(128d));
		Assert.assertThat(128d, Matchers.lessThan(129d));
		
		List<Integer> impares = Arrays.asList(1,3,5,7,9);//trabalhar com listas
		assertThat(impares, Matchers.hasSize(5));
		assertThat(impares, contains(1,3,5,7,9));
		assertThat(impares, containsInAnyOrder(3,1,9,5,7));
		assertThat(impares, hasItem(3)); //ver um ítem da lista
		assertThat(impares, hasItems(3,5)); //verificar mais de um item
		
		//vários Matchers alinhados // conectar várias assertivas dentro de uma mesma lógica
		assertThat("Maria", is(not("João")));
		assertThat("Maria", not("João"));//não é
		assertThat("Joaquim", anyOf(is("Maria"), is("Joaquim"))); // pode ser qualquer uma das alternativas //ou
		assertThat("Joaquim", allOf(startsWith("Joa"), endsWith("quim"), containsString("quim"))); 


	}
	
	@Test
	public void devovalidarOBody() {
		given()// pre-consição		
		.when() // ação de fato
			.get("http://restapi.wcaquino.me/ola")		
		.then()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo"))
			.body(is(not(nullValue()))); // verificar que o corpo não está vazio
		
		
	}

}