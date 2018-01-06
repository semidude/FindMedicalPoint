package org.semisoft.findmp.service.impl;

import org.semisoft.findmp.domain.ExpandableArea;
import org.semisoft.findmp.domain.Sector;
import org.semisoft.findmp.domain.SectorDecorator;
import org.semisoft.findmp.service.ExpandableAreaService;
import org.semisoft.findmp.service.SectorService;
import org.semisoft.findmp.util.Flags;
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
        area.addEdgeSector(new SectorDecorator(origin, LEFT | TOP | RIGHT | BOTTOM));
        return area;
    }

    @Override
    public void expand(ExpandableArea area) {
        List<SectorDecorator> oldEdge = new ArrayList<>(area.getEdge());
        area.clearEdge();

        for (SectorDecorator sector : oldEdge) {

            Flags flags = sector.getFlags();
            int x = sector.getX();
            int y = sector.getY();

            List<SectorDecorator> newSectors = new ArrayList<>();

            if (flags.hasFlag(LEFT))
                newSectors.add(new SectorDecorator(sectorService.fromCoordinates(x - 1, y), LEFT));

            if (flags.hasFlag(TOP))
                newSectors.add(new SectorDecorator(sectorService.fromCoordinates(x, y + 1), TOP));

            if (flags.hasFlag(RIGHT))
                newSectors.add(new SectorDecorator(sectorService.fromCoordinates(x + 1, y ), RIGHT));

            if (flags.hasFlag(BOTTOM))
                newSectors.add(new SectorDecorator(sectorService.fromCoordinates(x, y - 1), BOTTOM));

            if (flags.hasFlag(LEFT) && flags.hasFlag(TOP))
                newSectors.add(new SectorDecorator(sectorService.fromCoordinates(x - 1, y + 1 ), LEFT | TOP));

            if (flags.hasFlag(RIGHT) && flags.hasFlag(TOP))
                newSectors.add(new SectorDecorator(sectorService.fromCoordinates(x + 1, y + 1), RIGHT | TOP));

            if (flags.hasFlag(LEFT) && flags.hasFlag(BOTTOM))
                newSectors.add(new SectorDecorator(sectorService.fromCoordinates(x - 1, y - 1), LEFT | BOTTOM));

            if (flags.hasFlag(RIGHT) && flags.hasFlag(BOTTOM))
                newSectors.add(new SectorDecorator(sectorService.fromCoordinates(x + 1, y - 1), RIGHT | BOTTOM));

            area.addSectors(newSectors.stream().map(decorator -> decorator.getSector()).collect(Collectors.toList()));
            area.addEdgeSectors(newSectors);

        }
    }

    @Override
    public int getMaxAreaSize() {
        return Integer.MAX_VALUE;
    }
}
