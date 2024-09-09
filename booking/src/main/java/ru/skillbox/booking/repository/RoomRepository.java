package ru.skillbox.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.booking.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
