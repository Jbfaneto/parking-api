package com.joaoneto.parkinglot;


import com.joaoneto.parkinglot.web.dtos.parking.ParkingCreateDto;
import com.joaoneto.parkinglot.web.dtos.parking.ParkingResponseDto;
import com.joaoneto.parkinglot.web.exceptions.exceptionBody.ExceptionResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/parking/parking-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/parking/parking-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkingIT {
    @Autowired
    private WebTestClient client;

    @Test
    public void checkInWithSuccess(){
        ParkingResponseDto response = client
                .post()
                .uri("/api/v1/parking/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .bodyValue(new ParkingCreateDto("ABC-1234", "Fiat", "Uno", "White", "04084865036"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ParkingResponseDto.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.model()).isEqualTo("Uno");
    }

    @Test
    public void checkInWithInvalidCpf(){
        ExceptionResponseBody response = client
                .post()
                .uri("/api/v1/parking/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .bodyValue(new ParkingCreateDto("ABC-1234", "Fiat", "Uno", "White", "12345678901"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ExceptionResponseBody.class)
                .returnResult()
                .getResponseBody();
        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void checkInWithNotAdmin(){
        ExceptionResponseBody response = client
                .post()
                .uri("/api/v1/parking/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client, "nome1@email.com", "123456"))
                .bodyValue(new ParkingCreateDto("ABC-1234", "Fiat", "Uno", "White", "04084865036"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ExceptionResponseBody.class)
                .returnResult()
                .getResponseBody();
        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void checkInUnauthorized(){
        WebTestClient.ResponseSpec response = client
                .post()
                .uri("/api/v1/parking/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ParkingCreateDto("ABC-1234", "Fiat", "Uno", "White", "04084865036"))
                .exchange()
                .expectStatus().isUnauthorized();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void checkInWithNoCpf(){
        ExceptionResponseBody response = client
                .post()
                .uri("/api/v1/parking/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .bodyValue(new ParkingCreateDto("ABC-1234", "Fiat", "Uno", "while", "39506397805"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ExceptionResponseBody.class)
                .returnResult()
                .getResponseBody();
        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Sql(scripts = "/sql/parking/parking-occupied-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/parking/parking-occupied-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void checkInWithAllSpotsOccupied(){
        ExceptionResponseBody response = client
                .post()
                .uri("/api/v1/parking/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .bodyValue(new ParkingCreateDto("ABC-1234", "Fiat", "Uno", "while", "04084865036"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ExceptionResponseBody.class)
                .returnResult()
                .getResponseBody();
        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void getReceiptWithSuccessAsAdmin(){
        ParkingResponseDto response = client
                .get()
                .uri("/api/v1/parking/checkin/20240416-110201")
                .headers(JwtAuthentication.getHeaderAuthorization(client, "admin@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ParkingResponseDto.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.model()).isEqualTo("Uno");
    }

    @Test
    public void getReceiptWithSuccessAsClient(){
        ParkingResponseDto response = client
                .get()
                .uri("/api/v1/parking/checkin/20240416-110201")
                .headers(JwtAuthentication.getHeaderAuthorization(client, "nome1@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ParkingResponseDto.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.model()).isEqualTo("Uno");
    }



    @Test
    public void getReceiptWithExitTimeNotNull(){
        ExceptionResponseBody response = client
                .get()
                .uri("/api/v1/parking/checkin/20240416-110202")
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
    public void getReceiptWithWrongReceipt(){
        ExceptionResponseBody response = client
                .get()
                .uri("/api/v1/parking/checkin/20240416-110203")
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
    public void getReceiptUnauthorized(){
        WebTestClient.ResponseSpec response = client
                .get()
                .uri("/api/v1/parking/checkin/20240416-110201")
                .exchange()
                .expectStatus().isUnauthorized();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
    }

}
