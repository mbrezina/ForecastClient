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

    public Location makeForecastCall(String place) throws IOException {
        ApiUrl forecastUrl = new ApiUrl("forecast", place, key);
        return apiCall.doGetRequest(forecastUrl.composeForecastApiUrl());
    }

    public Location makeHistoryCall(String place) throws IOException {
        ApiUrl historyUrl = new ApiUrl("history", place, key, prepareTimeFrameQuery());
        return apiCall.doGetRequest(historyUrl.composeHistoryApiUrl());
    }

    public ModelAndView makeWebPage(String place, String nameOfWebView) throws IOException {   //basic home page
        ModelAndView dataHolder = new ModelAndView(nameOfWebView);
        Location forecast = makeForecastCall(place);

        if (forecast.getName().equals("not a valid place")) {
            //log.info(forecast.getName());
            log.info("inserted place is not valid");
            dataHolder.addObject("message", "The place " + place + " was not found, try another place or check typos");
            forecast = makeForecastCall(defaultPlace);

        } else if (forecast.getName().equals("no location inserted")) {
            log.info("no place inserted into search field");
            dataHolder.addObject("message", "You have not inserted any place, fill the search field to see the forecast");
            forecast = makeForecastCall(defaultPlace);

        } else if (forecast.getName().equals("the API key is not valid")) {
            log.info("**********************************");
            log.info("NO VALID API KEY");
            log.info("**********************************");
            forecast = makeForecastCall(defaultPlace);
        }

        dataHolder.addObject("nameOfPlace", forecast.getName());
        dataHolder.addObject("iconDays", getIconDays(forecast.getValues()));
        dataHolder.addObject("temperatureSerie", getTemperatureTimeSerie(place, forecast));
        log.debug(getTemperatureTimeSerie(place, forecast));
        log.debug("size daily weather: " + forecast.getValues().size());

        return dataHolder;
    }

    public List<Weather> getIconDays(List<Weather> dailyWeather) {
        List<Weather> iconDays = dailyWeather.subList(0, 2);
        for (Weather day : iconDays) {
            day.attachConditionsIcon();
            log.debug(day.getConditionsIcon());
        }
        return iconDays;
    }


    public String getTemperatureTimeSerie(String place, Location forecast) throws IOException {
        List<GraphTemperature> listForGraph = new ArrayList<>();

        Location history = makeHistoryCall(place);

        for (Weather day : history.getValues()) {
            day.applyJavaScriptDate();
            //listForGraph.add(new GraphTemperature(day.getTemp(), day.getReadableDate()));
            listForGraph.add(new GraphTemperature(day.getTemp(), day.getJavaScriptDate()));
        }

        for (Weather day : forecast.getValues()) {
            day.applyJavaScriptDate();
            //listForGraph.add(new GraphTemperature(day.getTemp(), day.getReadableDate()));
            listForGraph.add(new GraphTemperature(day.getTemp(), day.getJavaScriptDate()));
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


}
