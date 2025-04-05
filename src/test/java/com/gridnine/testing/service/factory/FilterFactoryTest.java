package com.gridnine.testing.service.factory;

import com.gridnine.testing.service.filter.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.service.filter.DepartureBeforeNowFilter;
import com.gridnine.testing.service.filter.FlightFilter;
import com.gridnine.testing.service.filter.TotalHoursTransfersFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FilterFactoryTest {

    @Test
    void testGetFilterByName_validWithoutParams() {
        String filterName = "DepartureBeforeNowFilter";

        FlightFilter filter = FilterFactory.getFilterByName(filterName, null);

        assertNotNull(filter);
        assertTrue(filter instanceof DepartureBeforeNowFilter);
    }

    @Test
    void testGetFilterByName_validWithParams() {
        String filterName = "TotalHoursTransfersFilter";
        String param = "3";

        FlightFilter filter = FilterFactory.getFilterByName(filterName, param);

        assertNotNull(filter);
        assertTrue(filter instanceof TotalHoursTransfersFilter);
        assertEquals(Double.parseDouble(param), ((TotalHoursTransfersFilter) filter).getBoundTransferHours());
    }

    @Test
    void testGetFilterByName_validWithEmptyParams() {
        String filterName = "TotalHoursTransfersFilter";
        String param = "";
        double expectHours = 2.0;

        FlightFilter filter = FilterFactory.getFilterByName(filterName, param);

        assertNotNull(filter);
        assertTrue(filter instanceof TotalHoursTransfersFilter);
        assertEquals(expectHours, ((TotalHoursTransfersFilter) filter).getBoundTransferHours());
    }

    @Test
    void testGetFilterByName_withInvalidParams() {
        String filterName = "TotalHoursTransfersFilter";
        String param = "wrong";

        FlightFilter filter = FilterFactory.getFilterByName(filterName, param);

        assertNull(filter);
    }

    @Test
    void testGetFilterByName_withParamsForNoParamsFilter() {
        String filterName = "ArrivalBeforeDepartureFilter";
        String param = "break";

        FlightFilter filter = FilterFactory.getFilterByName(filterName, param);

        assertTrue(filter instanceof ArrivalBeforeDepartureFilter);
    }

    @Test
    void testGetFilterByName_invalidFilter() {
        String filterName = "WrongFilter";
        String param = "exist";

        FlightFilter filter = FilterFactory.getFilterByName(filterName, param);

        assertNull(filter);
    }
}
