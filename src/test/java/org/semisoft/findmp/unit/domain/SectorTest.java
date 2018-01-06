package org.semisoft.findmp.unit.domain;

import org.junit.Before;
import org.junit.Test;
import org.semisoft.findmp.domain.Location;
import org.semisoft.findmp.domain.Sector;
import org.semisoft.findmp.service.SectorService;
import org.semisoft.findmp.service.impl.SectorServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class SectorTest {
    private SectorService sectorService;

    @Before
    public void init() {
        sectorService = new SectorServiceImpl() {
            @Override
            protected void persistIfNeeded(Sector sector) {
                //just do nothing :)
            }
        };
    }

    @Test
    public void fromLocation() {
        Location location = new Location(52.241629599999996, 20.940932999999998);

        Sector sector = sectorService.fromLocation(location);

        assertEquals(714, sector.getX());
        assertEquals(2889, sector.getY());
    }
}