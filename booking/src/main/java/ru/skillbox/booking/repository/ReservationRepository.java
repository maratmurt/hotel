package ru.skillbox.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.booking.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
