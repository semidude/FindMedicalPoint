package org.semisoft.findmp.service.impl;

import org.semisoft.findmp.domain.*;
import org.semisoft.findmp.domain.repository.MedicalPointRepository;
import org.semisoft.findmp.service.ExpandableAreaService;
import org.semisoft.findmp.service.FilterService;
import org.semisoft.findmp.service.FindMedicalPointService;
import org.semisoft.findmp.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.util.*;

@Service
public class FindMedicalPointServiceImpl implements FindMedicalPointService {

    @Autowired
    private SectorService sectorService;
    @Autowired
    private ExpandableAreaService expandableAreaService;
    @Autowired
    private FilterService filterService;
    @Autowired
    private MedicalPointRepository medicalPointRepository;

    private Location userLocation;
    private Specialization givenSpecialization;

    @Override
    public List<MedicalPoint> findMedicalPoints(Specialization specialization, Location userLocation, int count) {

        this.userLocation = userLocation;
        givenSpecialization = specialization;

        List<MedicalPoint> medicalPoints = findAtLeastMedicalPoints(count);

        sortByDistanceFromUser(medicalPoints);

        return cutIfNeeded(medicalPoints, count);
    }

    private List<MedicalPoint> findAtLeastMedicalPoints(int count) {

        Set<MedicalPoint> medicalPoints = new HashSet<>();
        Sector userSector = sectorService.fromLocation(userLocation);
        ExpandableArea area = expandableAreaService.createExpandableArea(userSector);

        while (medicalPoints.size() < count &&
                area.getSectors().size() < expandableAreaService.getMaxAreaSize()) {

            medicalPoints.addAll(
                    findMedicalPointsInSectors(area.getSectors()));

            expandableAreaService.expand(area);
        }

        return new ArrayList<>(medicalPoints);
    }

    private Set<MedicalPoint> findMedicalPointsInSectors(List<Sector> sectors) {

        Set<MedicalPoint> medicalPoints = new HashSet<>();

        for (Sector sector : sectors) {

            List<MedicalPoint> medicalPointsInSector =
                    (List<MedicalPoint>) medicalPointRepository.findBySector(sector);

            if (nullOrEmpty(medicalPointsInSector))
                continue;

            medicalPoints.addAll(
                    filterService.filterBy(medicalPointsInSector, givenSpecialization));
        }

        return medicalPoints;
    }

    private boolean nullOrEmpty(List<MedicalPoint> medicalPoints) {
        return medicalPoints == null || medicalPoints.isEmpty();
    }

    private void sortByDistanceFromUser(List<MedicalPoint> medicalPoints) {

        medicalPoints.sort((m1, m2) -> {
            double d1 = Location.calculateDistance(userLocation, m1.getLocation());
            double d2 = Location.calculateDistance(userLocation, m2.getLocation());

            return Double.compare(d1, d2);
        });
    }

    private List<MedicalPoint> cutIfNeeded(List<MedicalPoint> medicalPoints, int count) {

        return (medicalPoints.size() > 5) ? medicalPoints.subList(0, count) : medicalPoints;
    }
}
