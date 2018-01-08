package org.semisoft.findmp.util;

import org.semisoft.findmp.domain.Sector;

import java.util.ArrayList;
import java.util.List;

public class EdgeSector {
    private Sector sector;
    private List<Sector> expanderSectors = new ArrayList<>();
    private Flags flags = new Flags();

    public EdgeSector(Sector sector, int flags) {
        this.sector = sector;
        this.flags.setFlags(flags);
    }

//    public List<EdgeSector> expand() {
//        List<EdgeSector> newSectors = new ArrayList<>();
//
//        for (Sector expanderSector : expanderSectors) {
//
//            Sector newSector = new Sector(sector);
//            newSector.expandBy(expanderSector);
//
//            newSectors.add(new EdgeSector(newSector));
//        }
//
//        return newSectors;
//    }

    public void setX(int x) {
        sector.setX(x);
    }
    public int getX() {
        return sector.getX();
    }
    public void setY(int y) {
        sector.setY(y);
    }
    public int getY() {
        return sector.getY();
    }
    public Flags getFlags()
    {
        return flags;
    }
    public boolean hasFlag(int flag) {
        return flags.hasFlag(flag);
    }
    public Sector getSector() {
        return sector;
    }
    @Override
    public String toString() {
        return sector.toString();
    }
}
