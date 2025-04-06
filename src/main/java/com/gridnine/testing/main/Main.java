package com.gridnine.testing.main;

import com.gridnine.testing.service.FlightFilterService;

public class Main {
    public static void main(String[] args) {
        FlightFilterService.printFilteredTestFlights();

        String filtersFile = "filters.txt";
        String flightsFile = "flights.txt";
        FlightFilterService.printFilteredFlights(filtersFile, flightsFile);
    }
}


