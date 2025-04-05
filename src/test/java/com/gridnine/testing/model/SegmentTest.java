package com.gridnine.testing.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SegmentTest {

    @Test
    void testSegmentToString() {
        String expectedString = "[2025-04-08T11:15|2025-04-08T13:13]";
        LocalDateTime departureDate = LocalDateTime.of(2025, 4, 8, 11, 15);
        LocalDateTime arrivalDate = LocalDateTime.of(2025, 4, 8, 13, 13);
        Segment segment = new Segment(departureDate, arrivalDate);

        assertEquals(expectedString, segment.toString());
    }
}
