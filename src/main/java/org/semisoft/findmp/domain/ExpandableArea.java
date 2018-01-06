package org.semisoft.findmp.domain;

import org.semisoft.findmp.service.SectorService;
import org.semisoft.findmp.service.impl.SectorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExpandableArea {
    private List<Sector> sectors = new ArrayList<>();
    private List<SectorDecorator> edge = new ArrayList<>();

    public void addSector(Sector sector) {
        sectors.add(sector);
    }

    public void addSectors(Collection<Sector> sectors) {
        this.sectors.addAll(sectors);
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    public void addEdgeSector(SectorDecorator sector) {
        edge.add(sector);
    }

    public void addEdgeSectors(Collection<SectorDecorator> sectors) {
        edge.addAll(sectors);
    }

    public void clearEdge() {
        edge.clear();
    }

    public List<SectorDecorator> getEdge() {
        return edge;
    }

    @Override
    public String toString() {
        return sectors.toString();
    }
}
