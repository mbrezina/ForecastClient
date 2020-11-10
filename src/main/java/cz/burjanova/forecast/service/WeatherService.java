package cz.burjanova.forecast.service;

import cz.burjanova.forecast.entity.ApiUrl;
import cz.burjanova.forecast.entity.Location;
import cz.burjanova.forecast.entity.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String key;

    @Value("${weather.api.place}")
    private String defaultPlace;

    @Autowired
    private ApiCall apiCall;

//
//    public Location makeForecast() throws IOException {
//
//        Location location = call.doGetRequest(url);
//
////        Location location = call.doGetRequest("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/" +
////            "forecast?aggregateHours=24&combinationMethod=aggregate&contentType=json&unitGroup=metric&locationMode=single&" +
////            "key=D2FFG9NXUC1WNU4138HL7A868&dataElements=default&locations=Paris");
//
//
//    /*Location forecast = call.doGetRequest("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/" +
//        "forecast?aggregateHours=24&combinationMethod=aggregate&contentType=json&unitGroup=metric&locationMode=single&" +
//        "key=D2FFG9NXUC1WNU4138HL7A868&dataElements=default&locations=Paris");
//     */
//        return location;
//    }

    public ModelAndView makeWebPage(String place, String nameOfWebView) throws IOException {
        ModelAndView dataHolder = new ModelAndView(nameOfWebView);

        if (place == null) {
            place = defaultPlace;
        }
        ApiUrl forecastUrl = new ApiUrl("forecast", place, key);
        ApiUrl historyUrl = new ApiUrl("history", place, key);

        String uuurl = forecastUrl.composeApiUrl();

        Location forecast = apiCall.doGetRequest(forecastUrl.composeApiUrl());
        //Location history = call.doGetRequest(historyUrl.composeApiUrl());

        String address = forecast.getAddress();
        dataHolder.addObject("address", address);

        List<Weather> dailyWeather = forecast.getValues();
        dataHolder.addObject("dailyWeather", dailyWeather);

        List<Weather> iconDays = dailyWeather.subList(0, 3);
        Integer count = 0;
        for (Weather day : iconDays) {
            day.attachConditionsIcon();
            log.info(day.getConditionsIcon());
            count++;

        }
        log.info("připsala jsem ikonu: " + count);
        // 3 days of the forecast with icons:
        dataHolder.addObject("iconDays", iconDays);

        log.info("size daily weather: " + String.valueOf(dailyWeather.size()));
        log.info("size icon forecast: " + String.valueOf(iconDays.size()));

        return dataHolder;
    }

    /*
    Location history = call.doGetRequest("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/" +
        "weatherdata/history?aggregateHours=24&combinationMethod=aggregate&" +
        "startDateTime=2020-11-01T00%3A00%3A00&endDateTime=2020-11-08T00%3A00%3A00&maxStations=-1&maxDistance=-1&" +
        "contentType=json&unitGroup=metric&locationMode=single&key=D2FFG9NXUC1WNU4138HL7A868&dataElements=default&locations=blatn%C3%A1");
     */

}
