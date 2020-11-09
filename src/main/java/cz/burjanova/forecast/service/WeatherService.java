package cz.burjanova.forecast.service;

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

    @Autowired
    private ApiCall call;

    @Value("${weather.api.key}")
    String key;

    public Location makeForecast(String place) throws IOException {

        Location location = call.doGetRequest("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/" +
            "forecast?aggregateHours=24&combinationMethod=aggregate&contentType=json&unitGroup=metric&locationMode=single&" +
            "key=D2FFG9NXUC1WNU4138HL7A868&dataElements=default&locations=Paris");
    /*Location forecast = call.doGetRequest("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/" +
        "forecast?aggregateHours=24&combinationMethod=aggregate&contentType=json&unitGroup=metric&locationMode=single&" +
        "key=D2FFG9NXUC1WNU4138HL7A868&dataElements=default&locations=Paris");
     */
        return location;
    }

    public ModelAndView makeWebPage(String place, String nameOfWebView) throws IOException  {
        Location forecast = this.makeForecast(place);
        String address = forecast.getAddress();
        List<Weather> dailyWeather = forecast.getValues();
        List<Weather> iconForecast = dailyWeather.subList(0, 1);

        ModelAndView dataHolder = new ModelAndView(nameOfWebView);
        dataHolder.addObject("dailyWeather", dailyWeather);
        dataHolder.addObject("iconForecast", iconForecast);
        dataHolder.addObject("address", address);

        return dataHolder;
    }






    /*
    Location history = call.doGetRequest("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/" +
        "weatherdata/history?aggregateHours=24&combinationMethod=aggregate&" +
        "startDateTime=2020-11-01T00%3A00%3A00&endDateTime=2020-11-08T00%3A00%3A00&maxStations=-1&maxDistance=-1&" +
        "contentType=json&unitGroup=metric&locationMode=single&key=D2FFG9NXUC1WNU4138HL7A868&dataElements=default&locations=blatn%C3%A1");
     */

}
