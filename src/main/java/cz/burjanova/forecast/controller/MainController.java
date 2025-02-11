package cz.burjanova.forecast.controller;


import cz.burjanova.forecast.entity.PlaceForm;


import cz.burjanova.forecast.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@Controller
public class MainController {

    @Autowired
    WeatherService weatherService;

    @Value("${weather.api.place}")
    private String defaultPlace;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView showDefaultLocation() throws IOException {
        //ModelAndView data = new ModelAndView("index");
        //return data;

        return weatherService.makeBasicPage(defaultPlace, "index");
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView processSelectedLocation(PlaceForm filledForm) throws IOException {
        String selectedPlace = filledForm.getPlace();
        return weatherService.makeBasicPage(selectedPlace, "index");
    }

    @RequestMapping(value = "star/", method = RequestMethod.GET)
    public ModelAndView showFavouriteLocation(@AuthenticationPrincipal OAuth2User oauth2User) throws IOException {
        String subCode = oauth2User.getAttributes().get("sub").toString();
        String jmeno = oauth2User.getAttributes().get("name").toString();
        String email = oauth2User.getAttributes().get("email").toString();

        log.debug(subCode);
        log.debug(email);
        log.debug(jmeno);

        ModelAndView dataHolder = weatherService.makeStarPage("star", subCode, email);
        return dataHolder;
    }

    @RequestMapping(value = "star/", method = RequestMethod.POST, params = "saveFavouritePlace")
    public ModelAndView showFavouriteLocation(@AuthenticationPrincipal OAuth2User oauth2User, PlaceForm filledForm) throws IOException {
        String newFavouritePlace = filledForm.getPlace();
        String subCode = oauth2User.getAttributes().get("sub").toString();
        String email = oauth2User.getAttributes().get("email").toString();

        ModelAndView dataHolder;

        if (newFavouritePlace.isEmpty()) {
            dataHolder = weatherService.makeStarPage("star", subCode, email);
            dataHolder.addObject("message", "You have not filled any place, try again");
        } else if (weatherService.saveNewFavouritePlace(newFavouritePlace, subCode, email).equals("location is ok")) {
            dataHolder = weatherService.makeStarPage("star", subCode, email);
        } else {
            dataHolder = weatherService.makeStarPage("star", subCode, email);
            dataHolder.addObject("message", "The place you inserted was not found, try another place or check typos");
        }
        return dataHolder;
    }
}


