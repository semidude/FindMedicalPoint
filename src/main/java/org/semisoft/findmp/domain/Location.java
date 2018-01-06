package org.semisoft.findmp.domain;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.IOException;

@Component
@Entity
public class Location {
    @Id
    @GeneratedValue
    private Long id;
    private double latitude;
    private double longitude;

    private static final double KM_PER_LATITUDE = 110.574;
    private static final double KM_PER_LONGITUDE = 111.320;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location() {
        this(0.0, 0.0);
    }

    public static double calculateDistance(Location location1, Location location2) {

        return distance(location1.getLatitude(), location1.getLongitude(), location2.getLatitude(), location2.getLongitude(), 'K');
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public double getLatitudeKilometers() {
        return KM_PER_LATITUDE * latitude;
    }

    public double getLongitudeKilometers() {
        return KM_PER_LONGITUDE * Math.cos(Math.toRadians(latitude)) * longitude;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "(" + latitude + ", " + longitude + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location))
            return false;

        return toString().equals(o.toString());
    }
}
