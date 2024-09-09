package ru.skillbox.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.booking.dto.reservation.ReservationRequest;
import ru.skillbox.booking.dto.reservation.ReservationResponse;
import ru.skillbox.booking.mapper.ReservationMapper;
import ru.skillbox.booking.model.Reservation;
import ru.skillbox.booking.model.Room;
import ru.skillbox.booking.model.User;
import ru.skillbox.booking.service.ReservationService;
import ru.skillbox.booking.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    private final ReservationMapper reservationMapper;

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationResponse>> findAll(@RequestParam Integer page,
                                                             @RequestParam Integer size) {
        return ResponseEntity.ok(reservationService.findAll(page, size)
                .stream()
                .map(reservationMapper::toResponse)
                .toList()
        );
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest request,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        Reservation reservation = reservationMapper.toEntity(request);

        Room room = new Room();
        room.setId(request.roomId());
        reservation.setRoom(room);

        User user = userService.findByName(userDetails.getUsername());
        reservation.setUser(user);

        return ResponseEntity.ok(reservationMapper.toResponse(reservationService.create(reservation)));
    }

}
