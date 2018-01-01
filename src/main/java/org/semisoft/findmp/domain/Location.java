package org.semisoft.findmp.domain;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;

public class Location
{
    double latitude;
    double longitude;

    private static final double KM_PER_LATITUDE = 110.574;
    private static final double KM_PER_LONGITUDE = 111.320;

    public Location(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Location fromAddress(Address address)
    {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyBmigTs1qmX99io90DIZUcAUDlrO2gWkzI")
                .build();
        GeocodingResult[] results = new GeocodingResult[0];
        try
        {
            results = GeocodingApi.geocode(context,
                    address.toString()).await();
        } catch (ApiException | InterruptedException | IOException e)
        {
            return new Location(0,0);
        }

        if (results.length > 0)
            return new Location( results[0].geometry.location.lat, results[0].geometry.location.lng );
        else
            return new Location(0,0);
    }

    public static double calculateDistance(Location location1, Location location2)
    {
        return 0.0;
    }

    public double getLatitudeKilometers()
    {
        return KM_PER_LATITUDE*latitude;
    }
    public double getLongitudeKilometers()
    {
        return KM_PER_LONGITUDE*Math.cos(Math.toRadians(latitude))*longitude;
    }
    public double getLatitude()
    {
        return latitude;
    }
    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }
    public double getLongitude()
    {
        return longitude;
    }
    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    @Override
    public String toString()
    {
        return "(" + latitude + ", " + longitude + ")";
    }
}
