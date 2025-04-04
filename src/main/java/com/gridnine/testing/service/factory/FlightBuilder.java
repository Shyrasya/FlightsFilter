package com.gridnine.testing.service.factory;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Класс для создания списка объектов {@link Flight} с различными наборами сегментов.
 * Содержит методы для создания перелетов как с заранее заданными тестовыми датами, так и из данных, полученных из InputStream.
 */
public class FlightBuilder {
    public static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        try {
            return Arrays.asList(
                    createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                    createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                            threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                    createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                    createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                    createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                            threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                    createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                            threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                            threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Создает объект {@link Flight} из переданных дат.
     * Пары дат представляют собой сегменты (время отправления и время прибытия).
     *
     * @param dates массив дат для создания перелета
     * @return созданный объект {@link Flight}
     * @throws IllegalArgumentException если количество дат нечетное
     */
    private static Flight createFlight(final LocalDateTime... dates) {
        if (isOddDatesNumber(dates.length)) {
            throw new IllegalArgumentException("you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length) - 1; i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }

    /**
     * Проверяет, является ли количество дат нечетным
     *
     * @param length длина массива дат
     * @return true, если количество дат нечетное, иначе false
     */
    private static boolean isOddDatesNumber(int length) {
        return length % 2 != 0;
    }

    /**
     * Создает список перелетов из данных, полученных из InputStream.
     * Каждый перелет создается на основе строки, содержащей даты отправления и прибытия.
     *
     * @param inputStream поток ввода для чтения данных о перелетах
     * @return список созданных перелетов
     */
    public static List<Flight> createFlights(InputStream inputStream) {
        List<com.gridnine.testing.model.Flight> flights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                com.gridnine.testing.model.Flight flight = parseFlight(line);
                if (flight != null) {
                    flights.add(flight);
                } else {
                    System.out.println("Error processing line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return flights;
    }

    /**
     * Парсит строку, представляющую перелет, и создает объект {@link Flight}.
     * Строка должна содержать четное количество дат (время отправления и время прибытия для каждого сегмента).
     *
     * @param line строка, представляющая перелет
     * @return объект {@link Flight}, если строка валидна, иначе null
     */
    private static Flight parseFlight(String line) {
        if (line == null || line.trim().isEmpty()) {
            System.out.println("Empty line, cannot create flight!");
            return null;
        }

        String[] parts = line.trim().split("\\s+");
        if (isOddDatesNumber(parts.length)) {
            System.out.println("Odd number of dates in flight: " + line);
            return null;
        }
        List<LocalDateTime> dates = new ArrayList<>();
        for (String part : parts) {
            LocalDateTime partTime = parseTime(part.trim());
            if (partTime == null) {
                return null;
            }
            dates.add(partTime);
        }
        return FlightBuilder.createFlight(dates.toArray(new LocalDateTime[0]));
    }

    /**
     * Парсит строку в формате времени и возвращает объект LocalDateTime.
     *
     * @param timePart строка, представляющая время
     * @return объект {@link LocalDateTime}, если строка валидна, иначе null
     */
    private static LocalDateTime parseTime(String timePart) {
        try {
            return LocalDateTime.parse(timePart);
        } catch (DateTimeParseException e) {
            System.out.println("Time parsing error: " + timePart);
            return null;
        }
    }
}
