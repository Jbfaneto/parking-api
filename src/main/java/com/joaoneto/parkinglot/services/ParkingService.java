package com.joaoneto.parkinglot.services;

import com.joaoneto.parkinglot.entities.Client;
import com.joaoneto.parkinglot.entities.ClientSpot;
import com.joaoneto.parkinglot.entities.Spot;
import com.joaoneto.parkinglot.entities.enums.SpotStatus;
import com.joaoneto.parkinglot.utils.ParkingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.joaoneto.parkinglot.utils.ParkingUtil.generateReceipt;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ClientSpotService clientSpotService;
    private final ClientService clientService;
    private final SpotService spotService;

    public ClientSpot checkIn(ClientSpot clientSpot) {
        Client client = clientService.findByCpf(clientSpot.getClient().getCpf());
        clientSpot.setClient(client);

        Spot spot = spotService.findByFreeSpot();
        spot.setSpotStatus(SpotStatus.OCCUPIED);
        clientSpot.setSpot(spot);

        clientSpot.setEntryTime(LocalDateTime.now());

        clientSpot.setReceipt(generateReceipt());

        return clientSpotService.createClientSpot(clientSpot);
    }

    @Transactional
    public ClientSpot checkOut(String receipt) {
        ClientSpot clientSpotToCheckOut = clientSpotService.findByReceiptAndExitTimeIsNull(receipt);

        LocalDateTime exitTime = LocalDateTime.now();
        BigDecimal price = ParkingUtil.calculatePrice(clientSpotToCheckOut.getEntryTime(), exitTime);
        clientSpotToCheckOut.setExitTime(exitTime);
        clientSpotToCheckOut.setPrice(price);
        long totalTimesParking = clientSpotService.countByClientCpf(clientSpotToCheckOut.getClient().getCpf());
        BigDecimal discount = ParkingUtil.CalculateDiscount(price, totalTimesParking);
        clientSpotToCheckOut.setDiscount(discount);
        clientSpotToCheckOut.getSpot().setSpotStatus(SpotStatus.FREE);

        return clientSpotService.createClientSpot(clientSpotToCheckOut);
    }
}
