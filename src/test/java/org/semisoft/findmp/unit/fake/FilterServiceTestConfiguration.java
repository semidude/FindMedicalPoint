package org.semisoft.findmp.unit.fake;

import org.junit.Before;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Specialization;
import org.semisoft.findmp.service.FilterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class FilterServiceTestConfiguration {
    @Primary
    @Bean
    public FilterService filterService() {
        return new FilterService() {
            @Override
            public List<MedicalPoint> filterBy(List<MedicalPoint> medicalPoints, Specialization specialization) {
                return medicalPoints
                        .stream()
                        .filter(m -> m.getSpecialization().equals(specialization))
                        .collect(Collectors.toList());
            }
        };
    }
}
