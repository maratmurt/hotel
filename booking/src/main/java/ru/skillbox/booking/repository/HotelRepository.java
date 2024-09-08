package ru.skillbox.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.booking.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
