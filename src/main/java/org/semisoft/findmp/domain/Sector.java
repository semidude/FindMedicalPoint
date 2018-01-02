package org.semisoft.findmp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Sector
{
    @Id
    @GeneratedValue
    private Long id;
    private int x;
    private int y;

    private static double physicalWidth = 50;
    private static double physicalHeight = 50;

    public enum SectorCorner {TOP_LEFT, BOTTOM_LEFT, BOTTOM_RIGHT, TOP_RIGHT};

    public Sector(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Sector()
    {
        this(0,0);
    }

    public static Sector fromLocation(Location location)
    {
        double kmLongitude = location.getLongitudeKilometers();
        double kmLatitude = location.getLatitudeKilometers();

        int x = (int)((kmLongitude % physicalWidth == 0) ? kmLongitude/physicalWidth : kmLongitude/physicalWidth + 1);
        int y = (int)((kmLatitude % physicalHeight == 0) ? kmLatitude/physicalHeight : kmLatitude/physicalHeight + 1);
        return new Sector(x, y);
    }

    public SectorCorner getLocationSectorCorner(Location location)
    {
        if (!locationInBounds(location))
            throw new IllegalArgumentException("the location " + location + " is not placed in sector " + this);

        double kmLongitude = location.getLongitudeKilometers();
        double kmLatitude = location.getLatitudeKilometers();

        double left = x * physicalWidth;
        double right = (x+1) * physicalWidth;
        double top = (y+1) * physicalHeight;
        double bottom = y * physicalHeight;

        boolean leftCloser = Math.abs(left - kmLongitude) < Math.abs(right - kmLongitude);
        boolean topCloser = Math.abs(top - kmLatitude) < Math.abs(bottom - kmLatitude);

        SectorCorner corner = SectorCorner.TOP_LEFT;

        if (leftCloser && topCloser)
            corner = SectorCorner.TOP_LEFT;
        if (leftCloser && !topCloser)
            corner = SectorCorner.BOTTOM_LEFT;
        if (!leftCloser && !topCloser)
            corner = SectorCorner.BOTTOM_RIGHT;
        if (!leftCloser && topCloser)
            corner = SectorCorner.TOP_RIGHT;

        return corner;
    }

    private boolean locationInBounds(Location location)
    {
        //TODO implement locationInBounds
        return false;
    }


    public int getX()
    {
        return x;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public int getY()
    {
        return y;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public static double getPhysicalWidth() {
        return physicalWidth;
    }
    public static void setPhysicalWidth(double physicalWidth) {
        Sector.physicalWidth = physicalWidth;
    }
    public static double getPhysicalHeight() {
        return physicalHeight;
    }
    public static void setPhysicalHeight(double physicalHeight) {
        Sector.physicalHeight = physicalHeight;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sector)) return false;

        Sector sector = (Sector) o;

        if (x != sector.x) return false;
        return  y==sector.y;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }
}
