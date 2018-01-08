package org.semisoft.findmp.unit.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.semisoft.findmp.FindMedicalPointApplication;
import org.semisoft.findmp.domain.ExpandableArea;
import org.semisoft.findmp.domain.Sector;
import org.semisoft.findmp.service.ExpandableAreaService;
import org.semisoft.findmp.service.SectorService;
import org.semisoft.findmp.unit.fake.SectorServiceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {FindMedicalPointApplication.class, SectorServiceTestConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ExpandableAreaTest {
    @Autowired
    private SectorService sectorService;
    @Autowired
    private ExpandableAreaService expandableAreaService;

    @Test
    public void expand() {
        Sector sector = sectorService.fromCoordinates(0, 0);
        ExpandableArea area = expandableAreaService.createExpandableArea(sector);

        expandableAreaService.expand(area);
        expandableAreaService.expand(area);
        expandableAreaService.expand(area);

        List<Sector> areaSectors = area.getSectors();
        List<Sector> generatedSectors = generateAreaLayers(3);

        Collections.sort(areaSectors);
        Collections.sort(generatedSectors);

        assertEquals(49, areaSectors.size());
        assertEquals(generatedSectors, areaSectors);
    }

    private List<Sector> generateAreaLayers(int layers) {
        List<Sector> square = new ArrayList<>();

        for (int i = -layers; i <= layers; ++i) {
            for (int j = -layers; j <= layers; ++j) {
                square.add(new Sector(i, j));
            }
        }

        return square;
    }
}
