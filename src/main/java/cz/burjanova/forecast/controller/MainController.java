package cz.burjanova.forecast.controller;


import cz.burjanova.forecast.entity.Location;
import cz.burjanova.forecast.entity.PlaceForm;
import cz.burjanova.forecast.service.CustomerService;
import cz.burjanova.forecast.service.StarLocationService;
import cz.burjanova.forecast.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
//@RequestMapping(path = "weather")
public class MainController {

    @Autowired
    WeatherService weatherService;

    @Autowired
    StarLocationService starLocationService;

    @Autowired
    CustomerService customerService;

    @Value("${weather.api.place}")
    private String defaultPlace;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView showDefaultLocation() throws IOException {
        //ModelAndView data = new ModelAndView("index");
        //return data;
        return weatherService.makeWebPage(defaultPlace, "index");
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView processSelectedLocation(PlaceForm filledForm) throws IOException {
        String selectedPlace = filledForm.getPlace();
        return weatherService.makeWebPage(selectedPlace, "index");
    }

    @RequestMapping(value = "star/", method = RequestMethod.GET)
    public ModelAndView showFavouriteLocation(@AuthenticationPrincipal OAuth2User oauth2User) throws IOException {
        String subKod = oauth2User.getAttributes().get("sub").toString();
        String jmeno = oauth2User.getAttributes().get("name").toString();
        String email = oauth2User.getAttributes().get("email").toString();

        log.info(subKod);
        log.info(email);
        log.info(jmeno);


        ModelAndView dataHolder = weatherService.makeWebPage(defaultPlace, "star");
        dataHolder.addObject("email", email);
        return dataHolder;

//        Customer vyhledanyZakaznik = customerService.najdiPodleSub(subKod);
//        if (vyhledanyZakaznik == null) {
//            Customer novyZakaznik = new Customer(subKod, jmeno, email);
//            customerService.ulozUzivatele(novyZakaznik);
//            model.addAttribute("user", novyZakaznik);
//        } else {
//            model.addAttribute("user", vyhledanyZakaznik);
//        }
//        return "user";





/*

    @RequestMapping("location")
    public ModelAndView showSelectedLocation() throws IOException {

        Location forecast = weatherService.makeForecats();  //add place

        String address = forecast.getAddress();
        List<Weather> dailyWeather = forecast.getValues();

        ModelAndView dataHolder = new ModelAndView("index");
        List<Weather> iconForecast = dailyWeather.subList(0, 1);

        dataHolder.addObject("dailyWeather", dailyWeather);
        dataHolder.addObject("iconForecast", iconForecast);
        dataHolder.addObject("address", address);

        return dataHolder;
    }


    @RequestMapping("favourite")
    public ModelAndView showFavouriteLocatoin() throws IOException {

        Location forecast = weatherService.makeForecats();  //add place

        String address = forecast.getAddress();
        List<Weather> dailyWeather = forecast.getValues();

        ModelAndView dataHolder = new ModelAndView("index");
        List<Weather> iconForecast = dailyWeather.subList(0, 1);

        dataHolder.addObject("dailyWeather", dailyWeather);
        dataHolder.addObject("iconForecast", iconForecast);
        dataHolder.addObject("address", address);

        return dataHolder;
    }
*/
        @RequestMapping("api/v1/forecastGraph")
        public @ResponseBody
        List<Location> sendForecastData () {
            return rateRepository.findLatest7();


/*
    @RequestMapping("api/v1/historyGraph")
    public @ResponseBody
    List<Location> sendHistoricalData() {


        return rateRepository.findLatest7();
    }

     */

    }
} }
