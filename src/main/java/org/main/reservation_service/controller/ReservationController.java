package org.main.reservation_service.controller;

import org.main.reservation_service.DTO.CreateReservationRequest;
import org.main.reservation_service.DTO.ReservationResponseDTO;
import org.main.reservation_service.entity.Reservation;
import org.main.reservation_service.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService _reservationService;

    public ReservationController(ReservationService reservationService){
        this._reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> create(@RequestBody CreateReservationRequest request) {
        Reservation res =  _reservationService.createReservation(
                request.resourceId(),
                request.userId(),
                request.start(),
                request.end()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(_reservationService.toDTO(res));
    }

    @PostMapping("/{id}/cancel")
    public void cancel(@PathVariable UUID id) {
        _reservationService.cancelReservation(id);
    }

}
