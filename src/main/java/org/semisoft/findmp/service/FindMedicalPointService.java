package org.semisoft.findmp.service;

import org.semisoft.findmp.domain.*;
import org.semisoft.findmp.domain.repository.MedicalPointRepository;
import org.semisoft.findmp.domain.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FindMedicalPointService
{
    private MedicalPointRepository medicalPointRepository;
    private SectorRepository sectorRepository;
    private Location userLocation;

    @Autowired
    public FindMedicalPointService(MedicalPointRepository medicalPointRepository, SectorRepository sectorRepository) {
        this.medicalPointRepository = medicalPointRepository;
        this.sectorRepository = sectorRepository;
    }

    public List<MedicalPoint> findMedicalPoints(Specialization specialization, double latitude, double longitude) {

        userLocation = new Location(latitude, longitude);

        Sector userSector = Sector.fromLocation(userLocation);

        Set<MedicalPoint> medicalPoints = new HashSet<>();
        ExpandableArea area = new ExpandableArea(userSector);

        while (medicalPoints.size() < 5) {

            medicalPoints.addAll(
                    findClosestMedicalPointsInSectors(area.getSectors(), specialization));

            area.expand();
        }

        System.out.println("sectors in area: " + area.getSectors().size());
        System.out.println("sectors: " + area.getSectors());

        List<MedicalPoint> medicalPointList = new ArrayList<>(medicalPoints);
        sortByDistanceFromUser(medicalPointList);

        return medicalPointList.subList(0, 5);
    }

    private Set<MedicalPoint>
    findClosestMedicalPointsInSectors(List<Sector> sectors, Specialization specialization) {

        Set<MedicalPoint> medicalPoints = new HashSet<>();

        for (Sector sector : sectors) {

            List<MedicalPoint> medicalPointsInSector =
                    (List<MedicalPoint>) medicalPointRepository.findBySector(sector);

            if (nullOrEmpty(medicalPointsInSector))
                continue;

//            medicalPoints.addAll(
//                    filterBy(medicalPointsInSector, specialization));

            medicalPoints.addAll(medicalPointsInSector);
        }

        return medicalPoints;
    }

    private boolean nullOrEmpty(List<MedicalPoint> medicalPoints) {
        return medicalPoints == null || medicalPoints.isEmpty();
    }

    private List<MedicalPoint> filterBy(List<MedicalPoint> medicalPoints, Specialization specialization) {
        return medicalPoints
                .stream()
                .filter(m -> m.getSpecialization().equals(specialization))
                .collect(Collectors.toList());
    }

    private void sortByDistanceFromUser(List<MedicalPoint> medicalPoints) {

        medicalPoints.sort((m1, m2) -> {
            double d1 = Location.calculateDistance(userLocation, m1.getLocation());
            double d2 = Location.calculateDistance(userLocation, m2.getLocation());

            return Double.compare(d1, d2);
        });
    }
}
