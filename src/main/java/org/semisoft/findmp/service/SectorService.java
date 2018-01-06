package org.semisoft.findmp.service;

import org.semisoft.findmp.domain.Location;
import org.semisoft.findmp.domain.Sector;
import org.springframework.stereotype.Service;

public interface SectorService {
    Sector fromLocation(Location location);
    Sector fromCoordinates(int x, int y);
}
