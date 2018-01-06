package org.semisoft.findmp.service;

import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Specialization;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FindMedicalPointService {
    List<MedicalPoint> findMedicalPoints(Specialization specialization, double latitude, double longitude, int count);
}
