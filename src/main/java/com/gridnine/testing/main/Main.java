package com.gridnine.testing.main;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FiltersHolder;
import com.gridnine.testing.service.factory.FlightBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        FiltersHolder filtersHolder = FiltersHolder.getInstance();
        filtersHolder.addTestFilters();

        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println("Test flights list:");
        flights.forEach(System.out::println);

        List<Flight> filteredFlights = filtersHolder.applyFilters(flights);
        System.out.println("\nFiltered flights:");
        filteredFlights.forEach(System.out::println);
    }
}

