package cz.burjanova.forecast.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.burjanova.forecast.entity.ApiUrl;
import cz.burjanova.forecast.entity.GraphTemperature;
import cz.burjanova.forecast.entity.Location;
import cz.burjanova.forecast.entity.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public ModelAndView makeWebPage(String place, String nameOfWebView) throws IOException {
        ModelAndView dataHolder = new ModelAndView(nameOfWebView);

        ApiUrl forecastUrl = new ApiUrl("forecast", place, key);

        //ApiUrl historyUrl = new ApiUrl("history", place, key, prepareTimeFrameQuery());

        Location forecast = apiCall.doGetRequest(forecastUrl.composeForecastApiUrl());
        //Location history = apiCall.doGetRequest(historyUrl.composeHistoryApiUrl());

        if (forecast.getName().equals("not a valid place")) {
            log.info(forecast.getName());
            log.debug("inserted place is not valid");
            dataHolder.addObject("message", "The place " + place + " was not found, try another place or check typos");
            forecastUrl = new ApiUrl("forecast", defaultPlace, key);
            forecast = apiCall.doGetRequest(forecastUrl.composeForecastApiUrl());
        } else if (forecast.getName().equals("no location inserted")) {
            log.debug("no place inserted into search field");
            dataHolder.addObject("message", "You have not inserted any place, fill the search field to see the forecast");
            forecastUrl = new ApiUrl("forecast", defaultPlace, key);
            forecast = apiCall.doGetRequest(forecastUrl.composeForecastApiUrl());
        } else if (forecast.getName().equals("the API key is not valid")) {
            log.info("**********************************");
            log.info("NO VALID API KEY");
            log.info("**********************************");
            forecastUrl = new ApiUrl("forecast", defaultPlace, key);
            forecast = apiCall.doGetRequest(forecastUrl.composeForecastApiUrl());
        }

        //Location history = call.doGetRequest(historyUrl.composeApiUrl());

        //String address = forecast.getAddress();
        String nameOfPlace = forecast.getName();

        dataHolder.addObject("nameOfPlace", nameOfPlace);
        //dataHolder.addObject("address", address);

        List<Weather> dailyWeather = forecast.getValues();
        dataHolder.addObject("dailyWeather", dailyWeather);

        List<Weather> iconDays = dailyWeather.subList(0, 2);

        for (Weather day : iconDays) {
            day.attachConditionsIcon();
            log.debug(day.getConditionsIcon());
        }

        // 3 days of the forecast with icons:
        dataHolder.addObject("iconDays", iconDays);

        dataHolder.addObject("temperatureSerie", getTemperatureTimeSerie(place));
        log.info(getTemperatureTimeSerie(place));

        log.debug("size daily weather: " + String.valueOf(dailyWeather.size()));
        log.debug("size icon forecast: " + String.valueOf(iconDays.size()));

        return dataHolder;
    }

    public String getTemperatureTimeSerie(String place) throws IOException {
        List<GraphTemperature> listForGraph = new ArrayList<>();

        ApiUrl forecastUrl = new ApiUrl("forecast", place, key);
        ApiUrl historyUrl = new ApiUrl("history", place, key, prepareTimeFrameQuery());

        Location forecast = apiCall.doGetRequest(forecastUrl.composeForecastApiUrl());
        Location history = apiCall.doGetRequest(historyUrl.composeHistoryApiUrl());

        for (Weather day : history.getValues()) {
            listForGraph.add(new GraphTemperature(day.getTemp(), day.getReadableDate()));
        }

        for (Weather day : forecast.getValues()) {
            listForGraph.add(new GraphTemperature(day.getTemp(), day.getReadableDate()));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(listForGraph);
    }


    public String prepareTimeFrameQuery() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = startDateTime.minusDays(7);

        String queryDatePattern = "yyyy-MM-dd'T'hh:mm:ss";
        DateTimeFormatter queryFormatter = DateTimeFormatter.ofPattern(queryDatePattern);

        log.debug(queryFormatter.format(startDateTime));

        String start = queryFormatter.format(startDateTime);
        String end = queryFormatter.format(endDateTime);

        return "startDateTime=" + start + "&endDateTime=" + end;

    }


    /*
    Location history = call.doGetRequest("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/" +
        "weatherdata/history?aggregateHours=24&combinationMethod=aggregate&" +
        "startDateTime=2020-11-01T00%3A00%3A00&endDateTime=2020-11-08T00%3A00%3A00&maxStations=-1&maxDistance=-1&" +
        "contentType=json&unitGroup=metric&locationMode=single&key=D2FFG9NXUC1WNU4138HL7A868&dataElements=default&locations=blatn%C3%A1");
     */

}
