package com.joaoneto.parkinglot;


import com.joaoneto.parkinglot.jwt.JwtToken;
import com.joaoneto.parkinglot.web.dtos.authentication.UserLoginDto;
import com.joaoneto.parkinglot.web.exceptions.exceptionBody.ExceptionResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthenticationIT {
    @Autowired
    private WebTestClient client;

    @Test
    public void testAuthenticationWithSuccess() {
    JwtToken token = client
                    .post()
                    .uri("/api/v1/auth")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new UserLoginDto("admin@email.com", "123456"))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(JwtToken.class)
                    .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(token).isNotNull();
    org.assertj.core.api.Assertions.assertThat(token.getToken()).isNotBlank();
    }

    @Test
    public void testAuthenticationWithFailure() {
        ExceptionResponseBody responseBody = client
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginDto("joao@gmail.com", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ExceptionResponseBody.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.status()).isEqualTo(400);

    }
}
