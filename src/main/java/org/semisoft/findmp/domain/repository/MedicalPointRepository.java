package org.semisoft.findmp.domain.repository;

import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Sector;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalPointRepository extends CrudRepository<MedicalPoint, Long>
{
    Iterable<MedicalPoint> findBySector(Sector sector);
}
