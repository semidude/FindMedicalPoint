package org.semisoft.findmp.service;

import org.semisoft.findmp.domain.Location;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Specialization;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.util.List;

public interface FindMedicalPointService {

    List<MedicalPoint> findMedicalPoints(Specialization specialization, Location userLocation, int count);
}
