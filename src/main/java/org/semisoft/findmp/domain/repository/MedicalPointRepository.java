package org.semisoft.findmp.domain.repository;

import org.semisoft.findmp.domain.MedicalPoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalPointRepository extends CrudRepository<MedicalPoint, Long>
{
    //that method will be auto-implemented by spring - see Spring Data for detail explanation
    Iterable<MedicalPoint> findByName(String name);
}
