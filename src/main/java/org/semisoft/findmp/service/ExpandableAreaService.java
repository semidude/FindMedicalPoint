package org.semisoft.findmp.service;

import org.semisoft.findmp.domain.ExpandableArea;
import org.semisoft.findmp.domain.Sector;
import org.springframework.stereotype.Service;

public interface ExpandableAreaService {
    ExpandableArea createExpandableArea(Sector origin);
    void expand(ExpandableArea area);
    int getMaxAreaSize();
}
