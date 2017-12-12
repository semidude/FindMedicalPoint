package org.semisoft.findmp.domain.repository;

import org.semisoft.findmp.domain.Sector;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends CrudRepository<Sector, Long>
{
    Sector findByXAndY(int x, int y);
}
