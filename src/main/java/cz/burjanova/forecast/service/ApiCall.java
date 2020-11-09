package cz.burjanova.forecast.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import cz.burjanova.forecast.entity.Location;
import cz.burjanova.forecast.entity.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ApiCall {

    private final OkHttpClient client = new OkHttpClient();

    public Location doGetRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String stringResponse = response.body().string();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        cz.burjanova.forecast.entity.Response rawResponse = objectMapper.readValue(stringResponse, cz.burjanova.forecast.entity.Response.class);
        Location location = rawResponse.getLocation();
        List<Weather> rawWeather = location.getValues();
        for (Weather day : rawWeather) {
            day.applyReadableDate();
        }
        return location;
    }

    public Location parseResponse(String resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        cz.burjanova.forecast.entity.Response rawResponse = objectMapper.readValue(resp, cz.burjanova.forecast.entity.Response.class);
        Location location = rawResponse.getLocation();
        List<Weather> rawWeather = location.getValues();
        for (Weather day : rawWeather) {
            day.applyReadableDate();
        }
        Location locationDetails = rawResponse.getLocation();
        return locationDetails;
    }
}


