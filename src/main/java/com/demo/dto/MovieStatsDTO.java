package com.demo.dto;

import com.demo.model.Movie;

public record MovieStatsDTO(
        Movie movie,
        Long reviewCount,
        Double reviewAverageRating,
        Long ticketsSold,
        Double revenue
) {
}
