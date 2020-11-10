package cz.burjanova.forecast.controller;


import cz.burjanova.forecast.entity.PlaceForm;
import cz.burjanova.forecast.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@Controller
//@RequestMapping(path = "weather")
public class MainController {

    @Autowired
    WeatherService weatherService;


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



    /*
    @RequestMapping("api/v1/forecastGraph")
    public @ResponseBody
    List<Location> sendForecastData() {


        return rateRepository.findLatest7();
    }


    @RequestMapping("api/v1/historyGraph")
    public @ResponseBody
    List<Location> sendHistoricalData() {


        return rateRepository.findLatest7();
    }

     */

}
