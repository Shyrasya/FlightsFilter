package com.gridnine.testing.service.filter;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Фильтр, исключающий перелеты, в которых пересадки между сегментами длятся более чем указанное количество часов
 */
public class TotalHoursTransfersFilter implements FlightFilter {

    /**
     * Время пересадки по умолчанию (в часах)
     */
    private static final double TEST_TRANSFER_HOURS = 2;

    /**
     * Общее допустимое время пересадки между сегментами в полете
     */
    private double boundTransferHours;

    /**
     * Параметризированный конструктор класса {@link TotalHoursTransfersFilter}.
     * Создает фильтр с заданным временем пересадки между сегментами
     *
     * @param boundTransferHours общее допустимое время пересадки между сегментами в полете
     */
    public TotalHoursTransfersFilter(double boundTransferHours) {
        this.boundTransferHours = boundTransferHours;
    }

    /**
     * Базовый конструктор, устанавливающий время пересадки по умолчанию
     */
    public TotalHoursTransfersFilter() {
        this.boundTransferHours = TEST_TRANSFER_HOURS;
    }

    /**
     * Метод фильтрации, исключающий перелеты, в которых пересадки между сегментами в общем количестве занимают более чем указанное время (в часах)
     *
     * @param flights список полетов
     * @return отфильтрованный список полетов
     */
    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> {
                    List<Segment> segments = flight.segments();
                    double totalTransferTime = 0;
                    for (int i = 0; i < segments.size() - 1; i++) {
                        totalTransferTime += Duration.between(segments.get(i).arrivalDate(),
                                segments.get(i + 1).departureDate()).toHours();
                    }
                    return totalTransferTime <= boundTransferHours;
                })
                .collect(Collectors.toList());
    }

    /**
     * Получает общее допустимое время пересадки между сегментами
     *
     * @return допустимое время пересадки между сегментами
     */
    public double getBoundTransferHours() {
        return boundTransferHours;
    }

    /**
     * Устанавливает новое значение для допустимого времени пересадки между сегментами
     *
     * @param boundTransferHours новое значение времени пересадки
     */
    public void setBoundTransferHours(double boundTransferHours) {
        this.boundTransferHours = boundTransferHours;
    }
}
