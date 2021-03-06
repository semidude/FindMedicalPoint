package org.semisoft.findmp.service.impl.expanding.util;

import org.semisoft.findmp.domain.Sector;

public class EdgeSector {
    private Sector sector;
    private Flags flags = new Flags();

    public EdgeSector(Sector sector, int flags) {
        this.sector = sector;
        this.flags.setFlags(flags);
    }

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
