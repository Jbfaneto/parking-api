package com.joaoneto.parkinglot.services;

import com.joaoneto.parkinglot.entities.Client;
import com.joaoneto.parkinglot.entities.ClientSpot;
import com.joaoneto.parkinglot.entities.Spot;
import com.joaoneto.parkinglot.entities.enums.SpotStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
