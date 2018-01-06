package org.semisoft.findmp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpandableArea {
    private List<Sector> sectors = new ArrayList<>();
    private List<SectorDecorator> edge = new ArrayList<>();
    private int LEFT = 1, TOP = 2, RIGHT = 4, BOTTOM = 8;

    private static class Flags {
        private int flags = 0;

        public void setFlag(int flag) {
            flags |= flag;
        }

        public boolean hasFlag(int flag) {
            return (flags & flag) == flag;
        }

        public int getFlags() {
            return flags;
        }

        public void setFlags(int flags) {
            this.flags = flags;
        }
    }

    private static class SectorDecorator {
        private Sector sector;
        private Flags flags = new Flags();

        SectorDecorator(Sector sector, int flags) {
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

    public ExpandableArea(Sector origin) {
        sectors.add(origin);
        edge.add(new SectorDecorator(origin, LEFT | TOP | RIGHT | BOTTOM));
    }

    public void expand() {
        List<SectorDecorator> oldEdge = new ArrayList<>(edge);
        edge.clear();

        for (SectorDecorator sector : oldEdge) {

            Flags flags = sector.getFlags();
            int x = sector.getX();
            int y = sector.getY();

            List<SectorDecorator> newSectors = new ArrayList<>();

            if (flags.hasFlag(LEFT))
                newSectors.add(new SectorDecorator(Sector.fromCoordinates(x - 1, y), LEFT));

            if (flags.hasFlag(TOP))
                newSectors.add(new SectorDecorator(Sector.fromCoordinates(x, y + 1), TOP));

            if (flags.hasFlag(RIGHT))
                newSectors.add(new SectorDecorator(Sector.fromCoordinates(x + 1, y ), RIGHT));

            if (flags.hasFlag(BOTTOM))
                newSectors.add(new SectorDecorator(Sector.fromCoordinates(x, y - 1), BOTTOM));

            if (flags.hasFlag(LEFT) && flags.hasFlag(TOP))
                newSectors.add(new SectorDecorator(Sector.fromCoordinates(x - 1, y + 1 ), LEFT | TOP));

            if (flags.hasFlag(RIGHT) && flags.hasFlag(TOP))
                newSectors.add(new SectorDecorator(Sector.fromCoordinates(x + 1, y + 1), RIGHT | TOP));

            if (flags.hasFlag(LEFT) && flags.hasFlag(BOTTOM))
                newSectors.add(new SectorDecorator(Sector.fromCoordinates(x - 1, y - 1), LEFT | BOTTOM));

            if (flags.hasFlag(RIGHT) && flags.hasFlag(BOTTOM))
                newSectors.add(new SectorDecorator(Sector.fromCoordinates(x + 1, y - 1), RIGHT | BOTTOM));

            sectors.addAll(newSectors.stream().map(decorator -> decorator.getSector()).collect(Collectors.toList()));
            edge.addAll(newSectors);

        }
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    @Override
    public String toString() {
        return sectors.toString();
    }
}
