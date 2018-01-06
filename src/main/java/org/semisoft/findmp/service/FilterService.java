package org.semisoft.findmp.service;

import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Specialization;

import java.util.List;

public interface FilterService {
    List<MedicalPoint> filterBy(List<MedicalPoint> medicalPoints, Specialization specialization);
}
