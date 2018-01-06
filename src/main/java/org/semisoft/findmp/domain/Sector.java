package org.semisoft.findmp.domain;

import org.semisoft.findmp.domain.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity
public class Sector {
    @Id
    @GeneratedValue
    private Long id;
    private int x;
    private int y;

    private static SectorRepository sectorRepository;
    private static double physicalWidth = 2;
    private static double physicalHeight = 2;

    private Sector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Sector(Sector other) {
        this(other.x, other.y);
    }

    public Sector() {
        this(0, 0);
    }

    private void set(Sector other) {
        id = other.getId();
        x = other.getX();
        y = other.getY();
    }


    public static Sector fromCoordinates(int x, int y) {

        Sector sector = new Sector(x, y);

        persistIfNeeded(sector);

        return sector;
    }

    private static void persistIfNeeded(Sector sector) {

        List<Sector> persistedSectors = new ArrayList<>();
        sectorRepository.findAll().forEach(persistedSectors::add);

        if (persistedSectors.contains(sector))
            sector.set(sectorRepository.findByXAndY(sector.getX(), sector.getY()));
        else
            sectorRepository.save(sector);
    }

    public static Sector fromLocation(Location location) {

        double kmLongitude = location.getLongitudeKilometers();
        double kmLatitude = location.getLatitudeKilometers();

        int x = (int) ((kmLongitude % physicalWidth == 0) ? kmLongitude / physicalWidth : kmLongitude / physicalWidth + 1);
        int y = (int) ((kmLatitude % physicalHeight == 0) ? kmLatitude / physicalHeight : kmLatitude / physicalHeight + 1);

        return Sector.fromCoordinates(x, y);
    }

    public Long getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Autowired
    public void setSectorRepository(SectorRepository sectorRepository) {
        Sector.sectorRepository = sectorRepository;
    }

    @Override
    public String toString() {
//        return id + ": (" + x + ", " + y + ")";
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        Sector other = (Sector) o;
        return x == other.getX() && y == other.getY();
    }
}
