package com.gridnine.testing.service.factory;

import com.gridnine.testing.model.Flight;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlightBuilderTest {

    @Test
    void testCreateFlights_withOddDatesNumber() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("flight/test-flight-odd-numbers.txt");
        int sizeList = 0;

        List<Flight> flights = FlightBuilder.createFlights(inputStream);

        assertNotNull(flights);
        assertEquals(sizeList, flights.size());
    }

    @Test
    void testCreateFlights_emptyFileLine() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("flight/test-flight-empty-line.txt");
        int sizeList = 0;

        List<Flight> flights = FlightBuilder.createFlights(inputStream);

        assertNotNull(flights);
        assertEquals(sizeList, flights.size());
    }

    @Test
    void testCreateFlights_validFlights() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("flight/test-flight-correct-dates.txt");
        int sizeList = 2;

        List<Flight> flights = FlightBuilder.createFlights(inputStream);

        assertNotNull(flights);
        assertEquals(sizeList, flights.size());
    }

    @Test
    void testCreateFlights_wrongFormat() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("flight/test-flight-wrong-format.txt");
        int sizeList = 0;

        List<Flight> flights = FlightBuilder.createFlights(inputStream);

        assertNotNull(flights);
        assertEquals(sizeList, flights.size());
    }

    @Test
    void testCreateFlights_brokenStream() {
        @SuppressWarnings("resource") InputStream brokenStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Simulated IO exception");
            }
        };

        assertDoesNotThrow(() -> FlightBuilder.createFlights(brokenStream));
    }
}
