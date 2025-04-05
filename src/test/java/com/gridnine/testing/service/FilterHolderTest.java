package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.factory.FilterFactory;
import com.gridnine.testing.service.factory.FlightBuilder;
import com.gridnine.testing.service.filter.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.service.filter.DepartureBeforeNowFilter;
import com.gridnine.testing.service.filter.FlightFilter;
import com.gridnine.testing.service.filter.TotalHoursTransfersFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FilterHolderTest {
    private FiltersHolder filtersHolder;

    @BeforeEach
    void setUp() {
        filtersHolder = FiltersHolder.getInstance();
        filtersHolder.clearFilters();
    }

    @Test
    void testAddFilter_valid() {
        String filterName = "ArrivalBeforeDepartureFilter";
        String param = "";
        int filtersCount = 1;

        FlightFilter filter = FilterFactory.getFilterByName(filterName, param);
        filtersHolder.addFilter(filter);
        Set<FlightFilter> filterSet = filtersHolder.getFilters();
        FlightFilter firstFilter = filterSet.iterator().next();

        assertEquals(filtersCount, filterSet.size());
        assertTrue(firstFilter instanceof ArrivalBeforeDepartureFilter);
    }

    @Test
    void testRemoveFilter_existingFilter() {
        String filterName = "DepartureBeforeNowFilter";
        String param = "";

        FlightFilter filter = FilterFactory.getFilterByName(filterName, param);
        filtersHolder.addFilter(filter);
        filtersHolder.removeFilter(filter);
        Set<FlightFilter> filterSet = filtersHolder.getFilters();

        assertTrue(filterSet.isEmpty());
    }

    @Test
    void testAddTestFilters_shouldAddThreeFilters() {
        int filtersCount = 3;

        filtersHolder.addTestFilters();
        List<FlightFilter> filterList = new ArrayList<>(filtersHolder.getFilters());
        FlightFilter firstFilter = filterList.get(0);
        FlightFilter secondFilter = filterList.get(1);
        FlightFilter thirdFilter = filterList.get(2);

        assertEquals(filtersCount, filterList.size());
        assertTrue(firstFilter instanceof DepartureBeforeNowFilter);
        assertTrue(secondFilter instanceof ArrivalBeforeDepartureFilter);
        assertTrue(thirdFilter instanceof TotalHoursTransfersFilter);
    }

    @Test
    void testApplyFilters_withTestFilters_shouldReturnFilteredFlights() {
        List<Flight> flights = FlightBuilder.createFlights();
        int filteredFlightCount = 2;

        filtersHolder.addTestFilters();
        List<Flight> filteredFlights = filtersHolder.applyFilters(flights);

        assertEquals(filteredFlightCount, filteredFlights.size());
    }

    @Test
    void testGetFilters_brokenStream() {
        @SuppressWarnings("resource") InputStream brokenStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Simulated IO exception");
            }
        };

        assertDoesNotThrow(() -> filtersHolder.getFiltersFromStream(brokenStream));
    }

    @Test
    void testGetFiltersFromStream_emptyFile() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("filter/test-filter-empty.txt");

        filtersHolder.getFiltersFromStream(inputStream);
        Set<FlightFilter> filters = filtersHolder.getFilters();

        assertTrue(filters.isEmpty());
    }

    @Test
    void testGetFiltersFromStream_validWithoutParams() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("filter/test-filter-without-params.txt");
        int filtersCount = 1;

        filtersHolder.getFiltersFromStream(inputStream);
        Set<FlightFilter> filters = filtersHolder.getFilters();
        FlightFilter filter = filters.iterator().next();

        assertEquals(filtersCount, filters.size());
        assertTrue(filter instanceof DepartureBeforeNowFilter);
    }

    @Test
    void testGetFiltersFromStream_validWithParams() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("filter/test-filter-with-params.txt");
        int filtersCount = 1;

        filtersHolder.getFiltersFromStream(inputStream);
        Set<FlightFilter> filters = filtersHolder.getFilters();
        FlightFilter filter = filters.iterator().next();

        assertEquals(filtersCount, filters.size());
        assertTrue(filter instanceof TotalHoursTransfersFilter);
    }

    @Test
    void testGetFiltersFromStream_invalidFilter_shouldNotAdd() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("filter/test-filter-wrong-format.txt");
        int filtersCount = 0;

        filtersHolder.getFiltersFromStream(inputStream);
        Set<FlightFilter> filters = filtersHolder.getFilters();

        assertEquals(filtersCount, filters.size());
    }
}
