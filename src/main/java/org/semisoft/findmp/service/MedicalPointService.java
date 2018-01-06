package org.semisoft.findmp.service;

import org.semisoft.findmp.domain.Address;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Specialization;
import org.springframework.stereotype.Service;

public interface MedicalPointService {
    MedicalPoint createAndLocalizeMedicalPoint(String name, Specialization specialization, Address address);
    void localizeMedicalPoint(MedicalPoint medicalPoint);
}
