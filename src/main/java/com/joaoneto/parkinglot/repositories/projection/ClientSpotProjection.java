package com.joaoneto.parkinglot.repositories.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ClientSpotProjection {
    String getPlateNumber();

    String getBrand();

    String getModel();

    String getColor();

    String getClientCpf();

    String getReceipt();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getEntryTime();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getExitTime();

    String getSpotCode();

    BigDecimal getPrice();

    BigDecimal getDiscount();
}
