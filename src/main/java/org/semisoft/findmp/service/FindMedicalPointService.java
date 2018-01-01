package org.semisoft.findmp.service;

import org.semisoft.findmp.domain.Location;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Sector;
import org.semisoft.findmp.domain.Specialization;
import org.semisoft.findmp.domain.repository.MedicalPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindMedicalPointService
{
    private MedicalPointRepository medicalPointRepository;
    private Location userLocation;

    @Autowired
    public FindMedicalPointService(MedicalPointRepository medicalPointRepository) {
        this.medicalPointRepository = medicalPointRepository;
    }

    //TODO change to MedicalPointUnits
    public ArrayList<MedicalPoint> findMedicalPoints(Specialization specialization, double latitude, double longitude)
    {
        userLocation = new Location(latitude, longitude);

        Sector userSector = Sector.fromLocation(userLocation);

        List<Sector> nearbySectors = findNearbySectors(userSector);

        return findClosestMedicalPointsInSectors(nearbySectors, specialization);
    }

    private List<Sector> findNearbySectors(Sector sector)
    {
        int x = sector.getX();
        int y = sector.getY();

        List<Sector> sectors = new ArrayList<>();
        Sector.SectorCorner corner = sector.getLocationSectorCorner(userLocation);

        sectors.add(sector);

        switch (corner)
        {
            case TOP_LEFT:
                sectors.add(new Sector(x-1, y));
                sectors.add(new Sector(x-1, y+1));
                sectors.add(new Sector(x, y+1));
                break;
            case BOTTOM_LEFT:
                sectors.add(new Sector(x-1, y));
                sectors.add(new Sector(x-1, y-1));
                sectors.add(new Sector(x, y-1));
                break;
            case BOTTOM_RIGHT:
                sectors.add(new Sector(x+1, y));
                sectors.add(new Sector(x+1, y-1));
                sectors.add(new Sector(x, y-1));
                break;
            case TOP_RIGHT:
                sectors.add(new Sector(x+1, y));
                sectors.add(new Sector(x+1, y+1));
                sectors.add(new Sector(x, y+1));
                break;
        }

        return sectors;
    }

    private ArrayList<MedicalPoint> findClosestMedicalPointsInSectors(List<Sector> sectors, Specialization specialization)
    {
        ArrayList<MedicalPoint> medicalPoints = new ArrayList<>();

        for (Sector sector : sectors)
        {
            //get all medical points from given sector of given specialization
            Collection<MedicalPoint> medicalPointsInSector =
                    (Collection<MedicalPoint>) medicalPointRepository.findBySector(sector);

            if (medicalPointsInSector == null || medicalPointsInSector.isEmpty()) continue;

            medicalPoints.addAll(
                    medicalPointsInSector
                    .stream()
                    .filter(m -> m.getSpecialization().equals(specialization))
                    .collect(Collectors.toList()));
        }

        medicalPoints.sort((m1, m2) ->
        {
            double d1 = Location.calculateDistance(userLocation, Location.fromAddress(m1.getAddress()));
            double d2 = Location.calculateDistance(userLocation, Location.fromAddress(m2.getAddress()));

            if (d1 > d2) return 1;
            else if (d2 < d1) return -1;
            else return 0;
        });

        return medicalPoints;
    }
}
