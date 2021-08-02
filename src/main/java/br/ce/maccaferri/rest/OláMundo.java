package br.ce.maccaferri.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

////public class OláMundo {
//
//	public static void main(String[] args) {
//		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
//		
//		// criou um objeto response que est� recebendo essa requisição ctrl + 1
//		System.out.println(response.getBody().asString().equals("Ola Mundo!"));
//		System.out.println(response.statusCode() == 200);
//
//		ValidatableResponse validacao = response.then();
//		validacao.statusCode(201);
//	   
//	}
//
//}
