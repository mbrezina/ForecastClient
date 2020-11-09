package cz.burjanova.forecast.repository;

import cz.burjanova.forecast.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    User findBySub(String sub);

}


