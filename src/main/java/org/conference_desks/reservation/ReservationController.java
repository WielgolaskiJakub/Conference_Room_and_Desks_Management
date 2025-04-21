package org.conference_desks.reservation;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/reservation")
@CrossOrigin(origins = "http://localhost:5173")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponse> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable long id) {
        ReservationResponse reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
        ReservationResponse reservation = reservationService.createReservation(reservationRequest);
        return ResponseEntity.created(URI.
                        create("/api/v1/reservation/" + reservation.getId()))
                .body(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservationFully(@Valid @PathVariable long id,
                                                                      @RequestBody ReservationRequest reservationRequest) {
        ReservationResponse reservation = reservationService.updateReservationFully(reservationRequest, id);
        return ResponseEntity.ok(reservation);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservationPartially(@Valid @PathVariable long id
            , @RequestBody ReservationPatchRequest reservationPatchRequest) {
        ReservationResponse reservation = reservationService.updateReservationPartially(reservationPatchRequest, id);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservationService.deleteReservationById(id);
        return ResponseEntity.noContent().build();
    }
}
