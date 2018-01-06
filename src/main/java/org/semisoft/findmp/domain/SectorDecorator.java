package org.semisoft.findmp.domain;

import org.semisoft.findmp.util.Flags;

public class SectorDecorator {
    private Sector sector;
    private Flags flags = new Flags();

    public SectorDecorator(Sector sector, int flags) {
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
    public Sector getSector() {
        return sector;
    }
    @Override
    public String toString() {
        return sector.toString();
    }
}
