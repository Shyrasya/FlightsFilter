package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.factory.FilterFactory;
import com.gridnine.testing.service.filter.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.service.filter.DepartureBeforeNowFilter;
import com.gridnine.testing.service.filter.FlightFilter;
import com.gridnine.testing.service.filter.TotalHoursTransfersFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс для управления фильтрами, которые применяются к списку перелетов.
 * Позволяет добавлять, удалять и применять фильтры, а также загружать фильтры из файла.
 */
public class FiltersHolder {

    /**
     * Экземпляр FiltersHolder (Singleton)
     */
    private static final FiltersHolder INSTANCE = new FiltersHolder();

    /**
     * Набор фильтров для применения
     */
    private final Set<FlightFilter> filters = new LinkedHashSet<>();

    /**
     * Конструктор класса FiltersHolder (приватный для реализации Singleton)
     */
    private FiltersHolder() {
    }

    /**
     * Получает единственный экземпляр FiltersHolder
     *
     * @return единственный экземпляр FiltersHolder
     */
    public static FiltersHolder getInstance() {
        return INSTANCE;
    }

    /**
     * Очищает все добавленные фильтры.
     */
    public void clearFilters() {
        filters.clear();
    }

    /**
     * Добавляет фильтр в коллекцию фильтров
     *
     * @param filter фильтр, который нужно добавить
     */
    public void addFilter(FlightFilter filter) {
        filters.add(filter);
    }

    /**
     * Удаляет фильтр из коллекции фильтров
     *
     * @param filter фильтр, который нужно удалить
     */
    public void removeFilter(FlightFilter filter) {
        filters.remove(filter);
    }

    /**
     * Добавляет тестовые фильтры в коллекцию
     */
    public void addTestFilters() {
        addFilter(new DepartureBeforeNowFilter());
        addFilter(new ArrivalBeforeDepartureFilter());
        addFilter(new TotalHoursTransfersFilter());
    }

    /**
     * Применяет все фильтры к списку перелетов
     *
     * @param flights список перелетов, к которому нужно применить фильтры
     * @return отфильтрованный список перелетов
     */
    public List<Flight> applyFilters(List<Flight> flights) {
        List<Flight> filteredFlights = flights;
        for (FlightFilter filter : filters) {
            filteredFlights = filter.filter(filteredFlights);
        }
        return filteredFlights;
    }

    /**
     * Загружает фильтры из файла и добавляет их в коллекцию.
     * Формат строки в файле: имя фильтра (и необязательный параметр).
     * Строки, начинающиеся с "#" или пустые, игнорируются.
     *
     * @param inputStream поток ввода для чтения фильтров из файла
     */
    public void getFiltersFromStream(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split("\\s+", 2);
                String filterName = parts[0];
                String param = (parts.length > 1) ? parts[1] : null;
                FlightFilter filter = FilterFactory.getFilterByName(filterName, param);
                if (filter != null) {
                    addFilter(filter);
                } else {
                    System.out.println("Filter '" + filterName + "' not found or could not be added!");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Получение списка фильтров, установленных в класс {@link FiltersHolder}
     *
     * @return список уникальных фильтров
     */
    public Set<FlightFilter> getFilters() {
        return filters;
    }
}
