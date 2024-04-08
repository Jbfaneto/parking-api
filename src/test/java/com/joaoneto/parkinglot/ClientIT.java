package com.joaoneto.parkinglot;

import com.joaoneto.parkinglot.web.dtos.client.ClientCreateRequestDto;
import com.joaoneto.parkinglot.web.dtos.client.ClientCreateResponseDto;
import com.joaoneto.parkinglot.web.dtos.client.ClientGetResponseDto;
import com.joaoneto.parkinglot.web.exceptions.exceptionBody.ExceptionResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts ="/sql/clients/clients-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts ="/sql/clients/clients-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientIT {
    @Autowired
    private WebTestClient client;

    @Test
    public void testCreateClientWithSuccess() {
        ClientCreateResponseDto response = client
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client, "toby@email.com", "123456"))
                .bodyValue(new ClientCreateRequestDto("toby stewart", "05517610080"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClientCreateResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.id()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.name()).isEqualTo("toby stewart");
    }

    @Test
    public void testCreateClientWithInvalidCpf() {
       ExceptionResponseBody response = client
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client, "toby@email.com", "123456"))
                .bodyValue(new ClientCreateRequestDto("toby stewart", "123456789"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

         org.assertj.core.api.Assertions.assertThat(response).isNotNull();
         org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(400);
    }

    @Test
    public void testCreateClientForbidden() {
        ExceptionResponseBody response = client
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .bodyValue(new ClientCreateRequestDto("toby stewart", "05517610080"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(403);
    }

    @Test
    public void testCreateClientCpfAlreadyExists() {
        ExceptionResponseBody response = client
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client, "toby@email.com", "123456"))
                .bodyValue(new ClientCreateRequestDto("toby stewart", "79714506050"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(409);
    }

    @Test
    public void testCreateClientUnauthorized() {
        WebTestClient.ResponseSpec response = client
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ClientCreateRequestDto("toby stewart", "05517610080"))
                .exchange()
                .expectStatus().isUnauthorized();

        HttpStatusCode status = response.returnResult(Object.class).getStatus();
        org.assertj.core.api.Assertions.assertThat(status).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testGetClientWithSuccess() {
        ClientGetResponseDto response = client
                .get()
                .uri("/api/v1/clients/10")
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientGetResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.id()).isEqualTo(10);
        org.assertj.core.api.Assertions.assertThat(response.name()).isEqualTo("Barbara Mayers");
    }

    @Test
    public void testGetClientNotFound() {
        ExceptionResponseBody response = client
                .get()
                .uri("/api/v1/clients/100")
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(404);
    }

    @Test
    public void testGetClientForbidden() {
        ExceptionResponseBody response = client
                .get()
                .uri("/api/v1/clients/10")
                .headers(JwtAuthentication.getHeaderAuthorization(client, "nome1@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(403);
    }

    @Test
    public void testGetClientUnauthorized() {
        WebTestClient.ResponseSpec response = client
                .get()
                .uri("/api/v1/clients/10")
                .exchange()
                .expectStatus().isUnauthorized();

        HttpStatusCode status = response.returnResult(Object.class).getStatus();
        org.assertj.core.api.Assertions.assertThat(status).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testGetAllClientsWithSuccess() {
        PageDto<ClientGetResponseDto> response = client
                .get()
                .uri("/api/v1/clients")
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<PageDto<ClientGetResponseDto>>() {})
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
    }
}
