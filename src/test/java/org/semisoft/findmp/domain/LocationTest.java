package org.semisoft.findmp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest
{
    @Test
    void getLatitudeKilometers()
    {
        Location location = new Location(52.241629599999996, 20.940932999999998);

        assertEquals(5776, (int)location.getLatitudeKilometers());
    }

    @Test
    void getLongitudeKilometers()
    {
        Location location = new Location(52.241629599999996, 20.940932999999998);

        assertEquals(1427, (int)location.getLongitudeKilometers());
    }
}