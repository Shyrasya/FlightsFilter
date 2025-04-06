package com.gridnine.testing.service.filter.base;

import com.gridnine.testing.model.Flight;

import java.util.List;

/**
 * Интерфейс для фильтрации списка перелетов.
 * Каждый фильтр должен реализовывать метод {@code filter} для применения фильтрации к списку перелетов
 */
public interface FlightFilter {

    /**
     * Применяет фильтрацию к списку перелетов
     *
     * @param flights список перелетов, который нужно отфильтровать
     * @return новый список перелетов, соответствующих условиям фильтра
     */
    List<Flight> filter(List<Flight> flights);
}
