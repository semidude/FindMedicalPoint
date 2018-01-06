package org.semisoft.findmp.unit.fake;

import org.semisoft.findmp.domain.Sector;
import org.semisoft.findmp.service.SectorService;
import org.semisoft.findmp.service.impl.SectorServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SectorServiceTestConfiguration {
    @Primary
    @Bean
    public SectorService sectorService() {

        return new SectorServiceImpl() {
            @Override
            protected void persistIfNeeded(Sector sector) {
                //just do nothing :)
            }
        };
    }
}
