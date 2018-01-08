package org.semisoft.findmp.service.impl;

import org.semisoft.findmp.domain.ExpandableArea;
import org.semisoft.findmp.domain.Sector;
import org.semisoft.findmp.util.EdgeSector;
import org.semisoft.findmp.service.ExpandableAreaService;
import org.semisoft.findmp.service.SectorService;
import org.semisoft.findmp.util.Rule;
import org.semisoft.findmp.util.Rules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpandableAreaServiceImpl implements ExpandableAreaService {
    @Autowired
    private SectorService sectorService;
    private int LEFT = 1, TOP = 2, RIGHT = 4, BOTTOM = 8;

    @Override
    public ExpandableArea createExpandableArea(Sector origin) {
        ExpandableArea area = new ExpandableArea();
        area.addSector(origin);
        area.addEdgeSector(new EdgeSector(origin, LEFT | TOP | RIGHT | BOTTOM));
        return area;
    }

    @Override
    public void expand(ExpandableArea area) {
        List<EdgeSector> oldEdge = new ArrayList<>(area.getEdge());
        area.clearEdge();

        for (EdgeSector edgeSector : oldEdge) {

            List<EdgeSector> newSectors = new ArrayList<>();

            Rules<EdgeSector> rules = new Rules<>( edgeSector,
                                //when...                         //then...
                    new Rule<>( es -> es.hasFlag(LEFT),           es -> newSectors.add( generateSector(es, -1, 0, LEFT))),
                    new Rule<>( es -> es.hasFlag(TOP),            es -> newSectors.add( generateSector(es, 0, 1, TOP))),
                    new Rule<>( es -> es.hasFlag(RIGHT),          es -> newSectors.add( generateSector(es, 1, 0, RIGHT))),
                    new Rule<>( es -> es.hasFlag(BOTTOM),         es -> newSectors.add( generateSector(es, 0, -1, BOTTOM))),
                    new Rule<>( es -> es.hasFlag(LEFT | TOP),     es -> newSectors.add( generateSector(es, -1, 1, LEFT | TOP))),
                    new Rule<>( es -> es.hasFlag(RIGHT | TOP),    es -> newSectors.add( generateSector(es, 1, 1, RIGHT | TOP))),
                    new Rule<>( es -> es.hasFlag(LEFT | BOTTOM),  es -> newSectors.add( generateSector(es, -1, -1, LEFT | BOTTOM))),
                    new Rule<>( es -> es.hasFlag(RIGHT | BOTTOM), es -> newSectors.add( generateSector(es, 1, -1, RIGHT | BOTTOM)))
            );

            rules.applyRules();

            area.addSectors(hullSectors(newSectors));
            area.addEdgeSectors(newSectors);
        }
    }

    private EdgeSector generateSector(EdgeSector es, int xOffset, int yOffset, int flags) {
        return new EdgeSector(
                sectorService.fromCoordinates(
                    es.getX() + xOffset,
                    es.getY() + yOffset),
                    flags);
    }

    private List<Sector> hullSectors(List<EdgeSector> edgeSectors) {
        return edgeSectors
                .stream()
                .map(decorator -> decorator.getSector())
                .collect(Collectors.toList());
    }

    @Override
    public int getMaxAreaSize() {
        return Integer.MAX_VALUE;
    }
}
