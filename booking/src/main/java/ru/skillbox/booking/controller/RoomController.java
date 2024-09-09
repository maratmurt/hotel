package ru.skillbox.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.booking.dto.room.RoomFilter;
import ru.skillbox.booking.dto.room.RoomRequest;
import ru.skillbox.booking.dto.room.RoomResponse;
import ru.skillbox.booking.mapper.RoomMapper;
import ru.skillbox.booking.model.Room;
import ru.skillbox.booking.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    private final RoomMapper roomMapper;

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(roomMapper.toResponse(roomService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponse> create(@RequestBody RoomRequest request) {
        Room room = roomService.create(roomMapper.toEntity(request));
        return ResponseEntity.ok(roomMapper.toResponse(room));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id,
                                               @RequestBody RoomRequest request) {
        Room room = roomMapper.toEntity(request);
        room.setId(id);
        return ResponseEntity.ok(roomMapper.toResponse(roomService.update(room)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<RoomResponse>> findAllWithFilter(@RequestParam Integer page,
                                                                @RequestParam Integer size,
                                                                @RequestBody RoomFilter filter) {
        Specification<Room> specification = roomMapper.filterToSpecification(filter);
        List<RoomResponse> responseList = roomService.findAllWithFilter(page, size, specification)
                .stream()
                .map(roomMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseList);
    }

}
