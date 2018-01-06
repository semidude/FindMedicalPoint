package org.semisoft.findmp.service.impl;

import org.semisoft.findmp.domain.*;
import org.semisoft.findmp.service.LocationService;
import org.semisoft.findmp.service.MedicalPointService;
import org.semisoft.findmp.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalPointServiceImpl implements MedicalPointService {

    @Autowired
    private LocationService locationService;
    @Autowired
    private SectorService sectorService;

    @Override
    public MedicalPoint createAndLocalizeMedicalPoint(String name, Specialization specialization, Address address) {

        MedicalPoint medicalPoint = new MedicalPoint(name, specialization, address);

        localizeMedicalPoint(medicalPoint);

        return medicalPoint;
    }

    @Override
    public void localizeMedicalPoint(MedicalPoint medicalPoint) {

        Location location = locationService.fromAddress(medicalPoint.getAddress());
        Sector sector = sectorService.fromLocation(location);

        medicalPoint.setLocation(location);
        medicalPoint.setSector(sector);
    }
}
