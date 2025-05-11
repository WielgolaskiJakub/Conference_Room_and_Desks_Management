package org.conference_desks.reservation;

import jakarta.validation.Valid;
import org.conference_desks.security.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ReservationResponse> getAllReservations() {
        return reservationService.getAllReservations();
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/my")
    public ResponseEntity<List<ReservationResponse>> getMyReservations() {
        Long userId = SecurityUtils.getCurrentUserId();
        List<ReservationResponse> reservations = reservationService.getReservationsByUserId(userId);
        return ResponseEntity.ok(reservations);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable long id) {
        ReservationResponse reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
        ReservationResponse reservation = reservationService.createReservation(reservationRequest);
        return ResponseEntity.created(URI.
                        create("/api/v1/reservation/" + reservation.getId()))
                .body(reservation);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservationFully(@Valid @PathVariable long id,
                                                                      @RequestBody ReservationRequest reservationRequest) {
        ReservationResponse reservation = reservationService.updateReservationFully(reservationRequest, id);
        return ResponseEntity.ok(reservation);
    }
    @PreAuthorize("hasRole('ADMIN') or @reservationSecurity.isOwner(#id)")
    @PatchMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservationPartially(@Valid @PathVariable long id
            , @RequestBody ReservationPatchRequest reservationPatchRequest) {
        ReservationResponse reservation = reservationService.updateReservationPartially(reservationPatchRequest, id);
        return ResponseEntity.ok(reservation);
    }

    @PreAuthorize("hasRole('ADMIN') or @reservationSecurity.isOwner(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservationService.deleteReservationById(id);
        return ResponseEntity.noContent().build();
    }
}
