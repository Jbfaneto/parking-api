package com.joaoneto.parkinglot;

import com.joaoneto.parkinglot.web.dtos.spot.SpotCreateDto;
import com.joaoneto.parkinglot.web.dtos.spot.SpotCreateResponseDto;
import com.joaoneto.parkinglot.web.exceptions.exceptionBody.ExceptionResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/spots/spots-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/spots/spots-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SpotIT {
    @Autowired
    WebTestClient client;

    @Test
    public void createSpotWithSuccess() {
        SpotCreateResponseDto response = client
                .post()
                .uri("api/v1/spots")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client,"admin@email.com", "123456"))
                .bodyValue(new SpotCreateDto("A-15", "free"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(SpotCreateResponseDto.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.id()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.code()).isEqualTo("A-15");
    }

    @Test
    public void createSpotWithInvalidData() {
        ExceptionResponseBody response = client
                .post()
                .uri("api/v1/spots")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .bodyValue(new SpotCreateDto("", "free"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ExceptionResponseBody.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createSpotWithInvalidStatus() {
        ExceptionResponseBody response = client
                .post()
                .uri("api/v1/spots")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .bodyValue(new SpotCreateDto("A-15", "invalid"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ExceptionResponseBody.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createSpotWithExistingCode() {
        ExceptionResponseBody response = client
                .post()
                .uri("api/v1/spots")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .bodyValue(new SpotCreateDto("A-01", "free"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ExceptionResponseBody.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(HttpStatus.CONFLICT.value());
    }

    @Test
    public void createSpotWithoutAuthorization() {
        WebTestClient.ResponseSpec response = client
                .post()
                .uri("api/v1/spots")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SpotCreateDto("A-15", "free"))
                .exchange()
                .expectStatus().isUnauthorized();


        HttpStatusCode status = response.returnResult(Object.class).getStatus();
        org.assertj.core.api.Assertions.assertThat(status).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void createSpotWithForbiddenStatus() {
        ExceptionResponseBody response = client
                .post()
                .uri("api/v1/spots")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client, "nome2@email.com", "123456"))
                .bodyValue(new SpotCreateDto("A-15", "free"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ExceptionResponseBody.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getSpotByCodeWithSuccess() {
        SpotCreateResponseDto response = client
                .get()
                .uri("api/v1/spots/A-01")
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(SpotCreateResponseDto.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.id()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.code()).isEqualTo("A-01");
    }

    @Test
    public void getSpotByCodeWithNotFound() {
        ExceptionResponseBody response = client
                .get()
                .uri("api/v1/spots/A-99")
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ExceptionResponseBody.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void getSpotByCodeWithoutAuthorization() {
        WebTestClient.ResponseSpec response = client
                .get()
                .uri("api/v1/spots/A-01")
                .exchange()
                .expectStatus().isUnauthorized();

        HttpStatusCode status = response.returnResult(Object.class).getStatus();
        org.assertj.core.api.Assertions.assertThat(status).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void getSpotByCodeWithForbiddenStatus() {
        ExceptionResponseBody response = client
                .get()
                .uri("api/v1/spots/A-01")
                .headers(JwtAuthentication.getHeaderAuthorization(client, "nome2@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ExceptionResponseBody.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
}
