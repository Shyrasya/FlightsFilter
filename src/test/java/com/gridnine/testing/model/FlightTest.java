package com.gridnine.testing.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightTest {

    @Test
    void testFlightToString() {
        String expectedString = "[2025-04-13T12:11|2025-04-13T13:13] [2025-04-13T14:15|2025-04-13T15:17]";
        LocalDateTime departureDateFirst = LocalDateTime.of(2025, 4, 13, 12, 11);
        LocalDateTime arrivalDateFirst = LocalDateTime.of(2025, 4, 13, 13, 13);
        LocalDateTime departureDateSecond = LocalDateTime.of(2025, 4, 13, 14, 15);
        LocalDateTime arrivalDateSecond = LocalDateTime.of(2025, 4, 13, 15, 17);
        Segment firstSegment = new Segment(departureDateFirst, arrivalDateFirst);
        Segment secondSegment = new Segment(departureDateSecond, arrivalDateSecond);
        List<Segment> segments = Arrays.asList(firstSegment, secondSegment);
        Flight flight = new Flight(segments);

        assertEquals(expectedString, flight.toString());
    }
}
