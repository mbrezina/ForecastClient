package cz.burjanova.forecast.service;

import cz.burjanova.forecast.entity.StarLocation;
import cz.burjanova.forecast.repository.StarLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StarLocationService {

    @Autowired
    private StarLocationRepository starLocationRepository;

    public List<StarLocation> getStarLocationList(String customerSub) {
        return starLocationRepository.findBySub(customerSub);
    }

    public


    List<String> preferenceUzivatele = new ArrayList<>();


//        přidat item
//            rotate
//                zahodit 4.

//
//        for (StarLocation record : seznamUzivatele) {
//            preferenceUzivatele.add(record.getAktivita());
//        }
      //  return preferenceUzivatele;
}



