package com.gridnine.testing.service.factory;

import com.gridnine.testing.service.filter.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.service.filter.DepartureBeforeNowFilter;
import com.gridnine.testing.service.filter.FlightFilter;
import com.gridnine.testing.service.filter.TotalHoursTransfersFilter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Фабрика для создания фильтров перелетов.
 * Хранит два типа фильтров: фильтры без параметров и фильтры с параметрами.
 * Обеспечивает получение фильтра по имени и параметру.
 */
public class FilterFactory {

    /**
     * Хранит фильтры без параметров, где ключ — имя фильтра, значение — поставщик фильтра
     */
    private static final Map<String, Supplier<FlightFilter>> filtersWithoutParams = new HashMap<>();

    /**
     * Хранит фильтры с параметрами, где ключ — имя фильтра, значение — функция для создания фильтра с параметром
     */
    private static final Map<String, Function<String, FlightFilter>> filtersWithParams = new HashMap<>();

    static {
        filtersWithoutParams.put("ArrivalBeforeDepartureFilter", ArrivalBeforeDepartureFilter::new);
        filtersWithoutParams.put("DepartureBeforeNowFilter", DepartureBeforeNowFilter::new);

        filtersWithParams.put("TotalHoursTransfersFilter", param -> {
            if (param != null && !param.isEmpty()) {
                return new TotalHoursTransfersFilter(Double.parseDouble(param));
            }
            return new TotalHoursTransfersFilter();
        });
    }

    /**
     * Возвращает фильтр по имени и параметру.
     * Если фильтр с таким именем существует и поддерживает параметры, применяет параметр.
     * Если фильтр без параметров, просто создает его.
     *
     * @param filterName имя фильтра
     * @param param      параметр для фильтра (если есть)
     * @return созданный фильтр или {@code null}, если фильтр не найден или не может быть создан
     */
    public static FlightFilter getFilterByName(String filterName, String param) {
        if (filtersWithParams.containsKey(filterName)) {
            try {
                return filtersWithParams.get(filterName).apply(param);
            } catch (Exception e) {
                return null;
            }
        } else if (filtersWithoutParams.containsKey(filterName)) {
            return filtersWithoutParams.get(filterName).get();
        } else {
            return null;
        }
    }
}
