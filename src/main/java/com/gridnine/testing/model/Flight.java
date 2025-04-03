package com.gridnine.testing.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Перелет - маршрут, который может состоять из одного или более сегментов
 * @param segments список сегментов перелета
 */
public record Flight(List<Segment> segments) {

    /**
     * Возвращает строковое представление перелета в виде отдельных сегментов, разделенных пробелом
     * @return строковое представление перелета
     */
    @Override
    public String toString(){
        return segments.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}
