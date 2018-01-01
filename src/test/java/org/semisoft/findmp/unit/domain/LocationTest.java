package org.semisoft.findmp.unit.domain;

import org.junit.jupiter.api.Test;
import org.semisoft.findmp.domain.Location;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest
{
    @Test
    void getLatitudeKilometers()
    {
        Location location = new Location(52.241629599999996, 20.940932999999998);

        int latitudeKilometers = (int)location.getLatitudeKilometers();

        assertEquals(5776, latitudeKilometers);
    }

    @Test
    void getLongitudeKilometers()
    {
        Location location = new Location(52.241629599999996, 20.940932999999998);

        int longitudeKilometers = (int)location.getLongitudeKilometers();

        assertEquals(1427, longitudeKilometers);
    }
}