package ru.skillbox.booking.model;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class RoomSpecification {

    public static Specification<Room> byIds(List<Long> ids) {
        return (root, query, builder) -> {
            if (ids == null || ids.isEmpty()) {
                return builder.conjunction();
            }

            return root.get("id").in(ids);
        };
    }

    public static Specification<Room> byNames(List<String> names) {
        return (root, query, builder) -> {
            if (names == null || names.isEmpty()) {
                return builder.conjunction();
            }

            return root.get("name").in(names);
        };
    }

    public static Specification<Room> byPrice(Double minPrice, Double maxPrice) {
        final double min = minPrice == null ? 0D : minPrice;
        final double max = maxPrice == null ? Double.MAX_VALUE : maxPrice;

        return (root, query, builder) -> builder.between(root.get("price"), min, max);
    }

    public static Specification<Room> byCapacity(Integer capacity) {
        return (root, query, builder) -> {
            if (capacity == null) {
                return builder.conjunction();
            }

            return builder.equal(root.get("capacity"), capacity);
        };
    }

    public static Specification<Room> byDates(LocalDate checkinDate, LocalDate checkoutDate) {
        return (root, query, builder) -> {
            if (checkinDate == null || checkoutDate == null) {
                return builder.conjunction();
            }
            List<LocalDate> requestedDates = checkinDate.datesUntil(checkoutDate).toList();

            //todo: check occupied dates
            return builder.conjunction();
        };
    }

    public static Specification<Room> byHotelIds(List<Long> hotelIds) {
        return (root, query, builder) -> {
            if (hotelIds == null || hotelIds.isEmpty()) {
                return builder.conjunction();
            }

            return root.get("hotel").get("id").in(hotelIds);
        };
    }

}
