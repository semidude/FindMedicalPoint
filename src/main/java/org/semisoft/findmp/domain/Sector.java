package org.semisoft.findmp.domain;

import org.semisoft.findmp.domain.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity
public class Sector {
    @Id
    @GeneratedValue
    private Long id;
    private int x;
    private int y;

    private static double physicalWidth = 2;
    private static double physicalHeight = 2;

    public Sector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Sector(Sector other) {
        this(other.x, other.y);
    }

    public Sector() {
        this(0, 0);
    }

    public void set(Sector other) {
        id = other.getId();
        x = other.getX();
        y = other.getY();
    }

    public Long getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static double getPhysicalWidth() {
        return physicalWidth;
    }

    public static double getPhysicalHeight() {
        return physicalHeight;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        Sector other = (Sector) o;
        return x == other.getX() && y == other.getY();
    }
}
