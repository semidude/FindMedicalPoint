package org.semisoft.findmp.service;

import org.semisoft.findmp.domain.Address;
import org.semisoft.findmp.domain.Location;
import org.springframework.stereotype.Service;

public interface LocationService {
    Location fromAddress(Address address);
}
