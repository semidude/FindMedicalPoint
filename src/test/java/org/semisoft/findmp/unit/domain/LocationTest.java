package org.semisoft.findmp.unit.domain;

import org.junit.Test;
import org.semisoft.findmp.domain.Address;
import org.semisoft.findmp.domain.Location;
import org.semisoft.findmp.service.LocationService;
import org.semisoft.findmp.service.impl.LocationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {
    private LocationService locationService = new LocationServiceImpl();

    @Test
    public void fromAddress() {
        Location location = locationService.fromAddress(new Address("Warszawa", "Księcia Janusza", "39"));
        assertEquals(location, new Location(52.242022, 20.94072));
    }

    @Test
    public void getLatitudeKilometers() {
        Location location = new Location(52.241629599999996, 20.940932999999998);

        int latitudeKilometers = (int) location.getLatitudeKilometers();

        assertEquals(5776, latitudeKilometers);
    }

    @Test
    public void getLongitudeKilometers() {
        Location location = new Location(52.241629599999996, 20.940932999999998);

        int longitudeKilometers = (int) location.getLongitudeKilometers();

        assertEquals(1427, longitudeKilometers);
    }

    @Test
    public void calculateDistance() {
        Location location1 = new Location(52.241675, 20.940829);
        Location location2 = new Location(52.181766, 20.841200);

        double distance = Location.calculateDistance(location1, location2);

        assertEquals(9.510347970550852, distance);
    }
}