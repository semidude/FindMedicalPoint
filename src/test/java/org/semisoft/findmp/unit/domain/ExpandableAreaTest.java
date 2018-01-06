package org.semisoft.findmp.unit.domain;

import org.junit.Test;
import org.semisoft.findmp.domain.ExpandableArea;
import org.semisoft.findmp.domain.Sector;

import static org.junit.jupiter.api.Assertions.*;

public class ExpandableAreaTest {
    @Test
    public void expand() {
        Sector sector = Sector.fromCoordinates(0, 0);
        ExpandableArea area = new ExpandableArea(sector);

        area.expand();
        area.expand();
        area.expand();

        assertEquals(49, area.getSectors().size());
    }
}
