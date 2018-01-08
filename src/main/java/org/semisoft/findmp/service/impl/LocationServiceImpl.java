package org.semisoft.findmp.service.impl;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.semisoft.findmp.domain.Address;
import org.semisoft.findmp.domain.Location;
import org.semisoft.findmp.service.LocationService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LocationServiceImpl implements LocationService {

    @Override
    public Location fromAddress(Address address) {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyA7LUAz1n3CR1jJicUiRWHNDRF1ODvJsA4")
                .build();
        GeocodingResult[] results = new GeocodingResult[0];

        Location location;

        try {
            results = GeocodingApi.geocode(context, address.toString()).await();
        }
        catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        if (results.length > 0)
            location = new Location(results[0].geometry.location.lat, results[0].geometry.location.lng);
        else
            location = new Location(0, 0);

        return location;
    }
}
