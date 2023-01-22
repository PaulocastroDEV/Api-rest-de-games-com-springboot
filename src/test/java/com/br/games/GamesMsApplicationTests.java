package com.br.games;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.games.domain.repository.GameRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import util.ResourceUtils;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class GamesMsApplicationTests {
	
	@LocalServerPort
	private int port;
	private static final String DADOS_INVALIDOS_PROBLEM_TITLE="dados-invalidos";
	private String jsonGameCorreto;
	private String jsonGameSemNome;
	private String jsonGameSemGenero;
	private String jsonGameSemLancamento;
	
	@Autowired
	private GameRepository gameRepository;
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port=port;
		RestAssured.basePath="/games";
		jsonGameCorreto = ResourceUtils.getContentFromResource("/json/correto.json");
		jsonGameSemNome = ResourceUtils.getContentFromResource("/json/semnome.json");
		jsonGameSemGenero= ResourceUtils.getContentFromResource("/json/semgenero.json");
		jsonGameSemLancamento= ResourceUtils.getContentFromResource("/json/semlancamento.json");
	}
	@Test
	public void MustReturnStatus200_WhenSearchGames() {
		RestAssured
		.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	@Test
	public void MustReturnStatus201_WhenRegistrationGame() {
		RestAssured
			.given()
				.body(jsonGameCorreto)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value());
	}
	@Test
	public void MustReturnStatus400_WhenRegistrationGameWithoutName() {
		RestAssured
			.given()
				.body(jsonGameSemNome)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title",equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}
	@Test
	public void MustReturnStatus400_WhenRegistrationGameWithoutGenre() {
		RestAssured
			.given()
				.body(jsonGameSemGenero)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title",equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}
	@Test
	public void MustReturnStatus400_WhenRegistrationGameWithoutLaunch() {
		RestAssured
			.given()
				.body(jsonGameSemLancamento)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title",equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}
}
