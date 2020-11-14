package cz.burjanova.forecast.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.burjanova.forecast.entity.*;
import cz.burjanova.forecast.repository.StarLocationRepository;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String key;

    @Value("${weather.api.place}")
    private String defaultPlace;

    @Autowired
    private ApiCall apiCall;

    @Autowired
    private StarLocationRepository starLocationRepository;

    public Location makeForecastCall(String place) throws IOException {
        ApiUrl forecastUrl = new ApiUrl("forecast", place, key);
        return apiCall.doGetRequest(forecastUrl.composeForecastApiUrl());
    }

    public Location makeHistoryCall(String place) throws IOException {
        ApiUrl historyUrl = new ApiUrl("history", place, key, prepareTimeFrameQuery());
        log.debug(String.valueOf(historyUrl));
        return apiCall.doGetRequest(historyUrl.composeHistoryApiUrl());
    }

    public ModelAndView makeBasicPage(String place, String nameOfWebView) throws IOException {   //basic home page
        ModelAndView dataHolder = new ModelAndView(nameOfWebView);
        Location forecast = makeForecastCall(place);

        if (forecast.getMessage() != null) {
            dataHolder.addObject("message", forecast.getMessage());
            forecast = makeForecastCall(defaultPlace);
        }

        dataHolder.addObject("nameOfPlace", forecast.getName());
        dataHolder.addObject("iconDays", getIconDays(forecast.getValues()));
        dataHolder.addObject("temperatureSerie", getTemperatureTimeSerie(place, forecast));
        dataHolder.addObject("authenticated", "false");

        log.debug(getTemperatureTimeSerie(place, forecast));
        log.debug("size daily weather: " + forecast.getValues().size());

        return dataHolder;
    }

    public ModelAndView makeStarPage(String nameOfWebView, String subCode, String email) throws IOException {
        ModelAndView dataHolder = new ModelAndView(nameOfWebView);

        StarLocation userRecord = starLocationRepository.findBySub(subCode);
        log.info("userRecord: " + userRecord);

        List<String> starLocationList = new ArrayList<>();

        if (userRecord != null) {
            starLocationList.add(userRecord.getLocation1());
            if (userRecord.getLocation2() != null) {
                starLocationList.add(userRecord.getLocation2());
            }
            if (userRecord.getLocation3() != null) {
                starLocationList.add(userRecord.getLocation3());
            }
        } else {
            starLocationRepository.save(new StarLocation(subCode, email, defaultPlace));
            starLocationList.add(defaultPlace);
        }

        log.info("Favourite locations: starLocationList " + starLocationList);

        Map<String, List<Weather>> listStarForecast = new LinkedHashMap<>();

        log.debug(String.valueOf(starLocationList));

        for (
            String oneLocation : starLocationList) {
            Location forecast = makeForecastCall(oneLocation);
            listStarForecast.put(oneLocation, getIconDays(forecast.getValues()));
        }

        log.debug(String.valueOf(listStarForecast));

        dataHolder.addObject("starLocationList", starLocationList);
        dataHolder.addObject("listStarForecast", listStarForecast);
        dataHolder.addObject("authenticated", "true");

        return dataHolder;
    }

    public List<Weather> getIconDays(List<Weather> dailyWeather) {
        for (Weather day : dailyWeather) {
            day.attachConditionsIcon();
            log.debug(day.getConditionsIcon());
        }
        return dailyWeather.subList(0, 3);
    }

    public String getTemperatureTimeSerie(String place, Location forecast) throws IOException {
        List<GraphTemperature> listForGraph = new ArrayList<>();

        Location history = makeHistoryCall(place);
        if (history.getMessage() != null) {
            history = makeForecastCall(defaultPlace);
        }

        for (Weather day : history.getValues()) {
            day.applyJavaScriptDate();
            listForGraph.add(new GraphTemperature(day.getTemp(), day.getJavaScriptDate()));
        }

        log.info("list for graph " + String.valueOf(listForGraph));

        for (Weather day : forecast.getValues()) {
            day.applyJavaScriptDate();
            listForGraph.add(new GraphTemperature(day.getTemp(), day.getJavaScriptDate()));
        }

        ObjectMapper objectMapper = new ObjectMapper();

        log.debug("timeSerie for graph: " + objectMapper.writeValueAsString(listForGraph));

        return objectMapper.writeValueAsString(listForGraph);
    }

    public String prepareTimeFrameQuery() {
        LocalDate endDate = LocalDate.now().minusDays(1);
        LocalDateTime endDateTime = endDate.atStartOfDay();
        LocalDateTime startDateTime = endDateTime.minusDays(14);

        String queryDatePattern = "yyyy-MM-dd'T'hh:mm:ss";
        DateTimeFormatter queryFormatter = DateTimeFormatter.ofPattern(queryDatePattern);

        log.debug(queryFormatter.format(startDateTime));

        String start = queryFormatter.format(startDateTime);
        String end = queryFormatter.format(endDateTime);

        return "startDateTime=" + start + "&endDateTime=" + end;
    }

    public String saveNewFavouritePlace(String newFavouritePlace, String sub, String email) throws IOException {
        Location testForecast = makeForecastCall(newFavouritePlace);

        if (testForecast.getMessage() != null) {
            return "The place you inserted was not found, try another place or check typos";
        }

        StarLocation existingStarLocation = starLocationRepository.findBySub(sub);
        if (existingStarLocation.getLocation2() == null) {
            existingStarLocation.setLocation2(existingStarLocation.getLocation1());
            existingStarLocation.setLocation1(newFavouritePlace);
            starLocationRepository.save(existingStarLocation);
        } else {
            existingStarLocation.setLocation3(existingStarLocation.getLocation2());
            existingStarLocation.setLocation2(existingStarLocation.getLocation1());
            existingStarLocation.setLocation1(newFavouritePlace);
            starLocationRepository.save(existingStarLocation);
        }

        return "location is ok";
    }
}
