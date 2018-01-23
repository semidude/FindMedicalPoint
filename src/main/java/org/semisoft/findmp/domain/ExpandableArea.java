package org.semisoft.findmp.domain;

import org.semisoft.findmp.service.impl.expanding.util.EdgeSector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExpandableArea {
    private List<Sector> sectors = new ArrayList<>();
    private List<EdgeSector> edge = new ArrayList<>();

    public void addSector(Sector sector) {
        sectors.add(sector);
    }

    public void addSectors(Collection<Sector> sectors) {
        this.sectors.addAll(sectors);
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    public void addEdgeSector(EdgeSector sector) {
        edge.add(sector);
    }

    public void addEdgeSectors(Collection<EdgeSector> sectors) {
        edge.addAll(sectors);
    }

    public void clearEdge() {
        edge.clear();
    }

    public List<EdgeSector> getEdge() {
        return edge;
    }

    @Override
    public String toString() {
        return sectors.toString();
    }
}