package com.joaoneto.parkinglot.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingUtil {

    private static final double FIRST_15_MINUTES = 5.00;
    private static final double FIRST_HOUR = 9.25;
    private static final double ADDITIONAL_15_MINUTES = 1.75;

    private static final double DISCOUNT_PERCENTAGE = 0.30;

    //2023-03-16T15:23:48.616463500
    //20230316-152121
    public static String generateReceipt(){
        LocalDateTime date = LocalDateTime.now();
        String receipt = date.toString().substring(0,19);
        return receipt.replace("-","")
                .replace(":","")
                .replace("T", "-");
    }

    public static BigDecimal calculatePrice(LocalDateTime entryTime, LocalDateTime exitTime) {
        long minutes = entryTime.until(exitTime, java.time.temporal.ChronoUnit.MINUTES);
        double total = 0.0;

        if (minutes <= 15) {
            total = FIRST_15_MINUTES;
        } else if (minutes <= 60) {
            total = FIRST_HOUR;
        } else {
            // Calculate the additional minutes beyond the first hour
            int additionalMinutes = (int) minutes - 60;

            // Calculate the number of additional 15-minute blocks and round up
            int additional15MinutesBlocks = (int) Math.ceil((double) additionalMinutes / 15);

            // Calculate the total price including the additional 15-minute blocks
            total = FIRST_HOUR + (additional15MinutesBlocks * ADDITIONAL_15_MINUTES);
        }

        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal CalculateDiscount(BigDecimal price, long numberOfTimesParking) {
        BigDecimal discount = BigDecimal.ZERO;
        if (numberOfTimesParking % 10 == 1 && numberOfTimesParking > 1) {
            discount = price.multiply(new BigDecimal(DISCOUNT_PERCENTAGE));
        }

        return discount.setScale(2, RoundingMode.HALF_EVEN);
    }
}
