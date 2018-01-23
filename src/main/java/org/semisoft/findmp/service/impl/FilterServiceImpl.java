package org.semisoft.findmp.service.impl;

import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Specialization;
import org.semisoft.findmp.service.FilterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterServiceImpl implements FilterService {
    @Override
    public List<MedicalPoint> filterBy(List<MedicalPoint> medicalPoints, Specialization specialization) {
        return medicalPoints
                .stream()
                .filter(m -> m.getSpecialization().getName().toLowerCase()
                        .contains(specialization.getName().toLowerCase()))
                .collect(Collectors.toList());
    }
}
