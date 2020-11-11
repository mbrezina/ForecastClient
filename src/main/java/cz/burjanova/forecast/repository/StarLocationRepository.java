package cz.burjanova.forecast.repository;

import cz.burjanova.forecast.entity.StarLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StarLocationRepository  extends JpaRepository<StarLocation, Long> {

    //User findBySub(String sub);

    List<StarLocation> findBySub(String sub);

}
