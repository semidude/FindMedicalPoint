package org.semisoft.findmp.unit.fake;

import org.semisoft.findmp.service.ExpandableAreaService;
import org.semisoft.findmp.service.impl.ExpandableAreaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ExpandableAreaServiceTestConfiguration {
    @Primary
    @Bean
    public ExpandableAreaService expandableAreaService() {
        return new ExpandableAreaServiceImpl() {
            @Override
            public int getMaxAreaSize() {
                return 49;
            }
        };
    }
}
