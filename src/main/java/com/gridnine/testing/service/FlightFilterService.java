package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.factory.FlightBuilder;
import com.gridnine.testing.service.filter.base.FlightFilter;
import com.gridnine.testing.service.filter.manager.FiltersHolder;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class FlightFilterService {

    /**
     * Печать процесса фильтрации в консоль
     *
     * @param filtersFile название файла со списком фильтров
     * @param flightsFile название файла со списком полетов
     */
    public static void printFilteredFlights(String filtersFile, String flightsFile) {
        FiltersHolder filtersHolder = FiltersHolder.getInstance();
        filtersHolder.clearFilters();

        try (
                InputStream inputFiltersStream = FlightFilterService.class.getClassLoader().getResourceAsStream(filtersFile);
                InputStream inputFlightsStream = FlightFilterService.class.getClassLoader().getResourceAsStream(flightsFile)
        ) {
            if (inputFiltersStream == null) {
                throw new IllegalArgumentException("File with filters is missing: " + filtersFile);
            }

            if (inputFlightsStream == null) {
                throw new IllegalArgumentException("File with flights is missing: " + flightsFile);
            }

            filtersHolder.getFiltersFromStream(inputFiltersStream);

            Set<FlightFilter> filteredFlight = filtersHolder.getFilters();
            printFilters(filteredFlight);

            List<Flight> flights = FlightBuilder.createFlights(inputFlightsStream);
            printFlights("Test flights list:", flights);

            List<Flight> filteredFlights = filtersHolder.applyFilters(flights);
            printFlights("Filtered flights:", filteredFlights);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    /**
     * Печать процесса фильтрации тестового набора в консоль
     */
    public static void printFilteredTestFlights() {
        FiltersHolder filtersHolder = FiltersHolder.getInstance();
        filtersHolder.clearFilters();
        filtersHolder.addTestFilters();

        Set<FlightFilter> filteredFlight = filtersHolder.getFilters();
        printFilters(filteredFlight);

        List<Flight> flights = FlightBuilder.createFlights();
        printFlights("Test flights list:", flights);

        List<Flight> filteredFlights = filtersHolder.applyFilters(flights);
        printFlights("Filtered flights:", filteredFlights);
    }

    /**
     * Возвращает список полетов, прошедших фильтрацию на основе данных из указанных файлов
     *
     * @param filtersFile название файла со списком фильтров
     * @param flightsFile название файла со списком полетов
     * @return Отфильтрованный список полетов
     */
    public static List<Flight> getFilteredFlights(String filtersFile, String flightsFile) {
        FiltersHolder filtersHolder = FiltersHolder.getInstance();
        filtersHolder.clearFilters();
        try (
                InputStream inputFiltersStream = FlightFilterService.class.getClassLoader().getResourceAsStream(filtersFile);
                InputStream inputFlightsStream = FlightFilterService.class.getClassLoader().getResourceAsStream(flightsFile)
        ) {
            if (inputFiltersStream == null) {
                throw new IllegalArgumentException("File with filters is missing: " + filtersFile);
            }

            if (inputFlightsStream == null) {
                throw new IllegalArgumentException("File with flights is missing: " + flightsFile);
            }
            filtersHolder.getFiltersFromStream(inputFiltersStream);

            List<Flight> flights = FlightBuilder.createFlights(inputFlightsStream);
            return filtersHolder.applyFilters(flights);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Возвращает список полетов, отфильтрованных на основе тестовых данных
     *
     * @return Отфильтрованный список полетов из тестового набора
     */
    public static List<Flight> getFilteredTestFlights() {
        FiltersHolder filtersHolder = FiltersHolder.getInstance();
        filtersHolder.clearFilters();
        filtersHolder.addTestFilters();
        List<Flight> flights = FlightBuilder.createFlights();
        return filtersHolder.applyFilters(flights);
    }

    /**
     * Печать списка фильтров в консоль
     *
     * @param filters Список фильтров
     */
    private static void printFilters(Set<FlightFilter> filters) {
        System.out.println("\nFilters set:");
        filters.forEach(filter -> System.out.println(filter.getClass().getSimpleName()));
    }

    /**
     * Печать списка полетов в консоль
     *
     * @param title   Заголовок списка
     * @param flights Список полетов
     */
    private static void printFlights(String title, List<Flight> flights) {
        System.out.println("\n" + title);
        flights.forEach(System.out::println);
    }
}
