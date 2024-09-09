package ru.skillbox.booking.model;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class HotelSpecification {

    public static Specification<Hotel> byIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return (root, query, builder) -> builder.conjunction();
        }

        return (root, query, builder) -> root.get("id").in(ids);
    }

    public static Specification<Hotel> byNames(List<String> names) {
        if (names == null || names.isEmpty()) {
            return (root, query, builder) -> builder.conjunction();
        }

        return (root, query, builder) -> root.get("name").in(names);
    }

    public static Specification<Hotel> byTitles(List<String> titles) {
        if (titles == null || titles.isEmpty()) {
            return (root, query, builder) -> builder.conjunction();
        }

        return (root, query, builder) -> root.get("title").in(titles);
    }

    public static Specification<Hotel> byCities(List<String> cities) {
        if (cities == null || cities.isEmpty()) {
            return (root, query, builder) -> builder.conjunction();
        }

        return (root, query, builder) -> root.get("city").in(cities);
    }

    public static Specification<Hotel> byAddresses(List<String> addresses) {
        if (addresses == null || addresses.isEmpty()) {
            return (root, query, builder) -> builder.conjunction();
        }

        return (root, query, builder) -> root.get("address").in(addresses);
    }

    public static Specification<Hotel> byDistance(Double minDistance, Double maxDistance) {
        Double finalMinDistance = minDistance == null ? 0D : minDistance;
        Double finalMaxDistance = maxDistance == null ? Double.MAX_VALUE : maxDistance;

        return (root, query, builder) ->
                builder.between(root.get("distance"), finalMinDistance, finalMaxDistance);
    }

    public static Specification<Hotel> byRating(Double minRating, Double maxRating) {
        Double finalMinRating = minRating == null ? 0D : minRating;
        Double finalMaxRating = maxRating == null ? 5D : maxRating;

        return (root, query, builder) ->
                builder.between(root.get("rating"), finalMinRating, finalMaxRating);
    }

    public static Specification<Hotel> byRatingsCount(Integer minRatingsCount, Integer maxRatingsCount) {
        int finalMinRating = minRatingsCount == null ? 0 : minRatingsCount;
        int finalMaxRating = maxRatingsCount == null ? Integer.MAX_VALUE : maxRatingsCount;

        return (root, query, builder) ->
                builder.between(root.get("ratingsCount"), finalMinRating, finalMaxRating);
    }

}
