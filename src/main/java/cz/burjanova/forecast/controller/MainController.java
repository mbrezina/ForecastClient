package cz.burjanova.forecast.controller;


import cz.burjanova.forecast.entity.Location;
import cz.burjanova.forecast.entity.Weather;
import cz.burjanova.forecast.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
//@RequestMapping(path = "weather")
public class MainController {

    @Autowired
    WeatherService weatherService;

    @RequestMapping("")
    public ModelAndView showRate() throws IOException {

        Location forecast = weatherService.makeForecats();

        String address = forecast.getAddress();
        List<Weather> dailyWeather = forecast.getValues();

        ModelAndView dataHolder = new ModelAndView("index");

        dataHolder.addObject("dailyWeather", dailyWeather);

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
