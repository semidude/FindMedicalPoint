package org.semisoft.findmp.service.impl;

import org.semisoft.findmp.domain.Location;
import org.semisoft.findmp.domain.Sector;
import org.semisoft.findmp.domain.repository.SectorRepository;
import org.semisoft.findmp.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Override
    public Sector fromLocation(Location location) {

        double kmLongitude = location.getLongitudeKilometers();
        double kmLatitude = location.getLatitudeKilometers();

        double physicalWidth = Sector.getPhysicalWidth();
        double physicalHeight = Sector.getPhysicalHeight();

        int x = (int) ((kmLongitude % physicalWidth == 0) ? kmLongitude / physicalWidth : kmLongitude / physicalWidth + 1);
        int y = (int) ((kmLatitude % physicalHeight == 0) ? kmLatitude / physicalHeight : kmLatitude / physicalHeight + 1);

        return fromCoordinates(x, y);
    }

    @Override
    public Sector fromCoordinates(int x, int y) {

        Sector sector = new Sector(x, y);

        persistIfNeeded(sector);

        return sector;
    }

    protected void persistIfNeeded(Sector sector) {

        List<Sector> persistedSectors = new ArrayList<>();
        sectorRepository.findAll().forEach(persistedSectors::add);

        if (persistedSectors.contains(sector))
            sector.set(sectorRepository.findByXAndY(sector.getX(), sector.getY()));
        else
            sectorRepository.save(sector);
    }
}
