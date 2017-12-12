package org.semisoft.findmp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SectorTest
{
    @Test
    void fromLocation()
    {
        Location location = new Location(52.241629599999996, 20.940932999999998);

        Sector sector = Sector.fromLocation(location);

        assertEquals(29, sector.getX());
        assertEquals(116, sector.getY());
    }
}