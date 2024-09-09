package ru.skillbox.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.booking.dto.hotel.HotelListResponse;
import ru.skillbox.booking.dto.hotel.HotelRequest;
import ru.skillbox.booking.dto.hotel.HotelResponse;
import ru.skillbox.booking.mapper.HotelMapper;
import ru.skillbox.booking.model.Hotel;
import ru.skillbox.booking.service.HotelService;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    private final HotelMapper hotelMapper;

    @GetMapping
    public ResponseEntity<HotelListResponse> findAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(hotelMapper.toListResponse(hotelService.findAll(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelMapper.toResponse(hotelService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HotelResponse> create(@RequestBody HotelRequest request) {
        Hotel hotel = hotelService.create(hotelMapper.toEntity(request));
        return ResponseEntity.ok(hotelMapper.toResponse(hotel));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HotelResponse> update(@PathVariable Long id, @RequestBody HotelRequest request) {
        Hotel hotel = hotelMapper.toEntity(request);
        hotel.setId(id);
        return ResponseEntity.ok(hotelMapper.toResponse(hotelService.update(hotel)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hotelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/rate")
    public ResponseEntity<HotelResponse> addRating(@PathVariable Long id, @RequestParam Integer rating) {
        return ResponseEntity.ok(hotelMapper.toResponse(hotelService.addRating(id, rating)));
    }

}
