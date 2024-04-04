package com.joaoneto.parkinglot;


import com.joaoneto.parkinglot.web.dtos.user.*;
import com.joaoneto.parkinglot.web.exceptions.exceptionBody.ExceptionResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUserWithSuccessStatus201() {
        UserCreateResponseDto responseBody = testClient.post()
                .uri("api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequestDto("tody@gmail.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserCreateResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.username()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.username()).isEqualTo("tody@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.role()).isEqualTo("client");
    }

    @Test
    public void createUserInvalidUsername() {
        ExceptionResponseBody  responseBody = testClient.post()
                .uri("api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequestDto("todygmail.com", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.status()).isEqualTo(400);
    }

    @Test
    public void createUserNullUsername() {
        ExceptionResponseBody  responseBody = testClient.post()
                .uri("api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequestDto("", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.status()).isEqualTo(400);
    }

    @Test
    public void createUserAlreadyExists() {
        ExceptionResponseBody  responseBody = testClient.post()
                .uri("api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequestDto("nome2@email.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.status()).isEqualTo(409);
    }

    @Test
    public void createUserInvalidPassword() {
        ExceptionResponseBody  responseBody = testClient.post()
                .uri("api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequestDto("todygmail.com", ""))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.status()).isEqualTo(400);
    }

    @Test
    public void createUserInvalidPasswordLessThanSixCharacters() {
        ExceptionResponseBody  responseBody = testClient.post()
                .uri("api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequestDto("todygmail.com", "1234"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.status()).isEqualTo(400);
    }

    @Test
    public void findUserByIdWithSuccessStatus200(){
        GetUserDto responseBody = testClient.get()
                .uri("api/v1/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetUserDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.id()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.username()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.username()).isEqualTo("admin@email.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.role()).isEqualTo("ADMIN");
    }

    @Test
    public void findAnotherUserByIdWithSuccessStatus200(){
        GetUserDto responseBody = testClient.get()
                .uri("api/v1/users/101")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetUserDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.id()).isEqualTo(101);
        org.assertj.core.api.Assertions.assertThat(responseBody.username()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.username()).isEqualTo("nome1@email.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.role()).isEqualTo("ADMIN");
    }

    @Test
    public void findUserByIdNotFoundStatus404(){
        ExceptionResponseBody responseBody = testClient.get()
                .uri("api/v1/users/110")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.status()).isEqualTo(404);
    }

    @Test
    public void findUserByIdNotFoundStatus403(){
        ExceptionResponseBody responseBody = testClient.get()
                .uri("api/v1/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "nome2@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.status()).isEqualTo(403);
    }

    @Test
    public void updatePasswordWithSuccessStatus200() {
        UpdateUserResponseDto responseBody = testClient.put()
                .uri("api/v1/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UpdateUserRequestDto("123456", "123445", "123445"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UpdateUserResponseDto.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.id()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.username()).isNotNull();
    }


    @Test
    public void updatePasswordInvalidPasswordConfirmationStatus400() {
        ExceptionResponseBody responseBody = testClient.put()
                .uri("api/v1/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UpdateUserRequestDto("123456", "123456", "123458"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.status()).isEqualTo(400);
    }

    @Test
    public void listAllUsersWithSuccessStatus200() {
        List<GetUserDto> responseBody = testClient.get()
                .uri("api/v1/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GetUserDto.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(3);
    }

    @Test
    public void listAllUsersWithStatus403() {
        ExceptionResponseBody responseBody = testClient.get()
                .uri("api/v1/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "nome2@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.status()).isEqualTo(403);
    }
}
