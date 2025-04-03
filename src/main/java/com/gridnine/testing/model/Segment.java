package com.gridnine.testing.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Сегмент перелета
 * @param departureDate время отправления
 * @param arrivalDate время прибытия
 */
public record Segment(LocalDateTime departureDate, LocalDateTime arrivalDate) {

    /**
     * Компактный конструктор класса {@link Segment}
     * @param departureDate время отправления, не может быть null
     * @param arrivalDate время прибытия, не может быть null
     * @throws NullPointerException если любой из параметров равен null
     */
    public Segment {
        Objects.requireNonNull(departureDate);
        Objects.requireNonNull(arrivalDate);
    }

    /**
     * Возвращает строковое представление сегмента перелета в формате:
     * "[yyyy-MM-dd'T'HH:mm|yyyy-MM-dd'T'HH:mm]", где первая дата - отправление,
     * вторая - прибытие.
     *
     * @return строковое представление сегмента в формате "[departure|arrival]"
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return '[' + departureDate.format(formatter) + '|' + arrivalDate.format(formatter) + ']';
    }
}
