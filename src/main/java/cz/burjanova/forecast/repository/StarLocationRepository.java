package cz.burjanova.forecast.repository;

import cz.burjanova.forecast.entity.StarLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarLocationRepository extends JpaRepository<StarLocation, Long> {

//    @Query("SELECT s.location1, s.location2, s.location3 FROM StarLocation s WHERE s.sub = ?1")
//    List<String> findOnlyPlacesBySub(String sub);

    StarLocation findBySub(String sub);

}
