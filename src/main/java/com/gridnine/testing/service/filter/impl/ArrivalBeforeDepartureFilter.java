package com.gridnine.testing.service.filter.impl;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.filter.base.FlightFilter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Фильтр, исключающий перелёты с некорректными сегментами, где дата прилёта раньше даты вылета
 */
public class ArrivalBeforeDepartureFilter implements FlightFilter {

    public ArrivalBeforeDepartureFilter() {
    }

    /**
     * Метод фильтрации, исключающий перелеты, в сегментах которых вылет происходит позднее прилета
     *
     * @param flights список полетов
     * @return отфильтрованный список полетов
     */
    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.segments().stream()
                        .allMatch(segment -> segment.arrivalDate().isAfter(segment.departureDate())))
                .collect(Collectors.toList());
    }
}
