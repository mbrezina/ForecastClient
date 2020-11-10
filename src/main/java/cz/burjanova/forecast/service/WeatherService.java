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

        String baseURL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/";
        String callType = "forecast";
        String keyQuery = "key=" + key;
        String placeQuery = "locations=" + place;

        //String url =

        Location location = call.doGetRequest("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/" +
            "forecast?aggregateHours=24&combinationMethod=aggregate&contentType=json&unitGroup=metric&locationMode=single&" +
            "key=D2FFG9NXUC1WNU4138HL7A868&dataElements=default&locations=Paris");

        /*
public Tea(String name, Integer milk, Boolean herbs, Integer sugar, Integer teaPowder) {
            this.name = name;
            this.milk = milk == null ? 0 : milk.intValue();
            this.herbs = herbs == null ? false : herbs.booleanValue();
            this.sugar = sugar == null ? 0 : sugar.intValue();
            this.teaPowder = teaPowder == null ? DEFAULT_TEA_POWDER : teaPowder.intValue();
        }

         */

    /*Location forecast = call.doGetRequest("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/" +
        "forecast?aggregateHours=24&combinationMethod=aggregate&contentType=json&unitGroup=metric&locationMode=single&" +
        "key=D2FFG9NXUC1WNU4138HL7A868&dataElements=default&locations=Paris");
     */
        return location;
    }

    public ModelAndView makeWebPage(String place, String nameOfWebView) throws IOException {
        ModelAndView dataHolder = new ModelAndView(nameOfWebView);
        Location forecast = this.makeForecast(place);

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
