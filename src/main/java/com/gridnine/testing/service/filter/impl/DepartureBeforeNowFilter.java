package com.gridnine.testing.service.filter.impl;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.filter.base.FlightFilter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Фильтр, исключающий перелеты, в сегментах которых вылет состоялся ранее текущего времени
 */
public class DepartureBeforeNowFilter implements FlightFilter {

    /**
     * Метод фильтрации, исключающий перелеты, в сегментах которых вылет начался в прошлом
     *
     * @param flights список полетов
     * @return отфильтрованный список полетов
     */
    @Override
    public List<Flight> filter(List<Flight> flights) {
        LocalDateTime now = LocalDateTime.now();
        return flights.stream()
                .filter(flight -> flight.segments().stream()
                        .allMatch(segment -> segment.departureDate().isAfter(now)))
                .collect(Collectors.toList());
    }
}
