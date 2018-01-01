package org.semisoft.findmp.unit.domain;

import org.junit.jupiter.api.Test;
import org.semisoft.findmp.domain.Location;
import org.semisoft.findmp.domain.Sector;

import static org.junit.jupiter.api.Assertions.*;

class SectorTest
{
    @Test
    void fromLocation()
    {
        Location location = new Location(52.241629599999996, 20.940932999999998);

        Sector sector = Sector.fromLocation(location);

        assertEquals(143, sector.getX());
        assertEquals(578, sector.getY());
    }
}